package com.team12.user.repository;

//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.core.types.dsl.StringPath;
//import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team12.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserJpaRepository userJpaRepository;

//    @Autowired
    private JPAQueryFactory queryFactory;

    //username 중복 확인
    public void findByUsername(String username) throws BusinessLogicException {
        if(userJpaRepository.findByUsername(username).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_EXIST_USER);
        };
    }

    //db 저장
    public void save(User user) {
        userJpaRepository.save(user);
    }

    //findById (존재 여부 확인)
    public User findById(Long userId) {
        User user =  userJpaRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        if(user.getIsDeleted()) throw new BusinessLogicException(ExceptionCode.DELETED_USER);
        return user;
    }


    //findAll
    public Page<User> findAll(Pageable pageable) {
        return userJpaRepository.findAll(pageable);
    }

    //queryDsl
    public Page<User> search(String text, Pageable pageable) {
        // 페이징된 데이터 가져오기
        List<User> users = queryFactory
                .select(user)
                .from(user)
                .where(
                        containsText(user.name, text)
                                .or(containsText(user.username, text))
                                .or(containsText(user.slackEmail, text))
                )
                .offset(pageable.getOffset())    // 시작 위치
                .limit(pageable.getPageSize())   // 페이지 크기
                .fetch();

        // 전체 레코드 수 계산
        long totalCount = queryFactory
                .select(user)
                .from(user)
                .where(
                        containsText(user.name, text)
                                .or(containsText(user.username, text))
                                .or(containsText(user.slackEmail, text))
                )
                .fetchCount();

        // Page 객체로 반환
        return new PageImpl<>(users, pageable, totalCount);
    }
    private BooleanExpression containsText(StringPath path, String text) {
        return text != null ? path.contains(text) : null;
    }

    public User findByUsernameForAuth(String username) {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    };


}

