package com.example.service_voiture;


import com.example.service_voiture.entities.Car;
import com.example.service_voiture.repositories.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient  // Active l'enregistrement auprès d'Eureka
public class ServiceVoitureApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVoitureApplication.class, args);
    }

    /**
     * Configure RestTemplate pour la communication inter-services
     * @return Instance configurée de RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Configuration des timeouts
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);  // 5 secondes pour la connexion
        requestFactory.setReadTimeout(5000);     // 5 secondes pour la lecture

        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
    @Bean
    CommandLineRunner initDatabase(CarRepository carRepository) {
        return args -> {
            carRepository.save(new Car(null, "1234-A", "Clio", "Renault", 1L));
            carRepository.save(new Car(null, "5678-B", "Golf", "Volkswagen", 2L));
            carRepository.save(new Car(null, "9999-C", "208", "Peugeot", 3L));
            carRepository.save(new Car(null, "1234-A", "Clio", "Renault", 4L));
            carRepository.save(new Car(null, "5678-B", "Golf", "Volkswagen", 5L));
            carRepository.save(new Car(null, "9999-C", "208", "Peugeot", 6L));
            carRepository.save(new Car(null, "1234-A", "Clio", "Renault", 7L));
            carRepository.save(new Car(null, "5678-B", "Golf", "Volkswagen", 8L));
            carRepository.save(new Car(null, "9999-C", "208", "Peugeot", 9L));

        };
    }


}