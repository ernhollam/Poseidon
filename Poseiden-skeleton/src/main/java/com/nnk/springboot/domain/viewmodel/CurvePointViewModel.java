package com.nnk.springboot.domain.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class CurvePointViewModel {
    @NotBlank(message = "Must not be null")
    Integer curveId;
    Double  term;
    Double  value;
}
