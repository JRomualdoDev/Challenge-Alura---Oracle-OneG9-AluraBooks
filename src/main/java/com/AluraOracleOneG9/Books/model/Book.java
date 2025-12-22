package com.AluraOracleOneG9.Books.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String title;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Set<String> summaries;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Set<String> subjects;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Set<String> bookshelves;

    @ElementCollection
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language_name")
    private Set<String> languages;

    private boolean copyright;

    private String mediaType;

    @Column(name = "download_count")
    private int downloadCount;

    @ElementCollection
    @CollectionTable(name = "book_formats", joinColumns = @JoinColumn(name = "book_id"))
    @MapKeyColumn(name = "format_key")
    @Column(name = "format_value")
    private Map<String, String> formats;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getSummaries() {
        return summaries;
    }

    public void setSummaries(Set<String> summaries) {
        this.summaries = summaries;
    }

    public Set<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<String> subjects) {
        this.subjects = subjects;
    }

    public Set<String> getBookshelves() {
        return bookshelves;
    }

    public void setBookshelves(Set<String> bookshelves) {
        this.bookshelves = bookshelves;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    public String getMediaTypes() {
        return mediaType;
    }

    public void setMediaTypes(String mediaTypes) {
        this.mediaType = mediaTypes;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Map<String, String> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, String> formats) {
        this.formats = formats;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Stylized header
        sb.append("************************************************************\n");
        sb.append(" 📖 TITLE: ").append(title).append("\n");
        sb.append("************************************************************\n");

        // Basic Information
        sb.append(String.format("  • Language:      %s\n", languages));
        sb.append(String.format("  • Downloads:     %d\n", downloadCount));
        sb.append(String.format("  • Copyright:     %s\n", copyright ? "Yes" : "No"));

        // Authors (Iterating through the list to format each line)
        sb.append("\n  ✍️  AUTHORS:\n");
        if (authors != null && !authors.isEmpty()) {
            for (Author a : authors) {
                sb.append(String.format("     - %s (%d-%d)\n", a.getName(), a.getBirthYear(), a.getDeathYear()));
            }
        } else {
            sb.append("     - (Unknown)\n");
        }

        // Subjects/Categories
        sb.append("\n  📚 SUBJECTS:\n");
        if (subjects != null) {
            for (String subject : subjects) {
                sb.append("     * ").append(subject).append("\n");
            }
        }

        // Bookshelves
        sb.append("\n  🗄️  BOOKSHELVES:\n");
        if (bookshelves != null) {
            for (String shelf : bookshelves) {
                sb.append("     * ").append(shelf).append("\n");
            }
        }

        // Download Formats (Map)
        sb.append("\n  🔗 DOWNLOAD LINKS:\n");
        if (formats != null) {
            formats.forEach((type, url) -> {
                sb.append("     > [").append(type).append("]: ").append(url).append("\n");
            });
        }

        // Summary (Usually long, so placed at the end for better readability)
        sb.append("\n  📝 SUMMARY:\n");
        if (summaries != null && !summaries.isEmpty()) {
            // Grab only the first summary if multiple exist, to avoid clutter
            sb.append("     ").append(summaries.stream().findFirst().get()).append("\n");
        }

        sb.append("____________________________________________________________\n");

        return sb.toString();
    }
}
