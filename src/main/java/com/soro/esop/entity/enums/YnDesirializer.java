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
public class YnDesirializer extends JsonDeserializer<Yn> {
    @Override
    public Yn deserialize(JsonParser p,
                          DeserializationContext ctxt) throws java.io.IOException, JsonProcessingException {
        String value = p.getValueAsString();
        if ("Y".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)) {
            return Yn.Y;
        } else {
            return Yn.N;
        }
    }
}
