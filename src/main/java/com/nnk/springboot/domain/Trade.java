package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade")
@Data
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer id;

    @NotBlank(message = "Le compte est obligatoire.")
    @Size(max = 30)
    private String account;

    @NotBlank(message = "Le type est obligatoire.")
    @Size(max = 30)
    private String type;

    @NotNull(message = "La quantité est obligatoire.")
    @Positive(message = "La quantité doit être un nombre positif.")
    private Double buyQuantity;

    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private LocalDateTime tradeDate;

    @Size(max = 125)
    private String security;

    @Size(max = 10)
    private String status;

    @Size(max = 125)
    private String trader;

    @Size(max = 125)
    private String benchmark;

    @Size(max = 125)
    private String book;

    @Size(max = 125)
    private String creationName;

    private LocalDateTime creationDate;

    @Size(max = 125)
    private String revisionName;

    private LocalDateTime revisionDate;

    @Size(max = 125)
    private String dealName;

    @Size(max = 125)
    private String dealType;

    @Size(max = 125)
    private String sourceListId;

    @Size(max = 125)
    private String side;

    public Trade(){};

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }

}
