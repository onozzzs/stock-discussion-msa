package com.example.activity.dto;

import lombok.Data;

@Data
public class SearchDTO {
    private String type;
    private String keyword;
    private int page;
}
