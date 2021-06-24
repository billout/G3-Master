package com.billoutdgr.g3.repository;

import com.billoutdgr.g3.domain.Candidats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Candidats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidatsRepository extends JpaRepository<Candidats, Long> {}
