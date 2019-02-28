package ru.com.melt.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "genres")
@ToString(exclude = "id")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "genre_name")
    @NonNull
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }
}
