package com.baustoffe.online.de.hystrix;

import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import com.baustoffe.online.de.DataGenerator;
import com.baustoffe.online.de.dto.ArtikelDTO;
import com.baustoffe.online.de.entities.MandantTyp;
import com.baustoffe.online.de.services.UbiRemoteService;
import com.baustoffe.online.de.ui.event.MonkeyEvent;
import com.baustoffe.online.de.ui.event.StatusEvent;
import com.google.common.eventbus.EventBus;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class UbiHystrix extends HystrixCommand<List<ArtikelDTO>> {

    private UbiRemoteService service;

    private List<ArtikelDTO> fallbackCache;

    private EventBus eventBus;

    public UbiHystrix(UbiRemoteService service, EventBus eventBus) {
        super(HystrixCommandGroupKey.Factory.asKey("UbiRemoteService"));
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
            eventBus.post(new MonkeyEvent("Fallback Ubi", new Date()));
            eventBus.post(new StatusEvent("error", "u"));
        }
        return fallbackCache;
    }

    private void cache() {
        if (CollectionUtils.isEmpty(fallbackCache)) {
            fallbackCache = new DataGenerator().createRandomArtikel(MandantTyp.UBI_CACHE, 10);
        }
    }
}
