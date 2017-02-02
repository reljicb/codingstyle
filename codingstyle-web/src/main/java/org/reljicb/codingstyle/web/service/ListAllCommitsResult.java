package org.reljicb.codingstyle.web.service;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by bojanreljic on 31/01/17.
 */
public class ListAllCommitsResult {
    GitService gitService;

    List<RevCommit> revCommitList;

    public ListAllCommitsResult(List<RevCommit> revCommitList, GitService gitService) {
        this.revCommitList = revCommitList;
        this.gitService = gitService;
    }

    public RunForEachCommitResult runForEachCommit(Consumer<RevCommit> revCommitConsumer)
            throws IOException {

        ObjectId lastActiveCommit = this.gitService.getCurrentCommit();
        RunForEachCommitResult ret = new RunForEachCommitResult(lastActiveCommit, gitService);

        this.revCommitList.stream()
                .forEach(revCommitConsumer);

        return ret;
    }
}
