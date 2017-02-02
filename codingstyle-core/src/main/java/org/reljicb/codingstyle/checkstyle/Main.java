//package org.reljicb.codingstyle.checkstyle;
//
//import org.reljicb.codingstyle.web.beans.CSError;
//import org.reljicb.codingstyle.web.beans.TargetFile;
//
//import java.io.IOException;
//import java.util.List;
//
//public class Main {
//    public static void main (String[] args) throws IOException, InterruptedException {
//        CheckStyleExecutor checkStyleExecutor = new CheckStyleExecutor();
//        List<TargetFile> ret = checkStyleExecutor.run();
//        ret.forEach(targetFile ->
//                {
//                    System.out.println(targetFile.getName());
//                    System.out.println("------------------------");
//                    targetFile.getErrors().stream()
//                            .map(CSError::toString)
//                            .forEach(System.out::println);
//                }
//        );
//
//    }
//}
