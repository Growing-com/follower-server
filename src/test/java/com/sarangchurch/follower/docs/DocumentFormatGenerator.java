package com.sarangchurch.follower.docs;

import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.key;

public interface DocumentFormatGenerator {

    static Attributes.Attribute getDateFormant() {
        return key("format").value("yyyy-MM-dd");
    }

    static Attributes.Attribute getUUIDFormat() {
        return key("format").value("UUID");
    }
}
