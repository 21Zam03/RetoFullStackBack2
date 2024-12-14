package com.zam.prueba.service;

import com.zam.prueba.payload.response.AreaResponse;
import com.zam.prueba.payload.response.RoleResponse;

import java.util.List;

public interface OptionService {

    public List<RoleResponse> getRoles();
    public List<AreaResponse> getAreas();

}
