package ru.alishev.springcourse.FirstRestApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;
import ru.alishev.springcourse.FirstRestApp.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public Measurement findOne(int id) {
        Optional<Measurement> measurement = measurementRepository.findById(id);
        try {
            return measurement.orElseThrow(Exception::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        recordMeasurementTime(measurement);
        measurementRepository.save(measurement);
    }

    public String rainyDaysCount() {
        List<Measurement> measurements = measurementRepository.findAll();
        int count = 0;
        for (Measurement measurement : measurements) {
            if (measurement.isConditionsRaining())
                count++;
        }
        return String.valueOf(count);
    }

    private void recordMeasurementTime(Measurement measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setMeasurementDateTime(LocalDateTime.now());
    }
}
