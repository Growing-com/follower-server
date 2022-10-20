package com.sarangchurch.follower.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sarangchurch.follower.common.types.vo.PhoneNumber;

import java.io.IOException;

public class PhoneNumberSerializer extends StdSerializer<PhoneNumber> {

    public PhoneNumberSerializer(Class<PhoneNumber> aClass) {
        super(aClass);
    }

    @Override
    public void serialize(PhoneNumber value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getPhoneNumber());
    }
}
