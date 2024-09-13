package com.team12.hub.hubPath.domain;

import com.team12.hub.hubPath.dto.HubPathSearchRequestDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class HubPathSpecification {
    public static Specification<HubPath> searchWith(HubPathSearchRequestDto searchRequestDto) {
        return (root, query, criteriaBuilder) -> {
            // 기본 조건: 삭제되지 않은(delYn = 'N') 레코드만 검색
            Predicate predicate = criteriaBuilder.equal(root.get("isDeleted"), false);
            // 검색 조건 1: fromHubId가 null이 아닌 경우, 해당 상태와 일치하는 레코드 추가
            if (searchRequestDto.getFromHubId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("fromHubId"), searchRequestDto.getFromHubId()));
            }
            // 검색 조건 2: toHubId가 null이 아닌 경우, 해당 상태와 일치하는 레코드 추가
            if (searchRequestDto.getToHubId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("toHubId"), searchRequestDto.getToHubId()));
            }


            return predicate; // 최종적으로 생성된 조건 반환
        };
    }
}
