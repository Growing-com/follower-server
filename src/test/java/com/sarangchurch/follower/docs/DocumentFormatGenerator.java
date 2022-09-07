package com.sarangchurch.follower.docs;

import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.key;

public interface DocumentFormatGenerator {

    static Attributes.Attribute getJwtFormat() {
        return key("format").value("JWT 토큰");
    }

    static Attributes.Attribute getUUIDFormat() {
        return key("format").value("UUID");
    }
}
