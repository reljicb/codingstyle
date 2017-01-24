package org.reljicb.codingstyle.web.utils;

public class RepoWrapper {
    private String repoPath;

    private String sourcePathRel = "/src/main/java";

    public static RepoWrapper create(String repoPath) {
        return new RepoWrapper(repoPath);
    }

    private RepoWrapper(String repoPath) {
        this.repoPath = repoPath;
    }

    public String getRepoPath() {
        return repoPath;
    }

    public String getSourcePathRel() {
        return repoPath + sourcePathRel;
    }

    public RepoWrapper setSourcePathRel(String sourcePathRel) {
        this.sourcePathRel = sourcePathRel;
        return this;
    }
}
