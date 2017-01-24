package org.reljicb.checkstyle.checkstyle.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "checkstyle")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckStyle {

    //        @JacksonXmlElementWrapper(localName = "file")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "file")
    private TargetFile[] targetFiles;

    @JacksonXmlProperty(localName = "version")
    private String version;

    public List<TargetFile> getTargetFiles() {
        if (targetFiles == null)
            return Lists.newArrayList();

        return Lists.newArrayList(targetFiles).stream()
                .filter(error -> error != null)
                .collect(Collectors.toList());
    }

    public String getVersion() {
        return version;
    }
}
