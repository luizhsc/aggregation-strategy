package com.camel;

import com.camel.route.ItemNumbersRoute;
import com.camel.util.UtilCamel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AggregationStrategyApplication {

	@Autowired
	private UtilCamel utilCamel;

	public static void main(String[] args) {
		SpringApplication.run(AggregationStrategyApplication.class, args);
	}

	@PostConstruct
	public void initContext() throws Exception {
		List<Object> listRoutes = new ArrayList<>();
		listRoutes.add(new ItemNumbersRoute());
		utilCamel.runContextCamel(listRoutes);
	}

}
