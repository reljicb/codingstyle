package org.reljicb.codingstyle.git;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.util.List;

public class Main {
    private static final String BB_BACKBONE = "/Users/bojanreljic/development/workspace/bb-backbone";

    private static final String REPO_PATH = BB_BACKBONE;

    private static final String TEST = "/Users/bojanreljic/tmp/git-test/.git";

    public static void main (String[] args) throws IOException, GitAPIException {
        GitManager app = new GitManager();

        List<RevCommit> commits = app.walkBranch("master");
        commits.stream()
                .forEach(c -> System.out.println(String.format("%s %s", c.getName(), c.getShortMessage().trim())));

        for (RevCommit commit : commits) {
            Ref ref = app.checkoutCommit(commit);
            if (ref != null)
                System.out.println(String.format("ref name: ", ref.getName()));
        }
    }
}
