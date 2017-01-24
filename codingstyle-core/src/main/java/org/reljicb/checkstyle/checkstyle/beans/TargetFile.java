package org.reljicb.checkstyle.checkstyle.beans;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class TargetFile {

    @JacksonXmlProperty(localName = "error")
    @JacksonXmlElementWrapper(useWrapping = false)
    private CSError[] errors;

    @JacksonXmlProperty(localName = "name")
    private String name;

    public List<CSError> getErrors () {
        if (errors == null)
            return Lists.newArrayList();

        return Lists.newArrayList(errors).stream()
                .filter(error -> error != null)
                .collect(Collectors.toList());
    }

    public String getName () {
        return name;
    }
}
