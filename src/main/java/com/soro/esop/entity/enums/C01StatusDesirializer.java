package com.soro.esop.entity.enums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * packageName : com.soro.esop.entity.enums
 * fileName    : YnDesirializer
 * author      : soromiso
 * date        : 9/18/24
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/18/24             soromiso             new
 */
public class C01StatusDesirializer extends JsonDeserializer<C01Status> {
    @Override
    public C01Status deserialize(JsonParser p,
                          DeserializationContext ctxt) throws java.io.IOException, JsonProcessingException {
        String value = p.getValueAsString();
        return C01Status.valueOf(value.toUpperCase());
    }
}
