package ru.com.melt.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "book_comments")
@ToString(exclude = "id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "comment_text")
    private String commentTexet;

    @Column(name = "comment_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentData;

    public Comment() {
        this.commentData = new Date();
    }

    public Comment(Customer customer, Book book, String commentTexet) {
        this.customer = customer;
        this.book = book;
        this.commentTexet = commentTexet;
        this.commentData = new Date();
    }
}
