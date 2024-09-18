package com.team12.hub.hub.domain;

import com.team12.hub.hub.dto.HubSearchRequestDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class HubSpecification {
    public static Specification<Hub> searchWith(HubSearchRequestDto searchRequestDto) {
        return (root, query, criteriaBuilder) -> {
            // 기본 조건: 삭제되지 않은(delYn = false) 레코드만 검색
            Predicate predicate = criteriaBuilder.equal(root.get("isDeleted"), false);
            // 검색 조건 1: 허브 ID가 null이 아닌 경우, 해당 상태와 일치하는 레코드 추가
            if (searchRequestDto.getId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("id"), searchRequestDto.getId()));
            }
            // 검색 조건 2: 허브 이름(name)이 null이 아닌 경우, 해당 이름을 포함하는 레코드 추가
            if (searchRequestDto.getName() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get("name"), "%" + searchRequestDto.getName() + "%"));
            }
            // 검색 조건 3: 허브 주소(address)이 null이 아닌 경우, 해당 이름을 포함하는 레코드 추가
            if (searchRequestDto.getAddress() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get("address"), "%" + searchRequestDto.getAddress() + "%"));
            }

            return predicate; // 최종적으로 생성된 조건 반환
        };
    }
}
