package org.reljicb.codingstyle.web.entity;

import org.reljicb.codingstyle.web.beans.CSError;

import javax.persistence.*;

@Entity
@Table(name = ErrorEntity.TABLE_NAME)
public class ErrorEntity {
    public static final String TABLE_NAME = "file_error";

    public static ErrorEntity create(CSError csError, FileEntity fileEntity, ErrorTypeEntity errorType,
            CommitEntity commit) {
        return new ErrorEntity()
                .setColumn(csError.getColumn())
                .setLine(csError.getLine())
                .setFileEntity(fileEntity)
                .setErrorType(errorType)
                .setCommit(commit);
    }

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;

    private Integer column;

    public static final String COMMIT_FK_COLUMN_NAME = "commit_name_id";

    @ManyToOne
    @JoinColumn(name = COMMIT_FK_COLUMN_NAME)
    private CommitEntity commit;

    @ManyToOne
    private ErrorTypeEntity errorType;

    @ManyToOne
    private FileEntity fileEntity;

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
