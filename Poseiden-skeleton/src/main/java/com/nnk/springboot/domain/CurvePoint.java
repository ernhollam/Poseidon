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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Table(name = "CurvePoint")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurvePoint {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    Integer       id;
    @NotNull(message = "Must not be null")
    @Column(name = "CurveId")
    Integer       curveId;
    @Column(name = "asOfDate")
    LocalDateTime asOfDate;
    @NotNull(message = "Term is mandatory")
    Double        term;
    @NotNull(message = "Value is mandatory")
    Double        value;
    @Column(name = "creationDate")
    LocalDateTime creationDate;

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
