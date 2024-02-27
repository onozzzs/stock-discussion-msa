package com.example.batch.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MovingAverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ticker;
    private long price_12;
    private long price_20;
    private long price_26;
}
