package com.team12.common.audit;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<Long> {
    private static final String USER_ID_HEADER = "X-User-Id";  // 헤더에서 가져올 ID 값의 키
    private static final Long DEFAULT_USER_ID = 1L;  // 헤더가 없을 경우 사용할 기본 ID 값

    @Override
    public Optional<Long> getCurrentAuditor() {
        // 실제 인증된 사용자의 ID를 반환하도록 수정
        // 현재 요청의 HttpServletRequest 객체를 가져옴
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // 헤더에서 "X-User-Id" 값을 추출
            String userIdHeader = request.getHeader(USER_ID_HEADER);

            // 헤더에 값이 존재하면 Long 타입으로 변환하여 반환
            if (userIdHeader != null) {
                try {
                    Long userId = Long.parseLong(userIdHeader);
                    return Optional.of(userId);
                } catch (NumberFormatException e) {
                    // 숫자로 변환 실패 시 처리
                    e.printStackTrace();
                }
            }
        }
        // 기본값으로 1L 반환 (헤더에 값이 없거나 변환 실패 시)
        return Optional.of(DEFAULT_USER_ID);  // 필요시 기본값을 설정하거나 예외를 처리할 수 있음
    }
}
