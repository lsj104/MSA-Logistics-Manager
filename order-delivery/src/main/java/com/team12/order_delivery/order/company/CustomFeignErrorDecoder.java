package com.team12.order_delivery.order.company;

import com.team12.order_delivery.order.exception.ExceptionMessage;
import com.team12.order_delivery.order.exception.OrderException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new OrderException(ExceptionMessage.COMPANY_NOT_FOUND);
        } else if (response.status() == 400) {
            return new OrderException(ExceptionMessage.PRODUCT_NOT_FOUND);
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}