package ru.alishev.springcourse.FirstRestApp.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.dto.MeasurementDTO;
import ru.alishev.springcourse.FirstRestApp.dto.MeasurementsResponse;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;
import ru.alishev.springcourse.FirstRestApp.services.MeasurementService;
import ru.alishev.springcourse.FirstRestApp.util.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;

    private final ModelMapper modelMapper;

    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping("/all")
    public MeasurementsResponse findAll() {
        return new MeasurementsResponse(measurementService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public MeasurementDTO getOne(@PathVariable("id") int id) {
        return convertToMeasurementDTO(measurementService.findOne(id));
    }

    @PostMapping("/add")
    private ResponseEntity<HttpStatus> addMeasurements(@RequestBody @Valid MeasurementDTO measurementDTO,
                                 BindingResult bindingResult) {

        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementException(errorMsg.toString());
        }
        measurementService.addMeasurement(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public String rainyDaysCount() {
        return measurementService.rainyDaysCount();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
