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
    private String commentText;

    @Column(name = "comment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate;

    public Comment() {
        this.commentDate = new Date();
    }

    public Comment(Customer customer, Book book, String commentText) {
        this.customer = customer;
        this.book = book;
        this.commentText = commentText;
        this.commentDate = new Date();
    }
}
