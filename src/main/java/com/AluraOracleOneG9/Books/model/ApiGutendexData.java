package com.AluraOracleOneG9.Books.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiGutendexData(
        @JsonAlias("results") List<DataBook> books
) {
}
