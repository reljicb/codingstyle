package org.reljicb.codingstyle.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {
    @Value("${git.path}")
    private String gitRepositoryPath;

    @Value("${git.rel_path_to_source}")
    private String sourcePathRel;

    public String getRepoPath() {
        return gitRepositoryPath;
    }

    public String getSourcePath() {
        return gitRepositoryPath + sourcePathRel;
    }
}
