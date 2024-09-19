//package com.team12.hub.config;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonNode;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//
//import java.io.IOException;
//import java.util.List;
//
//public class PageImplDeserializer extends JsonDeserializer<PageImpl<?>> {
//
//    @Override
//    public PageImpl<?> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
//        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
//
//        // JsonNode에서 content 가져오기
//        List<?> content = ctxt.readValue(node.get("content").traverse(), List.class);
//
//        // number, size, totalElements 값 추출
//        int number = node.get("number").asInt();
//        int size = node.get("size").asInt();
//        long totalElements = node.get("totalElements").asLong();
//
//        return new PageImpl<>(content, PageRequest.of(number, size), totalElements);
//    }
//}
