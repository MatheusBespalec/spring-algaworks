package com.matheus.algaworks.jpa;

import com.matheus.algaworks.jpa.domain.model.Restaurant;
import com.matheus.algaworks.jpa.domain.repository.RestaurantRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(JpaApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        RestaurantRepository restaurantRepository = applicationContext.getBean(RestaurantRepository.class);

        List<Restaurant> restaurants = restaurantRepository.getAll();
        for (Restaurant restaurant : restaurants) {
            System.out.printf("%s - %f - %s\n", restaurant.getName(), restaurant.getFreightRate(), restaurant.getKitchen().getName());
        }
    }
}
