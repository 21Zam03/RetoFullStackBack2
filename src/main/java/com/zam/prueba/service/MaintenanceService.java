package com.zam.prueba.service;

import com.zam.prueba.payload.request.EmployerRequest;
import com.zam.prueba.payload.response.EmployeeUpdateRequest;
import com.zam.prueba.payload.response.EmployerResponse;
import com.zam.prueba.payload.response.MessageReponse;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface MaintenanceService {

    public Page<EmployerResponse> searchEmployees(String filterParameter, String sortField, String sortDirection,
                                                  int page, int size);
    public MessageReponse createEmployer(EmployerRequest employerRequest);
    public EmployerResponse findEmployer(Long id);
    public MessageReponse updateEmployer(EmployeeUpdateRequest employeeUpdateRequest);
    public MessageReponse deleteEmployer(Long id);

}
