package org.reljicb.codingstyle.web.entity;

import javax.persistence.*;

@Entity
@Table(name = CommitEntity.TABLE_NAME)
public class CommitEntity {
    public static final String TABLE_NAME = "commit";

    public static CommitEntity create(String commitName, int commitTime, AuthorEntity author) {
        return new CommitEntity()
                .setName(commitName)
                .setCommitTime(commitTime)
                .setAuthor(author);
    }

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;

    private String name;

    private int commitTime;

    @ManyToOne
    private AuthorEntity author;

    public Integer getId() {
        return id;
    }

    private CommitEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CommitEntity setName(String name) {
        this.name = name;
        return this;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public CommitEntity setAuthor(AuthorEntity author) {
        this.author = author;
        return this;
    }

    public int getCommitTime() {
        return commitTime;
    }

    public CommitEntity setCommitTime(int commitTime) {
        this.commitTime = commitTime;
        return this;
    }
}
