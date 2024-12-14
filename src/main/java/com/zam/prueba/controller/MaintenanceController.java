package com.zam.prueba.controller;

import com.zam.prueba.payload.request.EmployerRequest;
import com.zam.prueba.payload.response.EmployeeUpdateRequest;
import com.zam.prueba.payload.response.EmployerResponse;
import com.zam.prueba.payload.response.MessageReponse;
import com.zam.prueba.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(MaintenanceController.API_PATH)
public class MaintenanceController {

    public static final String API_PATH = "/api/maintenance";

    private final MaintenanceService maintenanceService;

    @Autowired
    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    public ResponseEntity<Page<EmployerResponse>> searchEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String filterParameter,
            @RequestParam(defaultValue = "employer_id") String sortField,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        return new ResponseEntity<>(maintenanceService.searchEmployees(filterParameter, sortField, sortDirection, page, size), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MessageReponse> createEmployer(@RequestBody EmployerRequest employerRequest) {
        return new ResponseEntity<>(maintenanceService.createEmployer(employerRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerResponse> getEmployerById(@PathVariable Long id) {
        return new ResponseEntity<>(maintenanceService.findEmployer(id), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<MessageReponse> updateEmployee(@RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        return new ResponseEntity<>(maintenanceService.updateEmployer(employeeUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageReponse> deleteEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(maintenanceService.deleteEmployer(id), HttpStatus.OK);
    }

}

