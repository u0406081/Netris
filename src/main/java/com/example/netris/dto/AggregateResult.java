package com.example.netris.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AggregateResult {
    private int id;
    private String urlType;
    private String videoUrl;
    private String value;
    private String ttl;
}
