package com.baustoffe.online.de;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.baustoffe.online.de.dto.ArtikelDTO;
import com.baustoffe.online.de.entities.Artikel;
import com.baustoffe.online.de.entities.MandantTyp;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Component
public class DataGenerator {

    private Random rand = new Random();

    public void insertDummyData(JpaRepository<Artikel, Long> repository, MandantTyp typ) {

        Artikel artikel = new Artikel(randomAmount(5, 59), "Sand", "Spielsand", randomPrice(0.99, 3.99), typ);
        Artikel artikel1 = new Artikel(randomAmount(1, 23), "Beton", "Schnell, sauber, super...", randomPrice(0.99, 2.99), typ);
        Artikel artikel2 =
            new Artikel(randomAmount(10, 99), "Mineralwolle", "Super mega warm...", randomPrice(0.99, 5.99), typ);
        Artikel artikel3 =
            new Artikel(randomAmount(13, 1000), "Farbe blau", "Farbe im 10 Liter Eimer", randomPrice(3.99, 4.99), typ);
        Artikel artikel4 =
            new Artikel(randomAmount(13, 1000), "Farbe gelb", "Farbe im 10 Liter Eimer", randomPrice(3.99, 4.99), typ);
        Artikel artikel5 =
            new Artikel(randomAmount(13, 1000), "Farbe orange", "Farbe im 10 Liter Eimer", randomPrice(2.99, 4.99), typ);
        Artikel artikel6 = new Artikel(randomAmount(13, 1000), "Rauhfaser", "Tapete 30m Rolle", randomPrice(5.99, 14.99), typ);
        Artikel artikel7 = new Artikel(randomAmount(3944, 10000), "Klemmfilz", "Ultimate", randomPrice(18.99, 24.99), typ);
        Artikel artikel8 = new Artikel(randomAmount(5000, 10000), "Zement", "30kg Sack", randomPrice(2.49, 4.99), typ);
        Artikel artikel9 = new Artikel(randomAmount(3000, 45000), "Gipskartonplatte", "Gipskartonplatte 1200mm x 600mm",
            randomPrice(2.99, 3.99), typ);
        Artikel artikel10 =
            new Artikel(randomAmount(4567, 45900), "Glasbaustein", "Glasbaustein weiss", randomPrice(2.99, 4.99), typ);

        repository.save(artikel);
        repository.save(artikel1);
        repository.save(artikel2);
        repository.save(artikel3);
        repository.save(artikel4);
        repository.save(artikel5);
        repository.save(artikel6);
        repository.save(artikel7);
        repository.save(artikel8);
        repository.save(artikel9);
        repository.save(artikel10);

    }

    private double randomPrice(double min, double max) {
        return RandomUtils.nextDouble(min, max);
    }

    private long randomAmount(long min, long max) {

        return RandomUtils.nextLong(min, max);
    }

    public List<ArtikelDTO> createRandomArtikel(MandantTyp typ, int anzahl) {

        List<ArtikelDTO> list = new LinkedList<>();
        for (int i = 1; i <= anzahl; i++) {
            list.add(new ArtikelDTO(randomAmount(1000l, 10000l), randomAmount(1, 1000), "Artikel", randomPrice(2, 10), typ));

        }
        return list;
    }

}
