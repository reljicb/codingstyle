package org.reljicb.codingstyle.web.repository;

import org.reljicb.codingstyle.web.entity.ErrorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ErrorEntityRepository extends CrudRepository<ErrorEntity, Integer> {

    String query = "select count(e.commit) as error_count, e.commit.name "
            + "from ErrorEntity e "
            + "group by e.commit "
            + "order by e.commit.id ";

    @Query(value = query, nativeQuery = false)
    List<Object[]> getAnalysis();
}
