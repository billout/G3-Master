package com.billoutdgr.g3.repository;

import com.billoutdgr.g3.domain.Formations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Formations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormationsRepository extends JpaRepository<Formations, Long> {}
