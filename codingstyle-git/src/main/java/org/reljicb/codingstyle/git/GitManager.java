package org.reljicb.codingstyle.git;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.spinn3r.log5j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.DepthWalk;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GitManager {
    private static final Logger log = Logger.getLogger();

    final Git git;

    final Repository repo;

    public GitManager(final String repoPath) throws IOException {
        repo = new FileRepository(repoPath + File.separator + ".git");

        git = new Git(repo);
    }

    public Ref checkoutCommit(String commitName) throws GitAPIException {
        return git.checkout().setName(commitName).call();
    }

    public void checkoutRoot(final String branchName) throws GitAPIException {
        git.checkout().setName(branchName).call();
    }

    public ObjectId getCurrentCommit() throws IOException {
        return repo.resolve(Constants.HEAD);
    }

    public List<RevCommit> walkBranch(final String branchName) throws IOException, GitAPIException {
        ObjectId id = repo.resolve(Constants.HEAD);
        String currentCommit = id.getName();

        DepthWalk.RevWalk walk = new DepthWalk.RevWalk(repo, Integer.MAX_VALUE);

        List<Ref> branches = git.branchList().call();
        log.debug("List of all branches: %s",
                Joiner.on(", ").join(branches.stream()
                        .map(branche -> branche.getName())
                        .collect(Collectors.toList())));

        final String BRANCH_MASTER = branches.stream()
                .filter(b -> b.getName().contains(branchName))
                .findFirst()
                .map(b -> b.getName())
                .get();

        //        System.out.println(String.format("Commits of branch: %s:", BRANCH_MASTER));
        //        System.out.println("-------------------------------------");

        Iterable<RevCommit> commits = git.log().all().call();

        List<RevCommit> ret = Lists.newArrayList();
        for (RevCommit commit : commits) {
            boolean foundInThisBranch = false;

            RevCommit targetCommit = walk.parseCommit(repo.resolve(
                    commit.getName()));
            for (Map.Entry<String, Ref> e : repo.getAllRefs().entrySet()) {
                if (e.getKey().startsWith(Constants.R_HEADS)) {
                    if (walk.isMergedInto(targetCommit, walk.parseCommit(
                            e.getValue().getObjectId()))) {
                        String foundInBranch = e.getValue().getName();
                        if (BRANCH_MASTER.equals(foundInBranch)) {
                            foundInThisBranch = true;
                            break;
                        }
                    }
                }
            }

            if (foundInThisBranch) {
                ret.add(commit);
                //                    System.out.println(commit.getName());
                //                    System.out.println(commit.getAuthorIdent().getName());
                //                    System.out.println(new Date(commit.getCommitTime()));
                //                System.out.println(String.format("%s %s", commit.getName(), commit.getShortMessage().trim()));
            }
        }

        //        git.checkout().setName(currentCommit).call();

        return ret;
    }
}
