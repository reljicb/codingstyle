package org.reljicb.codingstyle.web.rest;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.reljicb.checkstyle.checkstyle.beans.CSError;
import org.reljicb.checkstyle.checkstyle.beans.TargetFile;
import org.reljicb.codingstyle.checkstyle.CheckStyleExecutor;
import org.reljicb.codingstyle.git.GitManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CallbackController {

    @RequestMapping(value = "/callback",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> run() throws GitAPIException, IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();

        GitManager app = new GitManager();

        List<RevCommit> commits = app.walkBranch("master");
        commits.stream()
                .forEach(c -> sb.append(String.format("%s %s", c.getName(), c.getShortMessage().trim())));

        //        for (RevCommit commit : commits) {
        //            Ref ref = app.checkoutCommit(commit);
        //            if (ref != null)
        //                System.out.println(String.format("ref name: ", ref.getName()));
        //        }

        CheckStyleExecutor checkStyleExecutor = new CheckStyleExecutor();
        List<TargetFile> ret = checkStyleExecutor.run();
        ret.stream()
                .forEach(targetFile -> {
                            sb.append(targetFile.getName());
                            sb.append("------------------------");
                            targetFile.getErrors().stream()
                                    .map(CSError::toString)
                                    .forEach(e -> sb.append(e));
                        }
                );

        return ResponseEntity.ok(sb.toString());
    }
}