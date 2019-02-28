package ru.com.melt.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "books")
@ToString(exclude = "id")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "books_id"),
            inverseJoinColumns = @JoinColumn(name = "authors_id"))
    private Set<Author> authors;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    public Book(@NonNull String title, Genre genre, Set<Author> authors) {
        this.title = title;
        this.genre = genre;
        this.authors = authors;
    }
}
