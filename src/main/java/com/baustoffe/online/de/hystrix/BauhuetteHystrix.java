package com.baustoffe.online.de.hystrix;

import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import com.baustoffe.online.de.DataGenerator;
import com.baustoffe.online.de.dto.ArtikelDTO;
import com.baustoffe.online.de.entities.MandantTyp;
import com.baustoffe.online.de.services.BauhuetteRemoteService;
import com.baustoffe.online.de.ui.event.MonkeyEvent;
import com.baustoffe.online.de.ui.event.StatusEvent;
import com.google.common.eventbus.EventBus;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class BauhuetteHystrix extends HystrixCommand<List<ArtikelDTO>> {

    private BauhuetteRemoteService service;

    private List<ArtikelDTO> fallbackCache;

    private EventBus eventBus;

    public BauhuetteHystrix(BauhuetteRemoteService service, EventBus eventBus) {
        super(HystrixCommandGroupKey.Factory.asKey("BauhuetteRemoteService"));
        this.service = service;
        this.eventBus = eventBus;
        cache();
    }

    @Override
    protected List<ArtikelDTO> run() throws Exception {
        return service.getAlleArtikel();
    }

    @Override
    protected List<ArtikelDTO> getFallback() {
        // Fallback Log
        if (eventBus != null) {
            eventBus.post(new MonkeyEvent("Fallback Bauhuette", new Date()));
            eventBus.post(new StatusEvent("error", "b"));
        }
        return fallbackCache;
    }

    private void cache() {
        if (CollectionUtils.isEmpty(fallbackCache)) {
            fallbackCache = new DataGenerator().createRandomArtikel(MandantTyp.BAUHUETTE_CACHE, 10);
        }
    }
}
