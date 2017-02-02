package org.reljicb.codingstyle.web.entity;

import org.reljicb.codingstyle.web.beans.TargetFile;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class FileEntity {
    public static FileEntity create(TargetFile targetFile) {
        return new FileEntity()
                .setName(targetFile.getName());
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

    public FileEntity setName(String name) {
        this.name = name;
        return this;
    }
}
