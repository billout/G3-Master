package com.billoutdgr.g3.repository;

import com.billoutdgr.g3.domain.VoieAcces;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VoieAcces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoieAccesRepository extends JpaRepository<VoieAcces, Long> {}
