package ru.com.melt.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "customers")
@ToString(exclude = "id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String username;

    @OneToMany(mappedBy = "customer")
    private Set<Comment> comments = new HashSet<>();

    public Customer(@NonNull String username) {
        this.username = username;
    }
}
