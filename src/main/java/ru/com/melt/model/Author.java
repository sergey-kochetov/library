package ru.com.melt.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "authors")
@ToString(exclude = "id")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name")
    @NonNull
    private String name;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.PERSIST)
    private Set<Book> books = new HashSet<>();

    public Author(String name) {
        this.name = name;
    }
}
