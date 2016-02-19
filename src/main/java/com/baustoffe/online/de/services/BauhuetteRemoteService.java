package com.baustoffe.online.de.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baustoffe.online.de.dto.ArtikelDTO;
import com.baustoffe.online.de.entities.Artikel;
import com.baustoffe.online.de.mapper.ArtikelMapper;
import com.baustoffe.online.de.repository.BauhuetteRepository;
import com.baustoffe.online.de.utils.ChaosMonkey;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Service
public class BauhuetteRemoteService {

    @Autowired
    private ArtikelMapper mapper;

    @Autowired
    private BauhuetteRepository repository;

    @Autowired
    private ChaosMonkey chaosMonkey;

    public List<ArtikelDTO> getAlleArtikel() {

        // Frag den Monkey nach Ärger...
        chaosMonkey.callForTrouble("Bauhuette");

        List<Artikel> artikelList = repository.findAll();

        return mapper.mapArtikelList(artikelList);

    }
}
