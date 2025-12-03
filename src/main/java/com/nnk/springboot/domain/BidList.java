package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "bidlist")
@Data
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private Integer bidListId;

    @NotNull
    @NotBlank(message = "Le compte est nécessaire.")
    @Size(max = 30)
    private String account;

    @NotNull
    @NotBlank(message = "Le type est nécessaire.")
    @Size(max = 30)
    private String type;

    @NotNull(message = "La quantité est obligatoire.")
    @Positive(message = "La quantité doit être positive.")
    private Double bidQuantity;

    private Double askQuantity;
    private Double bid;
    private Double ask;

    @Size(max = 125)
    private String benchmark;

    private LocalDateTime bidListDate;

    @Size(max = 125)
    private String commentary;

    @Size(max = 125)
    private String security;

    @Size(max = 10)
    private String status;

    @Size(max = 125)
    private String trader;

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

    public BidList() {
    }

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

}
