package com.AluraOracleOneG9.Books.DTO;

import com.AluraOracleOneG9.Books.model.Author;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record BookDTO(
        String title,
        Set<String> summaries,
        Set<String> subjects,
        Set<String> bookshelves,
        Set<String> languages,
        boolean copyright,
        String mediaType,
        int downloadCount,
        Map<String, String> formats,
        Set<String> authors
) {
}
