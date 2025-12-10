package com.example.service_voiture.services;

import com.example.service_voiture.entities.Car;
import com.example.service_voiture.models.CarResponse;
import com.example.service_voiture.models.Client;
import com.example.service_voiture.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * URL du service client via la Gateway
     * Remarque: Dans un environnement de production, cette URL serait configurée
     * dans le fichier de propriétés ou récupérée via un service de découverte
     */
    private static final String CLIENT_SERVICE_URL =
            "http://localhost:8888/service-client/api/client/";

    /**
     * Récupère toutes les voitures avec les détails des clients
     */
    public List<CarResponse> findAll() {
        List<Car> cars = carRepository.findAll();

        return cars.stream()
                .map(this::mapToCarResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une voiture par son ID avec les détails du client
     */
    public CarResponse findById(Long id) throws Exception {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new Exception("Voiture non trouvée avec l'ID: " + id));

        return mapToCarResponse(car);
    }

    /**
     * Convertit une entité Car en CarResponse en récupérant les détails du client
     */
    private CarResponse mapToCarResponse(Car car) {
        // Récupération du client depuis le service client
        Client client = null;
        try {
            client = restTemplate.getForObject(
                    CLIENT_SERVICE_URL + car.getClient_id(),
                    Client.class);
        } catch (Exception e) {
            // En cas d'erreur, on continue avec un client null
            System.err.println("Erreur lors de la récupération du client: " + e.getMessage());
        }

        // Construction de la réponse
        return CarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .matricule(car.getMatricule())
                .client(client)
                .build();
    }
}