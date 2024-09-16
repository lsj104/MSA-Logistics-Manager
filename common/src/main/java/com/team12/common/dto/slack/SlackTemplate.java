package com.team12.common.dto.slack;

public class SlackTemplate {
    public static String startDelivery(String orderNumber, String status) {
        return String.format("""
            :package: *배송 시작*
            
            안녕하세요! 주문하신 상품의 배송이 시작되었습니다.
            
            :memo: 주문 번호: `%s`
            :truck: 현재 상태: *%s*
            
            배송 상태가 변경될 때마다 업데이트해 드리겠습니다.
            
            문의사항이 있으시면 언제든 고객센터로 연락 주세요.
            
            감사합니다!
            """, orderNumber, status);
    }

    public static String endDelivery(String orderNumber, String status) {
        return String.format("""
            :package: *배송 완료*
            
            안녕하세요! 주문하신 상품의 배송이 완료되었습니다.
            
            :memo: 주문 번호: `%s`
            :truck: 현재 상태: *%s*
                        
            문의사항이 있으시면 언제든 고객센터로 연락 주세요.
            
            감사합니다!
            """, orderNumber, status);
    }

    public static String updateDeliveryStatus(String orderNumber, String status) {
        return String.format("""
            :package: *배송 상태 업데이트*
            
            안녕하세요! 주문하신 상품의 배송 상태가 업데이트되었습니다.
            
            :memo: 주문 번호: `%s`
            :truck: 현재 상태: *%s*
            
            배송 상태가 변경될 때마다 업데이트해 드리겠습니다.
            
            문의사항이 있으시면 언제든 고객센터로 연락 주세요.
            
            감사합니다!
            """, orderNumber, status);
    }

    public static String arrivedAtHub(String deliveryId, String status, String fromHubId) {
        return String.format("""
            :package: *경유지 도착*
            
            안녕하세요! 주문하신 상품이 경유지에 도착했습니다.
            
            :memo: 주문 번호: `%s`
            :truck: 현재 상태: *%s*
            :factory: 도착 경유지: `%s`
            
            배송 상태가 변경될 때마다 업데이트해 드리겠습니다.
            
            문의사항이 있으시면 언제든 고객센터로 연락 주세요.
            
            감사합니다!
            """, deliveryId, status, fromHubId);

    }
}
