package org.reljicb.codingstyle.web.repository;

import org.reljicb.codingstyle.web.entity.CommitEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommitRepository extends CrudRepository<CommitEntity, Integer> {

    CommitEntity findByName(String name);
}
