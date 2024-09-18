package com.team12.hub.manager.domain;

import com.team12.hub.manager.dto.ManagerSearchRequestDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ManagerSpecification {
    public static Specification<Manager> searchWith(ManagerSearchRequestDto searchRequestDto) {
        return (root, query, criteriaBuilder) -> {
            // 기본 조건: 삭제되지 않은(delYn = 'N') 레코드만 검색
            Predicate predicate = criteriaBuilder.equal(root.get("isDeleted"), false);
            // 검색 조건 1: 매니저 ID(id)가 null이 아닌 경우, 해당 ID와 일치하는 레코드 추가
            if (searchRequestDto.getManagerId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("id"), searchRequestDto.getManagerId())); // `LIKE` 대신 `equal` 사용
            }
            // 검색 조건 2: 허브 ID(hubId)가 null이 아닌 경우, 해당 상태와 일치하는 레코드 추가
            if (searchRequestDto.getHubId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("hub").get("id"), searchRequestDto.getHubId()));
            }
            // 검색 조건 2: 타입(type)이 null이 아닌 경우, 해당 상태와 일치하는 레코드 추가
            if (searchRequestDto.getType() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("type"), searchRequestDto.getType()));
            }
            return predicate; // 최종적으로 생성된 조건 반환
        };
    }
}
