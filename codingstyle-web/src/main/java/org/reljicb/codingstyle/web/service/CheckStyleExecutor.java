package org.reljicb.codingstyle.web.service;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.Lists;
import com.spinn3r.log5j.Logger;
import org.apache.commons.exec.*;
import org.reljicb.codingstyle.web.beans.CheckStyle;
import org.reljicb.codingstyle.web.beans.TargetFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class CheckStyleExecutor {
    private static final Logger log = Logger.getLogger();

    private static String reformatPath(final String path) {
        if (File.separator.equals("\\")) {
            return path.replace("/C:/", "c:\\").replace("/", "\\");
        }
        return path;
    }

    final String checkStylePath;

    final String rulesXmlPath;

    public CheckStyleExecutor() {
        ClassLoader classLoader = CheckStyleExecutor.class.getClassLoader();

        URL libResource = classLoader.getResource("./lib");
        checkStylePath = reformatPath(libResource.getPath());

        rulesXmlPath = reformatPath(
                classLoader.getResource("./rules").getPath());
    }

    public List<TargetFile> run(final String sourcesPath)
            throws IOException, InterruptedException {
        final String javaHome = System.getProperty("java.home");

        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        PumpStreamHandler psh = new PumpStreamHandler(stdout, stderr);

        Executor executor = new DaemonExecutor();
        executor.setStreamHandler(psh);

        ExecuteWatchdog watchdog = new ExecuteWatchdog(15000);
        executor.setWatchdog(watchdog);
        //        executor.setExitValues(new int[] { 0, 255 });

        int exitValue = -1;
        try {
            exitValue = executor.execute(new CommandLine(javaHome + File.separator
                    + "bin" + File.separator + "java")
                    .addArgument("-jar")
                    .addArgument(String.format("%s/checkstyle-7.4-all.jar", checkStylePath))
                    .addArgument("-c")
                    .addArgument(String.format("%s/google_checks.xml", rulesXmlPath))
                    .addArgument("-f").addArgument("xml")
                    .addArgument(sourcesPath)
            );
        } finally {
            log.debug("exit code: %d", exitValue);
            log.debug("out: %s", stdout.toString());
            log.error("err: %s", stderr.toString());
        }

        if (exitValue == 0) {
            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);

            XmlMapper xmlMapper = new XmlMapper(module);

            CheckStyle checkStyle = xmlMapper.readValue(stdout.toString(), CheckStyle.class);

            List<TargetFile> ret = checkStyle.getTargetFiles().stream()
                    .filter(file -> file.getName().endsWith(".java"))
                    .collect(Collectors.toList());

            return ret;
        }

        return Lists.newArrayList();
    }
}
