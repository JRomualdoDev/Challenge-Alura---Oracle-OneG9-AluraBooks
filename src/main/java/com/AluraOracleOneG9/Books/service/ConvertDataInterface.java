package com.AluraOracleOneG9.Books.service;

public interface ConvertDataInterface {
    <T> T convertDataToObject(String json, Class<T> iclass);
}
