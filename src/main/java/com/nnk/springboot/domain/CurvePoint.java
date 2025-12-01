package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "curvepoint")
@Data
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    private Integer curveId;

    private LocalDateTime asOfDate;

    @NotNull(message = "Le terme est obligatoire.")
    @Positive(message = "Le terme doit être un nombre positif.")
    private Double term;

    @NotNull(message = "La valeur est obligatoire.")
    private Double value;

    private LocalDateTime creationDate;

    public CurvePoint(){};

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }


}
