package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurvePoint {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    Integer       id;
    @NotNull(message = "CurveId is mandatory")
    @Column(name = "CurveId")
    @Positive(message = "Must be a positive number")
    Integer       curveId;
    LocalDateTime asOfDate;
    @NotNull(message = "Term is mandatory")
    Double        term;
    @NotNull(message = "Value is mandatory")
    @Digits(integer = 10, fraction = 2,
            message = "Value must be numeric and must not have more than (fraction) decimals")
    Double        value;
    LocalDateTime creationDate;

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
