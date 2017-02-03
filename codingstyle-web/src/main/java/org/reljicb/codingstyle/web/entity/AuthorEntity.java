package org.reljicb.codingstyle.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "author")
public class AuthorEntity {
    @GeneratedValue
    @Id
    private Integer id;

    private String name;

    private String email;

    public Integer getId() {
        return id;
    }

    private AuthorEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AuthorEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AuthorEntity setEmail(String email) {
        this.email = email;
        return this;
    }
}
