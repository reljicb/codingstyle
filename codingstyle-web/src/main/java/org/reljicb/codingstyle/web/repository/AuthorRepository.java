package org.reljicb.codingstyle.web.repository;

import org.reljicb.codingstyle.web.entity.AuthorEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Integer> {

    AuthorEntity getByEmail(String email);

    AuthorEntity getByNameAndEmail(String name, String email);
}
