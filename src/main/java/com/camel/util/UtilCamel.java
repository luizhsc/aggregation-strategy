package com.camel.util;


import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UtilCamel {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void runContextCamel(List<Object> listRoutes) throws Exception {
        logger.info("*********** Iniciando o contexto do Camel");
        CamelContext ctx = new DefaultCamelContext();
        listRoutes.stream().forEach(i -> {
            try {
                ctx.addRoutes((RoutesBuilder) i);
            } catch (Exception e) {
                logger.error("*********** Erro");
                e.printStackTrace();
            }
        });
        ctx.start();
        logger.info("*********** Sucesso - contexto do Camel executando... ***********");
    }
}
