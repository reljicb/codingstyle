package org.reljicb.codingstyle.web.repository;

import org.reljicb.codingstyle.web.entity.ErrorTypeEntity;
import org.springframework.data.repository.CrudRepository;

public interface ErrorTypeRepository extends CrudRepository<ErrorTypeEntity, String> {

    ErrorTypeEntity findByMessageAndSeverityAndSource(String message, String severity, String source);
}
