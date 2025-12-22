package com.AluraOracleOneG9.Books.repository;

import com.AluraOracleOneG9.Books.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findFirstByTitle(String title);

    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.languages " +
            "LEFT JOIN FETCH b.formats " +
            "LEFT JOIN FETCH b.authors " +
            "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> findByTitleContaining(@Param("title") String title);

    // LAZY are default.
    //EntityGraph is the control.
    @EntityGraph(attributePaths = {"authors", "formats", "languages"})
    Page<Book> findAll(Pageable pageable);

    @Query("""
        SELECT DISTINCT b FROM Book b
        LEFT JOIN FETCH b.languages
        LEFT JOIN FETCH b.formats
        LEFT JOIN FETCH b.authors
        WHERE :language MEMBER OF b.languages
    """)
    List<Book> findAllByLanguages(@Param("language") String language);


}
