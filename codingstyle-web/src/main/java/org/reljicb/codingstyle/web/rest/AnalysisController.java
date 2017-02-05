package org.reljicb.codingstyle.web.rest;

import org.reljicb.codingstyle.web.beans.UserErrorCount;
import org.reljicb.codingstyle.web.entity.AuthorEntity;
import org.reljicb.codingstyle.web.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AbstractController.API_PATH)
public class AnalysisController {

    @Autowired
    AuthorRepository authorRepo;

    @RequestMapping(value = "/user/{name}/{email}/error_count",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserErrorCount> getUserErrorCount(@PathVariable String name, @PathVariable String email) {

        AuthorEntity author = authorRepo.getByNameAndEmail(name, email);

        UserErrorCount res = UserErrorCount.create(author);

        return ResponseEntity.ok(res);
    }
}
