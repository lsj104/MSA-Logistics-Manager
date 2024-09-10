package com.team12.common.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // 실제 인증된 사용자를 반환하도록 수정
        // 예: SecurityContextHolder.getContext().getAuthentication().getName()
        return Optional.of("system");  // 여기서 'system'은 임시 사용자
    }
}
