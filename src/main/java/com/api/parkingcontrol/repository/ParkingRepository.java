package com.api.parkingcontrol.repository;

import com.api.parkingcontrol.model.ParkingModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<ParkingModel, Integer> {

    boolean existsByLicensePlateAndExitDateTimeIsNull(String licensePlate);

}
