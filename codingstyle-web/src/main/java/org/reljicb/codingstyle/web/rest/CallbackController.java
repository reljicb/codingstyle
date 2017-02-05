package org.reljicb.codingstyle.web.rest;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.spinn3r.log5j.Logger;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.reljicb.codingstyle.web.entity.*;
import org.reljicb.codingstyle.web.repository.*;
import org.reljicb.codingstyle.web.service.CheckstyleService;
import org.reljicb.codingstyle.web.service.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(AbstractController.API_PATH)
public class CallbackController {
    private static final Logger log = Logger.getLogger();

    @Autowired
    private CheckstyleService checkstyleService;

    @Autowired
    private CommitRepository commitRepo;

    @Autowired
    private ErrorRepository errorRepo;

    @Autowired
    private ErrorTypeRepository errorTypeRepo;

    @Autowired
    private FileRepository fileRepo;

    @Autowired
    private GitService gitService;

    @Autowired
    private AuthorRepository authorRepo;

    @RequestMapping(value = "/callback",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object[]>> run() throws GitAPIException, IOException, InterruptedException {

        final boolean[] ignoreTheRest = new boolean[] { false };

        gitService
                .checkoutRoot()
                .listAllCommits()
                .runForEachCommit((final RevCommit commit) -> {
                    if (ignoreTheRest[0])
                        return;

                    log.debug("commit: %s", commit.getName());

                    CommitEntity commitEntity = commitRepo.findByName(commit.getName());
                    if (commitEntity != null) {
                        ignoreTheRest[0] = true;
                        return;
                    }

                    PersonIdent pi = commit.getAuthorIdent();
                    AuthorEntity author = authorRepo.getByNameAndEmail(pi.getName(), pi.getEmailAddress());
                    if (author == null) {
                        author = new AuthorEntity()
                                .setName(pi.getName())
                                .setEmail(pi.getEmailAddress());
                        author = authorRepo.save(author);
                    }

                    commitEntity = commitRepo.save(
                            CommitEntity.create(commit.getName(), commit.getCommitTime(), author));
                    final CommitEntity commitEntityFinal = commitEntity;

                    try {
                        checkstyleService.run()
                                .stream()
                                .forEach(file -> {
                                    String fileName = file.getName();
                                    log.debug("file: %s", fileName);

                                    FileEntity fileEntity = fileRepo.findByName(fileName);
                                    if (fileEntity == null) {
                                        FileEntity fileEntityUnmanged = FileEntity.create(file);
                                        fileEntity = fileRepo.save(fileEntityUnmanged);
                                    }
                                    final FileEntity fileEntityFinal = fileEntity;

                                    file.getErrors()
                                            .stream()
                                            .forEach(error -> {
                                                log.debug("error: %s (%d:%d)",
                                                        error.getMessage(),
                                                        error.getLine(),
                                                        error.getColumn());

                                                String errorMessage = error.getMessage();
                                                ErrorTypeEntity errorTypeEntity = errorTypeRepo
                                                        .findByMessageAndSeverityAndSource(error.getMessage(),
                                                                error.getSeverity(), error.getSource());
                                                if (errorTypeEntity == null) {
                                                    ErrorTypeEntity errorTypeEntityUnmanaged = ErrorTypeEntity
                                                            .create(error);
                                                    errorTypeEntity = errorTypeRepo.save(errorTypeEntityUnmanaged);
                                                }

                                                ErrorEntity errorEntity = ErrorEntity
                                                        .create(error, fileEntityFinal, errorTypeEntity,
                                                                commitEntityFinal);
                                                errorEntity = errorRepo.save(errorEntity);
                                            });
                                });
                    } catch (Exception e) {
                        throw new UncheckedExecutionException(e);
                    }
                })
                .checkoutLastActive();

        List<Object[]> res = errorRepo.getAnalysis();

        log.info(res);

        return ResponseEntity.ok(res);
    }

}
