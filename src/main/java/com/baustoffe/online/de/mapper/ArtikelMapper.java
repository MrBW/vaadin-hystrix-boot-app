package com.baustoffe.online.de.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import com.baustoffe.online.de.dto.ArtikelDTO;
import com.baustoffe.online.de.entities.Artikel;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Component
public class ArtikelMapper {

    public ArtikelDTO mapArtikel(Artikel artikel) {
        Assert.notNull(artikel);

        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");

        ArtikelDTO dto = new ArtikelDTO();
        dto.setAnzahlBestand(artikel.getAnzahlBestand());
        dto.setId(artikel.getId());
        dto.setName(artikel.getName());
        dto.setPreis(artikel.getPreis());
        dto.setMandant(artikel.getMandant().name());
        dto.setTimestamp(df.format(new Date()));

        return dto;
    }

    public List<ArtikelDTO> mapArtikelList(List<Artikel> list) {
        List<ArtikelDTO> dtoList = new LinkedList<>();

        for (Artikel artikel : list) {
            dtoList.add(mapArtikel(artikel));
        }
        return dtoList;
    }
}
