package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class CurvePoint {
    Integer       id;
    Integer       curveId;
    LocalDateTime asOfDate;
    Double        term;
    Double        value;
    LocalDateTime creationDate;
}
