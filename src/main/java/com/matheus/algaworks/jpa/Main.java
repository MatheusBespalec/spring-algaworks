package com.matheus.algaworks.jpa;

import com.matheus.algaworks.jpa.domain.model.Kitchen;
import com.matheus.algaworks.jpa.domain.repository.KitchenRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(JpaApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        KitchenRepository kitchenRepository = applicationContext.getBean(KitchenRepository.class);

        Kitchen kitchen = new Kitchen();
        kitchen.setId(1L);
        kitchenRepository.remove(kitchen);
    }
}
