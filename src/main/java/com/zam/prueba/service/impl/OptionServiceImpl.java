package com.zam.prueba.service.impl;

import com.zam.prueba.entity.AreaEntity;
import com.zam.prueba.entity.RoleEntity;
import com.zam.prueba.payload.response.AreaResponse;
import com.zam.prueba.payload.response.RoleResponse;
import com.zam.prueba.repository.AreaRepository;
import com.zam.prueba.repository.RoleRepository;
import com.zam.prueba.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {

    private final RoleRepository roleRepository;
    private final AreaRepository areaRepository;

    @Autowired
    public OptionServiceImpl(RoleRepository roleRepository, AreaRepository areaRepository) {
        this.roleRepository = roleRepository;
        this.areaRepository = areaRepository;
    }

    @Override
    public List<RoleResponse> getRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        List<RoleResponse> roleResponses = new ArrayList<RoleResponse>();
        for (RoleEntity role : roles) {
            RoleResponse roleResponse = RoleResponse.builder()
                    .roleId(role.getRoleId())
                    .roleName(role.getName())
                    .build();
            roleResponses.add(roleResponse);
        }
        return roleResponses;
    }

    @Override
    public List<AreaResponse> getAreas() {
        List<AreaEntity> areas = areaRepository.findAll();
        List<AreaResponse> areaResponses = new ArrayList<AreaResponse>();
        for (AreaEntity area : areas) {
            AreaResponse areaResponse = AreaResponse.builder()
                    .areaId(area.getAreaId())
                    .areaName(area.getAreaName())
                    .build();
            areaResponses.add(areaResponse);
        }
        return areaResponses;
    }

}
