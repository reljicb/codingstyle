package org.reljicb.codingstyle.web.entity;

import org.reljicb.codingstyle.web.beans.CSError;

import javax.persistence.*;

@Entity
@Table(name = ErrorEntity.TABLE_NAME)
public class ErrorEntity {

    public static final String TABLE_NAME = "error";

    public static ErrorEntity create(CSError csError, FileEntity fileEntity, ErrorTypeEntity errorType,
            CommitEntity commit) {
        return new ErrorEntity()
                .setColumn(csError.getColumn())
                .setLine(csError.getLine())
                .setFileEntity(fileEntity)
                .setErrorType(errorType)
                .setCommit(commit);
    }

    private Integer column;

    @ManyToOne
    private CommitEntity commit;

    @ManyToOne
    private ErrorTypeEntity errorType;

    @ManyToOne
    private FileEntity fileEntity;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;

    private Integer line;

    public Integer getColumn() {
        return column;
    }

    public CommitEntity getCommit() {
        return commit;
    }

    public ErrorTypeEntity getErrorType() {
        return errorType;
    }

    public FileEntity getFileEntity() {
        return fileEntity;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLine() {
        return line;
    }

    public ErrorEntity setColumn(Integer column) {
        this.column = column;
        return this;
    }

    public ErrorEntity setCommit(CommitEntity commit) {
        this.commit = commit;
        return this;
    }

    public ErrorEntity setErrorType(ErrorTypeEntity errorType) {
        this.errorType = errorType;
        return this;
    }

    public ErrorEntity setFileEntity(FileEntity fileEntity) {
        this.fileEntity = fileEntity;
        return this;
    }

    public ErrorEntity setLine(Integer line) {
        this.line = line;
        return this;
    }
}
