package com.example.netris.service;

import com.example.netris.dto.AggregateResult;
import com.example.netris.exception.CustomException;

import java.util.List;

public interface CameraService {
    List<AggregateResult> getAggregateCamers() throws CustomException;
}
