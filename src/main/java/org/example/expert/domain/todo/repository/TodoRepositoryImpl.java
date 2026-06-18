package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        Todo result = queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(
            String keyword,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String nickname,
            Pageable pageable
    ) {
        QManager managerCount = new QManager("managerCount");
        QComment commentCount = new QComment("commentCount");

        BooleanBuilder condition = new BooleanBuilder();
        if (StringUtils.hasText(keyword)) {
            condition.and(todo.title.contains(keyword));
        }
        if (startDate != null) {
            condition.and(todo.createdAt.goe(startDate));
        }
        if (endDate != null) {
            condition.and(todo.createdAt.loe(endDate));
        }
        if (StringUtils.hasText(nickname)) {
            QManager managerFilter = new QManager("managerFilter");
            QUser userFilter = new QUser("userFilter");
            condition.and(JPAExpressions.selectOne()
                    .from(managerFilter)
                    .join(managerFilter.user, userFilter)
                    .where(managerFilter.todo.eq(todo), userFilter.nickname.contains(nickname))
                    .exists());
        }

        List<TodoSearchResponse> content = queryFactory
                .select(Projections.constructor(TodoSearchResponse.class,
                        todo.title,
                        JPAExpressions.select(managerCount.count())
                                .from(managerCount)
                                .where(managerCount.todo.eq(todo)),
                        JPAExpressions.select(commentCount.count())
                                .from(commentCount)
                                .where(commentCount.todo.eq(todo))
                ))
                .from(todo)
                .where(condition)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(todo.count())
                .from(todo)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0L : total);
    }
}
