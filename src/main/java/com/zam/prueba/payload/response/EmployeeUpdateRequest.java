package com.zam.prueba.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeUpdateRequest {

    private Long employeeId;
    private String email;
    private Long areaId;
    private String phoneNumber;
    private String name;

}
