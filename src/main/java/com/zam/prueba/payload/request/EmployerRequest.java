package com.zam.prueba.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerRequest {

    private String employerName;
    private String email;
    private Long areaId;
    private String phoneNumber;

}
