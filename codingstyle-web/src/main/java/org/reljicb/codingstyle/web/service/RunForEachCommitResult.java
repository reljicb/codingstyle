package org.reljicb.codingstyle.web.service;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;

import java.io.IOException;

/**
 * Created by bojanreljic on 31/01/17.
 */
public class RunForEachCommitResult {
    GitService gitService;

    private ObjectId lastActiveCommit;

    public RunForEachCommitResult(ObjectId lastActiveCommit, GitService gitService) {
        this.lastActiveCommit = lastActiveCommit;
        this.gitService = gitService;
    }

    public GitService checkoutLastActive() throws IOException, GitAPIException {
        this.gitService.checkoutCommit(this.lastActiveCommit.getName());
        return this.gitService;
    }

}
