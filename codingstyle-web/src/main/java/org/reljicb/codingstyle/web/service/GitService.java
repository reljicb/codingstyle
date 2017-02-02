package org.reljicb.codingstyle.web.service;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.reljicb.codingstyle.git.GitManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GitService {

    @Value("${git.branch_name}")
    private String branchName;

    private GitManager gitManager;

    @Autowired
    public GitService(RepositoryService repositoryService) throws IOException {
        gitManager = new GitManager(repositoryService.getRepoPath());
    }

    public GitService checkoutCommit(String commitName) throws GitAPIException {
        gitManager.checkoutCommit(commitName);
        return this;
    }

    public GitService checkoutRoot() throws IOException, GitAPIException {
        gitManager.checkoutRoot(this.branchName);
        return this;
    }

    public ObjectId getCurrentCommit() throws IOException {
        return gitManager.getCurrentCommit();
    }

    public ListAllCommitsResult listAllCommits() throws IOException, GitAPIException {
        List<RevCommit> revCommitList = gitManager.walkBranch(this.branchName);
        return new ListAllCommitsResult(revCommitList, this);
    }

}
