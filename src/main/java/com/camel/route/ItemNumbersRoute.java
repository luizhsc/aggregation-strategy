package com.camel.route;

import com.camel.aggregator.ItensNumbersAggregator;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;

import java.util.UUID;

public class ItemNumbersRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
            from("timer:numbers-timer?period=30000")
                    .process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                            ProducerTemplate template = exchange.getContext().createProducerTemplate();
                            String id = UUID.randomUUID().toString();
                            exchange.getIn().setHeader("id", id);
                            exchange.getIn().setHeader("x-banco", "123456789");
                            log.info("------------ Início do loop ------------");
                            for (int i =1; i <=10; i++) {
                                exchange.getIn().setBody(i);
                                log.info("Arrived! -> " + exchange.getIn().getBody());
                                Thread.sleep(1000);
                                template.send("seda:aggregate", exchange);
                                template.sendBodyAndHeader("activemq:aggregator-ex",
                                        "<hello>world!</hello>",
                                        "x-security", "Gol22wwed");
                                log.info("------------ Item log " + i + " ------------");
                            }
                        }
                    });

            from("seda:aggregate")
                    .aggregate(header("id"), new ItensNumbersAggregator()).completionSize(5).completionTimeout(1000)
                    .process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                            log.info(exchange.getIn().getHeader("id") + " : " + exchange.getIn().getBody());
                        }
                    });
    }
}
