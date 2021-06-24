package com.billoutdgr.g3.repository;

import com.billoutdgr.g3.domain.Jury;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Jury entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JuryRepository extends JpaRepository<Jury, Long> {}
