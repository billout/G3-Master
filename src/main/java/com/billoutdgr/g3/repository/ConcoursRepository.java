package com.billoutdgr.g3.repository;

import com.billoutdgr.g3.domain.Concours;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Concours entity.
 */
@Repository
public interface ConcoursRepository extends JpaRepository<Concours, Long> {
    @Query(
        value = "select distinct concours from Concours concours left join fetch concours.candidats",
        countQuery = "select count(distinct concours) from Concours concours"
    )
    Page<Concours> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct concours from Concours concours left join fetch concours.candidats")
    List<Concours> findAllWithEagerRelationships();

    @Query("select concours from Concours concours left join fetch concours.candidats where concours.id =:id")
    Optional<Concours> findOneWithEagerRelationships(@Param("id") Long id);
}
