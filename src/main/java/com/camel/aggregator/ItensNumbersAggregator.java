package com.camel.aggregator;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.ArrayList;
import java.util.List;

public class ItensNumbersAggregator implements AggregationStrategy {

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        System.out.println(newExchange.getIn().getHeader("x-banco"));
        if (oldExchange != null) {
            System.out.println(" old -> " + oldExchange.getIn().getBody());
        }
        System.out.println(" new -> " + newExchange.getIn().getBody());
        Object newBody = newExchange.getIn().getBody();
        List<Object> list = new ArrayList<>();
        if (oldExchange == null) {
            list = new ArrayList<Object>();
            list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(newBody);
            return oldExchange;
        }
    }
}