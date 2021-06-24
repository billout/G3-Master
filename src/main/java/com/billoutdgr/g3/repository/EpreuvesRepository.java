package com.billoutdgr.g3.repository;

import com.billoutdgr.g3.domain.Epreuves;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Epreuves entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpreuvesRepository extends JpaRepository<Epreuves, Long> {}
