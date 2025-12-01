package com.nnk.springboot.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
@Data
public class RuleName {
    // TODO: Map columns in data table RULENAME with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Le nom est obligatoire.")
    @Size(max = 125)
    private String name;

    @Size(max = 125)
    private String description;

    @Size(max = 125)
    private String json;

    @Size(max = 125)
    private String template;

    @Size(max = 125)
    private String sqlStr;

    @Size(max = 125)
    private String sqlPart;

    public RuleName(){};

    public RuleName(String name,
                    String description,
                    String json,
                    String template,
                    String sqlStr,
                    String sqlPart) {

        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

}
