package org.reljicb.codingstyle.checkstyle;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.Lists;
import org.apache.commons.exec.*;
import org.reljicb.checkstyle.checkstyle.beans.CheckStyle;
import org.reljicb.checkstyle.checkstyle.beans.TargetFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CheckStyleExecutor {

    final String CHECK_STYLE_PATH = reformatPath(this.getClass().getClassLoader().getResource("./lib").getPath());

    final String RULES_XML_PATH = reformatPath(this.getClass().getClassLoader().getResource("./rules").getPath());

    private static String reformatPath(final String path) {
        if (File.separator.equals("\\")) {
            return path.replace("/C:/", "c:\\").replace("/", "\\");
        }
        return path;
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
                    .addArgument(String.format("%s/checkstyle-7.4-all.jar", CHECK_STYLE_PATH))
                    .addArgument("-c")
                    .addArgument(String.format("%s/google_checks.xml", RULES_XML_PATH))
                    .addArgument("-f").addArgument("xml")
                    .addArgument(sourcesPath)
            );
        } finally {
            System.out.println(String.format("exit code: %d", exitValue));
            System.out.println(String.format("out: %s", stdout.toString()));
            System.err.println(String.format("err: %s", stderr.toString()));
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
