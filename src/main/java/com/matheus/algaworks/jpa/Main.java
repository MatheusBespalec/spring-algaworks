package com.matheus.algaworks.jpa;

import com.matheus.algaworks.jpa.dao.KitchenDAO;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(JpaApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        KitchenDAO kitchenDAO = applicationContext.getBean(KitchenDAO.class);
        kitchenDAO.getAll().forEach(kitchen -> System.out.println(kitchen.getName()));
    }
}
