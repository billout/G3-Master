package com.billoutdgr.g3.repository;

import com.billoutdgr.g3.domain.Personnes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Personnes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonnesRepository extends JpaRepository<Personnes, Long> {}
