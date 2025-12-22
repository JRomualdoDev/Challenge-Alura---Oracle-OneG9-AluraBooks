package com.AluraOracleOneG9.Books.repository;

import com.AluraOracleOneG9.Books.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameContainingIgnoreCase(String name);

    @Query("SELECT a FROM Author a WHERE :year BETWEEN a.birthYear AND a.deathYear")
    List<Author> findAuthorAliveIn(@Param("year") int year);
}
