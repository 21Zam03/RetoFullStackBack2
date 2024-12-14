package com.zam.prueba.controller;

import com.zam.prueba.payload.response.AreaResponse;
import com.zam.prueba.payload.response.RoleResponse;
import com.zam.prueba.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(OptionController.API_PATH)
public class OptionController {

    public static final String API_PATH = "/api/option";
    public static final String ROLE_PATH = "/role";
    public static final String AREA_PATH = "/area";

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping(OptionController.ROLE_PATH)
    public ResponseEntity<List<RoleResponse>> getRoles() {
        return new ResponseEntity<>(optionService.getRoles(), HttpStatus.OK);
    }

    @GetMapping(OptionController.AREA_PATH)
    public ResponseEntity<List<AreaResponse>> getAreas() {
        return new ResponseEntity<>(optionService.getAreas(), HttpStatus.OK);
    }

}
