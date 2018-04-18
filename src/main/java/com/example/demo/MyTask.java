package com.example.demo;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MyTask {
    private String url = "http://localhost:8080/";
    RestTemplate restTemplate = new RestTemplate();
    private int id = 0;

    @Scheduled(cron = "0 */2 * ? * *")
    public void addVehicle(){
        String urlFull = "http://localhost:8080/addVehicle/";
        String [] makeModel = {"Honda", "Ford", "Toyota", "Hyundai", "Dodge", "Nissan"};
        int ranModel = new Random().nextInt(makeModel.length);
        int year = ThreadLocalRandom.current().nextInt(1986, 2017);
        double retailPrice = ThreadLocalRandom.current().nextDouble(15000, 45000);
        Vehicle v = new Vehicle(id, makeModel[ranModel], year, retailPrice);
        restTemplate.postForObject(urlFull, v, Vehicle.class);

       //System.out.println("Add Vehicle: " + v.toString());
    }

    @Scheduled(cron = "0 * * ? * *")
    public void deleteVehicle() {
        int deleteID = ThreadLocalRandom.current().nextInt(1, 101);

        String getUrl = "http://localhost:8080/getVehicle/" + deleteID;
        String deleteUrl = "http://localhost:8080/deleteVehicle/" + deleteID;

        Vehicle v = restTemplate.getForObject(getUrl, Vehicle.class);
        if (v != null) {
            restTemplate.delete(deleteUrl);
            System.out.println("Deleted: " + v);
        }
    }


    @Scheduled(cron = "0 * * ? * *")
    public void updateVehicle() {
        String urlFull = "http://localhost:8080/updateVehicle/";

        String [] makeModel = {"Honda", "Ford", "Toyota", "Hyndai", "Dodge", "Nissan"};
        int ranModel = new Random().nextInt(makeModel.length);
        int year = new Random().nextInt(2016 - 1986) + 1986;
        double retailPrice = ThreadLocalRandom.current().nextDouble(15000, 45000);

        Vehicle v = new Vehicle(new Random().nextInt(100), makeModel[ranModel], year, retailPrice);
        restTemplate.put(urlFull, v);
        System.out.println("Update Vehicle: " + v.toString());
    }

    @Scheduled(cron = "0 0 * * * *")
    public void latestVehicleReport() {
        String getUrl = "http://localhost:8080/getLatestVehicles/";
        List<Vehicle> latestVehicles = restTemplate.getForObject(getUrl, List.class);
        System.out.println("-----LATEST VEHICLES-----");
        for (int i = 0; i < latestVehicles.size(); i++) {
            System.out.println(latestVehicles.get(i));
        }
        System.out.println("--------------------------");
    }
}

