package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "rating")
@Data
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Le moodys rating est obligatoire.")
    @Size(max = 125)
    private String moodysRating;

    @NotBlank(message = "Le sandP rating est obligatoire.")
    @Size(max = 125)
    private String sandPRating;

    @NotBlank(message = "Le fitch rating est obligatoire.")
    @Size(max = 125)
    private String fitchRating;

    @PositiveOrZero(message = "L'order number doit être positif.")
    @NotNull(message = "L'order number est obligatoire.")
    private Integer orderNumber;

    public Rating(){};

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

}
