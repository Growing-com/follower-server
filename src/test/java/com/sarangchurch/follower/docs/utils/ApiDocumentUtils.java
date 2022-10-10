package com.sarangchurch.follower.docs.utils;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public interface ApiDocumentUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .scheme("https")
                        .host("docs.follower.com")
                        .removePort(),
                prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

    static List<FieldDescriptor> getPagingFieldDescriptors(FieldDescriptor... contentDescriptors) {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        Collections.addAll(fieldDescriptors, contentDescriptors);

        fieldDescriptors.add(fieldWithPath("pageable.offset").type(NUMBER).description("사용한 offset(⭐)"));
        fieldDescriptors.add(fieldWithPath("pageable.pageSize").type(NUMBER).description("사용한 size(⭐)"));
        fieldDescriptors.add(fieldWithPath("numberOfElements").type(NUMBER).description("컨텐츠 개수(⭐)"));
        fieldDescriptors.add(fieldWithPath("last").type(BOOLEAN).description("마지막 페이지 여부(⭐)"));
        fieldDescriptors.add(fieldWithPath("first").type(BOOLEAN).description("첫번째 페이지 여부(⭐)"));
        fieldDescriptors.add(fieldWithPath("pageable.pageNumber").type(NUMBER).description("페이지 번호(무시)"));
        fieldDescriptors.add(fieldWithPath("pageable.sort.unsorted").type(BOOLEAN).description("정렬 미사용 여부"));
        fieldDescriptors.add(fieldWithPath("pageable.sort.sorted").type(BOOLEAN).description("정렬 사용 여부"));
        fieldDescriptors.add(fieldWithPath("pageable.sort.empty").type(BOOLEAN).description("정렬 조건 존재 여부"));
        fieldDescriptors.add(fieldWithPath("pageable.paged").type(BOOLEAN).description("페이징 사용 여부"));
        fieldDescriptors.add(fieldWithPath("pageable.unpaged").type(BOOLEAN).description("페이징 미사용 여부"));
        fieldDescriptors.add(fieldWithPath("empty").type(BOOLEAN).description("컨텐츠 존재 여부"));
        fieldDescriptors.add(fieldWithPath("number").type(NUMBER).description(""));
        fieldDescriptors.add(fieldWithPath("sort.unsorted").type(BOOLEAN).description(""));
        fieldDescriptors.add(fieldWithPath("sort.sorted").type(BOOLEAN).description(""));
        fieldDescriptors.add(fieldWithPath("sort.empty").type(BOOLEAN).description(""));
        fieldDescriptors.add(fieldWithPath("size").type(NUMBER).description(""));
        return fieldDescriptors;
    }
}
