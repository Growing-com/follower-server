package com.sarangchurch.follower.common.jpa.converter;

import com.sarangchurch.follower.common.types.vo.PhoneNumber;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PhoneNumberConverter implements AttributeConverter<PhoneNumber, String> {
    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {
        return (attribute != null) ? attribute.getPhoneNumber() : null;
    }

    @Override
    public PhoneNumber convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? new PhoneNumber(dbData) : null;
    }
}
