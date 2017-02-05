package org.reljicb.codingstyle.web.beans;

import org.reljicb.codingstyle.web.entity.AuthorEntity;

public class UserErrorCount {
    private AuthorEntity author;

    private UserErrorCount() {
    }

    public static UserErrorCount create(AuthorEntity author) {
        return new UserErrorCount()
                .setAuthor(author);
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    private UserErrorCount setAuthor(AuthorEntity author) {
        this.author = author;
        return this;
    }
}
