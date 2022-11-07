package com.nnk.springboot.domain.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class BidViewModel {
    @NotBlank(message = "Account is mandatory")
    String account;
    @NotBlank(message = "Type is mandatory")
    String type;
    double bidQuantity;
}
