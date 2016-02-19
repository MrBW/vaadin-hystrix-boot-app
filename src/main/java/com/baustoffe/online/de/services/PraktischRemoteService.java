package com.baustoffe.online.de.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baustoffe.online.de.dto.ArtikelDTO;
import com.baustoffe.online.de.entities.Artikel;
import com.baustoffe.online.de.mapper.ArtikelMapper;
import com.baustoffe.online.de.repository.PraktischRepository;
import com.baustoffe.online.de.utils.ChaosMonkey;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Service
public class PraktischRemoteService {

    @Autowired
    private ArtikelMapper mapper;

    @Autowired
    private PraktischRepository repository;

    @Autowired
    private ChaosMonkey chaosMonkey;

    public List<ArtikelDTO> getAlleArtikel() {
        List<Artikel> artikelList = repository.findAll();

        chaosMonkey.callForTrouble("Praktisch");

        return mapper.mapArtikelList(artikelList);

    }
}
