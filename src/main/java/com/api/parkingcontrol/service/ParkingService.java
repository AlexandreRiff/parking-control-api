package com.api.parkingcontrol.service;

import com.api.parkingcontrol.dto.ParkingSaveDto;
import com.api.parkingcontrol.dto.ParkingUpdateDto;
import com.api.parkingcontrol.exception.BusinessRuleException;
import com.api.parkingcontrol.exception.ResourceNotFoundException;
import com.api.parkingcontrol.model.ParkingModel;
import com.api.parkingcontrol.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Transactional
    public ParkingModel save(ParkingSaveDto parkingSaveDto) {
        if (thereIsActiveParking(parkingSaveDto.getLicensePlate())) {
            throw new BusinessRuleException("existe um estacionamento ativo para a placa: " + parkingSaveDto.getLicensePlate(), HttpStatus.CONFLICT);
        }

        var parkingModel = new ParkingModel();
        BeanUtils.copyProperties(parkingSaveDto, parkingModel);

        String TIMEZONE = "America/Sao_Paulo";
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of(TIMEZONE));
        parkingModel.setEntryDateTime(currentDateTime);

        return parkingRepository.save(parkingModel);
    }

    public List<ParkingModel> findAll() {
        return parkingRepository.findAll();
    }

    public ParkingModel findById(int id) {
        Optional<ParkingModel> parkingModelOptional = parkingRepository.findById(id);

        if (parkingModelOptional.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }

        return parkingModelOptional.get();
    }

    @Transactional
    public ParkingModel update(int id, ParkingUpdateDto parkingUpdateDto) {
        var parkingModel = findById(id);

        if (exitDateTimeIsLessThanEntryDateTime(parkingModel.getEntryDateTime(),
                parkingUpdateDto.getExitDateTime())) {
            throw new BusinessRuleException("a data e hora informada não pode ser inferior a data e hora de entrada",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        parkingModel.setExitDateTime(parkingUpdateDto.getExitDateTime());

        LocalDateTime entryDateTime = parkingModel.getEntryDateTime();
        LocalDateTime exitDateTime = parkingModel.getExitDateTime();
        long minutesParked = ChronoUnit.MINUTES.between(entryDateTime, exitDateTime);

        long SIXTY_MINUTES = 60;
        double parkedHours = Math.ceil((double) minutesParked / SIXTY_MINUTES);
        double VALUE_HOUR_PARKING = 10.00;
        double totalParkingValue = (parkedHours * VALUE_HOUR_PARKING);
        parkingModel.setTotalParkingValue(totalParkingValue);

        return parkingRepository.save(parkingModel);
    }

    @Transactional
    public void delete(int id) {
        var parkingModel = findById(id);
        parkingRepository.delete(parkingModel);
    }

    private boolean thereIsActiveParking(String licensePlate) {
        return parkingRepository.existsByLicensePlateAndExitDateTimeIsNull(licensePlate);
    }

    private boolean exitDateTimeIsLessThanEntryDateTime(LocalDateTime entryDateTime, LocalDateTime exitDateTime) {
        long minutesParked = ChronoUnit.MINUTES.between(entryDateTime, exitDateTime);
        return minutesParked < 0;
    }

}
