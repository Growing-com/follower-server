package com.sarangchurch.follower.common.jpa.converter;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.net.URI;

@Converter(autoApply = true)
public class UriConverter implements AttributeConverter<URI, String> {
    @Override
    public String convertToDatabaseColumn(URI attribute) {
        return (attribute != null) ? attribute.toString() : null;
    }

    @Override
    public URI convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? URI.create(dbData.trim()) : null;
    }
}
