package com.baustoffe.online.de.services;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import com.baustoffe.online.de.dto.ArtikelDTO;
import com.baustoffe.online.de.hystrix.BauhuetteHystrix;
import com.baustoffe.online.de.hystrix.PraktischHystrix;
import com.baustoffe.online.de.hystrix.UbiHystrix;
import com.google.common.eventbus.EventBus;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Service
public class BaushopService {

    private static final Logger log = LoggerFactory.getLogger(BaushopService.class);

    @Autowired
    private BauhuetteRemoteService bauhuetteRemoteService;

    @Autowired
    private PraktischRemoteService praktischRemoteService;

    @Autowired
    private UbiRemoteService ubiRemoteService;

    @Autowired
    private EventBus eventBus;

    /***
     * Liefert alle Artikel immer zuverlässig und ohne Fehler
     * @return Liste von ArtikelDTOs
     */
    public List<ArtikelDTO> getAlleArtikel() {

        List<ArtikelDTO> bauhuetteArtikel = bauhuetteRemoteService.getAlleArtikel();
        List<ArtikelDTO> praktischArtikel = praktischRemoteService.getAlleArtikel();
        List<ArtikelDTO> ubiArtikel = ubiRemoteService.getAlleArtikel();

        return collectAll(bauhuetteArtikel, praktischArtikel, ubiArtikel);

    }

    private List collectAll(List<ArtikelDTO> bauhuetteArtikel, List<ArtikelDTO> praktischArtikel, List<ArtikelDTO> ubiArtikel) {
        List sum = ListUtils.sum(bauhuetteArtikel, praktischArtikel);
        return ListUtils.sum(sum, ubiArtikel);
    }

    /***
     * Hystrix Synchron Commands
     * @return Liste aller ArtieklDTOs
     */
    public List<ArtikelDTO> getAlleArtikelHystrixSynchron() {

        List<ArtikelDTO> bauhuetteArtikel = new BauhuetteHystrix(bauhuetteRemoteService, eventBus).execute();
        List<ArtikelDTO> praktischArtikel = new PraktischHystrix(praktischRemoteService, eventBus).execute();
        List<ArtikelDTO> ubiArtikel = new UbiHystrix(ubiRemoteService, eventBus).execute();

        return collectAll(bauhuetteArtikel, praktischArtikel, ubiArtikel);

    }

    /***
     * Hystrix Asynchron Commands
     * @return Liste aller ArtieklDTOs
     */
    public List<ArtikelDTO> getAlleArtikelHystrixQueue() {

        Future<List<ArtikelDTO>> futureBauhuette = new BauhuetteHystrix(bauhuetteRemoteService, eventBus).queue();
        Future<List<ArtikelDTO>> futurePraktisch = new PraktischHystrix(praktischRemoteService, eventBus).queue();
        Future<List<ArtikelDTO>> futureUbi = new UbiHystrix(ubiRemoteService, eventBus).queue();

        try {
            List<ArtikelDTO> bauhuetteArtikelDTOs = futureBauhuette.get();
            List<ArtikelDTO> praktischArtikelDTOs = futurePraktisch.get();
            List<ArtikelDTO> ubiArtikelDTOs = futureUbi.get();

            return collectAll(bauhuetteArtikelDTOs, praktischArtikelDTOs, ubiArtikelDTOs);
        } catch (InterruptedException | ExecutionException e) {
            return Collections.emptyList();
        }

    }

    public List<ArtikelDTO> getAlleArtikelHystrixReactive() {
        Observable<List<ArtikelDTO>> obs1 = new BauhuetteHystrix(bauhuetteRemoteService, eventBus).observe();
        Observable<List<ArtikelDTO>> obs2 = new PraktischHystrix(praktischRemoteService, eventBus).observe();
        Observable<List<ArtikelDTO>> obs3 = new UbiHystrix(ubiRemoteService, eventBus).observe();

        // // Parallele Abfrage aller System mit einem kombinierten Ergebnis

        // Observable.combineLatest(obs1,obs2,obs3,CollectionUtils::union).toBlocking().first();
        // Collection<ArtikelDTO> combinedResult = Observable
        // .combineLatest(obs1, obs2, obs3,CollectionUtils::union)
        // .toBlockingObservable()
        // .first();

        return Collections.emptyList();
    }

}
