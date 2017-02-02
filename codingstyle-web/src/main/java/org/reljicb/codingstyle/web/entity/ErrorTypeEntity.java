package org.reljicb.codingstyle.web.entity;

import org.reljicb.codingstyle.web.beans.CSError;

import javax.persistence.*;

@Entity
@Table(name = "error_type")
public class ErrorTypeEntity {

    public static ErrorTypeEntity create(CSError csError) {
        return new ErrorTypeEntity()
                .setMessage(csError.getMessage())
                .setSeverity(csError.getSeverity())
                .setSource(csError.getSource());
    }

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;

    private String message;

    private String severity;

    private String source;

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getSeverity() {
        return severity;
    }

    public String getSource() {
        return source;
    }

    private ErrorTypeEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorTypeEntity setSeverity(String severity) {
        this.severity = severity;
        return this;
    }

    public ErrorTypeEntity setSource(String source) {
        this.source = source;
        return this;
    }
}
