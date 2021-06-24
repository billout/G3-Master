package com.billoutdgr.g3.repository;

import com.billoutdgr.g3.domain.Niveaux;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Niveaux entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NiveauxRepository extends JpaRepository<Niveaux, Long> {}
