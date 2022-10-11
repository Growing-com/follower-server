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

        fieldDescriptors.add(fieldWithPath("pageable.offset").type(NUMBER).description("사용한 offset."));
        fieldDescriptors.add(fieldWithPath("pageable.pageSize").type(NUMBER).description("사용한 size"));
        fieldDescriptors.add(fieldWithPath("numberOfElements").type(NUMBER).description("컨텐츠 개수"));
        fieldDescriptors.add(fieldWithPath("first").type(BOOLEAN).description("첫번째 페이지 여부"));
        fieldDescriptors.add(fieldWithPath("last").type(BOOLEAN).description("마지막 페이지 여부. next-offset = offset + numberOfElements"));
        fieldDescriptors.add(fieldWithPath("pageable.pageNumber").ignored());
        fieldDescriptors.add(fieldWithPath("pageable.sort.unsorted").ignored());
        fieldDescriptors.add(fieldWithPath("pageable.sort.sorted").ignored());
        fieldDescriptors.add(fieldWithPath("pageable.sort.empty").ignored());
        fieldDescriptors.add(fieldWithPath("pageable.paged").ignored());
        fieldDescriptors.add(fieldWithPath("pageable.unpaged").ignored());
        fieldDescriptors.add(fieldWithPath("empty").ignored());
        fieldDescriptors.add(fieldWithPath("number").ignored());
        fieldDescriptors.add(fieldWithPath("sort.unsorted").ignored());
        fieldDescriptors.add(fieldWithPath("sort.sorted").ignored());
        fieldDescriptors.add(fieldWithPath("sort.empty").ignored());
        fieldDescriptors.add(fieldWithPath("size").ignored());
        return fieldDescriptors;
    }
}
