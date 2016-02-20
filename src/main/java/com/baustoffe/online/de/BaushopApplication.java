package com.baustoffe.online.de;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import com.baustoffe.online.de.configuration.DataSourceConfiguration;
import com.baustoffe.online.de.configuration.HystrixConfiguration;
import com.baustoffe.online.de.entities.MandantTyp;
import com.baustoffe.online.de.repository.BauhuetteRepository;
import com.baustoffe.online.de.repository.PraktischRepository;
import com.baustoffe.online.de.repository.UbiRepository;
import com.baustoffe.online.de.scheduler.ServiceCallScheduler;
import com.google.common.eventbus.EventBus;

@SpringBootApplication
@Import({DataSourceConfiguration.class, HystrixConfiguration.class})
public class BaushopApplication {

    private static final Logger log = LoggerFactory.getLogger(BaushopApplication.class);

    @Autowired
    private DataGenerator generator;

    @Autowired
    private ServiceCallScheduler serviceCallScheduler;

    public static void main(String[] args) {
        SpringApplication.run(BaushopApplication.class, args);
    }

    @Bean
    public EventBus eventBus() {
        return new EventBus("BaushopBus");
    }

    @Bean
    public CommandLineRunner demo(BauhuetteRepository repository, PraktischRepository praktischRepository,
            UbiRepository ubiRepository) {
        return (args) -> {
            log.info("Starte Artikel Generierung...");

            generator.insertDummyData(repository, MandantTyp.BAUHUETTE);
            generator.insertDummyData(praktischRepository, MandantTyp.PRAKTISCH);
            generator.insertDummyData(ubiRepository, MandantTyp.UBI);

            log.info("Artikel wurden in allen Repos erzeugt");

            log.info("Artikel in Repo Bauhuette: " + repository.count());
            log.info("Artikel in Repo Praktisch:" + praktischRepository.count());
            log.info("Artikel in Repo Ubi:" + ubiRepository.count());

        };
    }
}
