package com.truliacare.niramayaconfiguration.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "configuration")
@Data
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int identifier;
    private String configurationKey;
    private String configurationValue;
    private String context;
}
