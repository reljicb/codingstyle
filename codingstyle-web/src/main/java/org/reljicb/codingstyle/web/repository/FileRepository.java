package org.reljicb.codingstyle.web.repository;

import org.reljicb.codingstyle.web.entity.FileEntity;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<FileEntity, String> {

    FileEntity findByName(String name);
}
