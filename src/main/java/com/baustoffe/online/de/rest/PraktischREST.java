package com.baustoffe.online.de.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baustoffe.online.de.dto.ArtikelDTO;
import com.baustoffe.online.de.mapper.ArtikelMapper;
import com.baustoffe.online.de.repository.PraktischRepository;

/**
 * @author Benjamin Wilms (xd98870)
 */
@RestController
@RequestMapping("/praktisch")
public class PraktischREST {
    @Autowired
    private PraktischRepository repository;

    @Autowired
    private ArtikelMapper mapper;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<ArtikelDTO> getAlleArtikel() {
        return mapper.mapArtikelList(repository.findAll());

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ArtikelDTO findOne(@PathVariable(value = "id") Long id) {
        return mapper.mapArtikel(repository.findOne(id));
    }
}
