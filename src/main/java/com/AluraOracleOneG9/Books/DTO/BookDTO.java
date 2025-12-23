package com.AluraOracleOneG9.Books.DTO;

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
