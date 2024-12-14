package com.zam.prueba.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerResponse {

    private Long employerId;
    private String employerName;
    private String email;
    private String phoneNumber;
    private String areaName;

}
