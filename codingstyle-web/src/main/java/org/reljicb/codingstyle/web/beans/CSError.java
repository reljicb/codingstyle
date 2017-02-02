package org.reljicb.codingstyle.web.beans;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.base.MoreObjects;

public class CSError {
    @JacksonXmlProperty(localName = "column")
    private int column;

    @JacksonXmlProperty(localName = "line")
    private int line;

    @JacksonXmlProperty(localName = "message")
    private String message;

    @JacksonXmlProperty(localName = "severity")
    private String severity;

    @JacksonXmlProperty(localName = "source")
    private String source;

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("line", line)
                .add("column", column)
                .add("severity", severity)
                .add("message", message)
                .add("source", source)
                .toString();
    }
}
