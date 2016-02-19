package com.baustoffe.online.de.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Configuration
@ConditionalOnExpression(value = "${hystrix.enabled:true}")
public class HystrixConfiguration {

    @Bean
    @ConditionalOnClass(HystrixCommandAspect.class)
    public HystrixCommandAspect hystrixCommandAspect() {
        return new HystrixCommandAspect();
    }

    @Bean
    @ConditionalOnClass(HystrixMetricsStreamServlet.class)
    @ConditionalOnExpression(value = "${hystrix.streamEnabled:true}")
    public ServletRegistrationBean hystrixStreamServlet() {
        return new ServletRegistrationBean(new HystrixMetricsStreamServlet(), "/hystrix.stream");
    }

}
