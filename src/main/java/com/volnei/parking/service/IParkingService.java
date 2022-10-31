package com.volnei.parking.service;

import com.volnei.parking.model.Parking;

import java.util.List;

public interface IParkingService {

    List<Parking> findAll();
    Parking findById(String id);
    Parking create(Parking parkingCreate);
    void delete(String id);
    Parking update(String id, Parking parkingCreate);
    Parking checkOut(String id);
}
