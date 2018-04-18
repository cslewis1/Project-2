package com.example.demo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional

public class VehicleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Vehicle vehicle) {
        entityManager.persist(vehicle);
        return;
    }

    public Vehicle getById(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    public Vehicle update(Vehicle newVehicle) {
        entityManager.merge(newVehicle);
        return newVehicle;
    }

    public Vehicle delete(int id) {
        Vehicle v = entityManager.find(Vehicle.class, id);
        if (v != null) {
            entityManager.remove(v);
            return v;
        }
        return null;
    }

    public int getHighestId() {
        Query q = entityManager.createNativeQuery("select max(id) from vehicles");
        int highestId = (int) q.getSingleResult();
        return highestId;
    }
}
