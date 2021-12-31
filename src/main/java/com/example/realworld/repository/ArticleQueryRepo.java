package com.example.realworld.repository;

import com.example.realworld.controller.dto.ArticleListResponseDto;
import com.example.realworld.controller.dto.ArticleResponseDto;
import com.example.realworld.controller.dto.AuthorResponseDto;
import com.example.realworld.controller.dto.FavoriteDto;
import com.example.realworld.entity.QFavorite;
import com.example.realworld.entity.QFollow;
import com.example.realworld.entity.QTag;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.realworld.entity.QArticle.article;
import static com.example.realworld.entity.QUser.user;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class ArticleQueryRepo {

    private final JPAQueryFactory jpaQueryFactory;

    public ArticleListResponseDto getArticles(String tag, String author, String favorited, Long userId) {
        List<ArticleResponseDto> articles = getArticleResponseDtos(tag, author);
        for (ArticleResponseDto articleResponseDto : articles) {
            articleResponseDto.setAuthor(getAuthor(articleResponseDto.getArticleId(), userId));

            List<FavoriteDto> favorite = getFavorite(articleResponseDto.getArticleId(), userId, favorited);
            articleResponseDto.setFavorited(favorite.stream().anyMatch(f -> f.getFavorited() == true));
        }

        return new ArticleListResponseDto(articles, articles.size());
    }

    public List<FavoriteDto> getFavorite(Long articleId, Long userId, String favorited) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        FavoriteDto.class,
                        new CaseBuilder()
                                .when(QFavorite.favorite.user.id.eq(userId)).then(true)
                                .otherwise(false).as("favorited")
                        ))
                .from(QFavorite.favorite)
                .where(QFavorite.favorite.article.id.eq(articleId), eqFavorited(favorited))
                .fetch();
    }

    public AuthorResponseDto getAuthor(Long articleId, Long userId) {
        return jpaQueryFactory
                .select(Projections.fields(
                        AuthorResponseDto.class,
                        user.id,
                        user.userName,
                        user.bio,
                        user.image,
                        new CaseBuilder()
                                .when(QFollow.follow.follower.id.eq(userId)).then(true)
                                .otherwise(false).as("following")

                ))
                .from(user)
                .leftJoin(user.follow, QFollow.follow)
                .leftJoin(user.articles, article)
                .where(article.id.eq(articleId))
                .fetchOne();
    }

    private List<ArticleResponseDto> getArticleResponseDtos(String tag, String author) {
        return jpaQueryFactory
                .from(article)
                .leftJoin(article.tagList, QTag.tag)
                .leftJoin(article.user, user)
                .where(eqTag(tag), eqAuthor(author))
                .transform(
                        groupBy(article.id).list(
                                Projections.constructor(
                                        ArticleResponseDto.class,
                                        article.id,
                                        article.slug,
                                        article.title,
                                        article.description,
                                        article.body,
                                        list(
                                                Projections.constructor(
                                                        String.class,
                                                        QTag.tag.tagName
                                                )
                                        ),
                                        article.createdAt,
                                        article.updatedAt,
                                        article.likeCount
                                )
                        )
                );
    }

    private BooleanExpression eqAuthor(String author) {
        if (!StringUtils.hasText(author)) {
            return null;
        }
        return user.userName.eq(author);
    }

    private BooleanExpression eqTag(String tag) {
        if (!StringUtils.hasText(tag)) {
            return null;
        }
        return QTag.tag.tagName.eq(tag);
    }

    private BooleanExpression eqFavorited(String favorited) {
        if (!StringUtils.hasText(favorited)) {
            return null;
        }
        return QFavorite.favorite.user.userName.eq(favorited);
    }
}
