package com.baustoffe.online.de.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.baustoffe.online.de.entities.Artikel;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Repository
public interface PraktischRepository extends JpaRepository<Artikel, Long> {
    @Override
    @Query("SELECT e FROM Artikel e where e.mandant ='PRAKTISCH'")
    List<Artikel> findAll();
}