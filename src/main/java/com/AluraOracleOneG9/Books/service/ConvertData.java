package com.AluraOracleOneG9.Books.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements ConvertDataInterface {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T convertDataToObject(String json, Class<T> iclass) {
        try {
            return mapper.readValue(json, iclass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
