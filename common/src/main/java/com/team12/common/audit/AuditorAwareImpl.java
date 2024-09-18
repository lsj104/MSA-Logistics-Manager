package com.team12.common.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        // 실제 인증된 사용자의 ID를 반환하도록 수정
        // 예: 현재는 임시로 1L을 반환
        return Optional.of(1L);
    }
}