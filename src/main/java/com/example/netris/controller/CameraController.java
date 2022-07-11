package com.example.netris.controller;

import com.example.netris.dto.AggregateResult;
import com.example.netris.exception.CustomException;
import com.example.netris.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CameraController {

    @Autowired
    CameraService cameraService;

    @GetMapping(path="/aggregateCamers", produces = "application/json")
    public ResponseEntity<List<AggregateResult>> getAggregateCamers() throws CustomException {
        return new ResponseEntity<>(cameraService.getAggregateCamers(), HttpStatus.OK);
    }
}
