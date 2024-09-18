package com.soro.esop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.soro.esop.entity.enums.Yn;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "dx_entity")
public class DxEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    private String name;
    private String value;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String ssn;

    private String description;

    private Yn isTrxCeaseYn = Yn.N;

    private LocalDate fromDate;
    private LocalDate toDate;

    @JsonProperty("isTrxCeaseYn")
    public boolean getIsTrxCeaseYnBoolean() {
        return this.isTrxCeaseYn == Yn.Y;
    }
}
