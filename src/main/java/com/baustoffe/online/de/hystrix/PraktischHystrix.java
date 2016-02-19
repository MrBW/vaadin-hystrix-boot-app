package com.baustoffe.online.de.hystrix;

import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import com.baustoffe.online.de.DataGenerator;
import com.baustoffe.online.de.dto.ArtikelDTO;
import com.baustoffe.online.de.entities.MandantTyp;
import com.baustoffe.online.de.services.PraktischRemoteService;
import com.baustoffe.online.de.ui.event.MonkeyEvent;
import com.baustoffe.online.de.ui.event.StatusEvent;
import com.google.common.eventbus.EventBus;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class PraktischHystrix extends HystrixCommand<List<ArtikelDTO>> {

    private PraktischRemoteService service;

    private List<ArtikelDTO> fallbackCache;

    private EventBus eventBus;

    public PraktischHystrix(PraktischRemoteService service, EventBus eventBus) {
        super(HystrixCommandGroupKey.Factory.asKey("PraktischRemoteService"));
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
        if (eventBus != null) {
            eventBus.post(new MonkeyEvent("Fallback Praktisch", new Date()));
            eventBus.post(new StatusEvent("error", "p"));
        }
        return fallbackCache;
    }

    private void cache() {
        if (CollectionUtils.isEmpty(fallbackCache)) {
            fallbackCache = new DataGenerator().createRandomArtikel(MandantTyp.PRAKTISCH_CACHE, 10);
        }
    }
}
