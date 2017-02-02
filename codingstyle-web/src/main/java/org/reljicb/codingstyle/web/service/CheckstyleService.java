package org.reljicb.codingstyle.web.service;

import org.reljicb.codingstyle.web.beans.TargetFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CheckstyleService {
    private CheckStyleExecutor checkStyleExecutor;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    public CheckstyleService(RepositoryService repositoryService) {
        checkStyleExecutor = new CheckStyleExecutor();
    }

    public List<TargetFile> run() throws IOException, InterruptedException {
        return checkStyleExecutor.run(repositoryService.getSourcePath());
    }
}
