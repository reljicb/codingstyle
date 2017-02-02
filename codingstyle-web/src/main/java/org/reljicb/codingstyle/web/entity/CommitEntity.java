package org.reljicb.codingstyle.web.entity;

import javax.persistence.*;

@Entity
@Table(name = CommitEntity.TABLE_NAME)
public class CommitEntity {
    public static final String TABLE_NAME = "commit";

    public static CommitEntity create(String commitName) {
        return new CommitEntity()
                .setName(commitName);
    }

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CommitEntity setName(String name) {
        this.name = name;
        return this;
    }
}
