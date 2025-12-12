package com.AluraOracleOneG9.Books.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook (
        String title,
        Set<DataAuthor> authors,
        List<String> summaries,
        List<String> subjects,
        List<String> bookshelves,
        List<String> languages,
        boolean copyright,
        Map<String, String> formats,
        int download_count
) {
}
