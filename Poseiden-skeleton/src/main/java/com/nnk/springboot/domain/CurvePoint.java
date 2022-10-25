package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class CurvePoint {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer       id;
    Integer       curveId;
    LocalDateTime asOfDate;
    Double        term;
    Double        value;
    LocalDateTime creationDate;

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
