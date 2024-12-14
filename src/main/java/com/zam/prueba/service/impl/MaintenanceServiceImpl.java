package com.zam.prueba.service.impl;

import com.zam.prueba.entity.AreaEntity;
import com.zam.prueba.entity.EmployerEntity;
import com.zam.prueba.exception.DuplicateException;
import com.zam.prueba.exception.NotFoundException;
import com.zam.prueba.payload.request.EmployerRequest;
import com.zam.prueba.payload.response.EmployeeUpdateRequest;
import com.zam.prueba.payload.response.EmployerResponse;
import com.zam.prueba.payload.response.MessageReponse;
import com.zam.prueba.repository.AreaRepository;
import com.zam.prueba.repository.EmployerRepository;
import com.zam.prueba.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    private final EmployerRepository employerRepository;
    private final AreaRepository areaRepository;

    @Autowired
    public MaintenanceServiceImpl(EmployerRepository employerRepository, AreaRepository areaRepository) {
        this.employerRepository = employerRepository;
        this.areaRepository = areaRepository;
    }

    @Override
    public Page<EmployerResponse> searchEmployees(String filterParameter, String sortField,
                                                  String sortDirection, int page, int size) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        Page<Object[]> results = employerRepository.searchEmployees(filterParameter, pageable, sortField, sortDirection);
        List<EmployerResponse> list = results.getContent().stream().map(result -> {
            Long employeeId = (Long) result[0];
            String email = (String) result[1];
            String employeeName = (String) result[2];
            String phoneNumber = (String) result[3];
            String areaName = (String) result[4];
            return EmployerResponse.builder()
                    .employerId(employeeId)
                    .employerName(employeeName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .areaName(areaName)
                    .build();
        }).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, results.getTotalElements());
    }

    @Override
    public MessageReponse createEmployer(EmployerRequest employerRequest) {
        boolean isDuplicate = employerRepository.existsByEmployerEmail(employerRequest.getEmail());
        if (isDuplicate) {
            throw new DuplicateException("Employer with email, "+ employerRequest.getEmail() + " already exists");
        }

        AreaEntity area = areaRepository.findById(employerRequest.getAreaId()).orElseThrow(() -> {
            return new NotFoundException("Area does not exist");
        });

        EmployerEntity employerEntity = EmployerEntity.builder()
                .employerEmail(employerRequest.getEmail())
                .employerName(employerRequest.getEmployerName())
                .employerPhoneNumber(employerRequest.getPhoneNumber())
                .area(area)
                .build();
        employerRepository.save(employerEntity);
        return new MessageReponse("Employer was created successfully");
    }

    @Override
    public EmployerResponse findEmployer(Long id) {
        EmployerEntity employerEntity = employerRepository.findById(id).orElseThrow(() -> {
            return new NotFoundException("Employer does not exist");
        });

        return EmployerResponse.builder()
                .employerId(employerEntity.getEmployerId())
                .employerName(employerEntity.getEmployerName())
                .areaName(employerEntity.getArea().getAreaName())
                .email(employerEntity.getEmployerEmail())
                .phoneNumber(employerEntity.getEmployerPhoneNumber())
                .build();
    }

    @Override
    public MessageReponse updateEmployer(EmployeeUpdateRequest employeeUpdateRequest) {
        boolean isDuplicate = employerRepository.existsByEmployerEmailAndEmployerIdNot(
                employeeUpdateRequest.getEmail(), employeeUpdateRequest.getEmployeeId());
        if (isDuplicate) {
            throw new DuplicateException("Employee with email, "+ employeeUpdateRequest.getEmail() + " already exists");
        }

        AreaEntity area = areaRepository.findById(employeeUpdateRequest.getAreaId()).orElseThrow(() -> {
            return new NotFoundException("Area does not exist");
        });

        EmployerEntity employerEntity = employerRepository.findById(employeeUpdateRequest.getEmployeeId()).orElseThrow(() -> {
            return new NotFoundException("Employee does not exist");
        });

        employerEntity.setEmployerEmail(employeeUpdateRequest.getEmail());
        employerEntity.setEmployerPhoneNumber(employeeUpdateRequest.getPhoneNumber());
        employerEntity.setArea(area);
        employerEntity.setEmployerName(employeeUpdateRequest.getName());
        employerRepository.save(employerEntity);
        return new MessageReponse("Employee was updated successfully");
    }

    @Override
    public MessageReponse deleteEmployer(Long id) {
        EmployerEntity employerEntity = employerRepository.findById(id).orElseThrow(() -> {
            return new NotFoundException("Employee to delete does not exist");
        });
        employerRepository.delete(employerEntity);
        return new MessageReponse("Employee was deleted successfully");
    }

}
