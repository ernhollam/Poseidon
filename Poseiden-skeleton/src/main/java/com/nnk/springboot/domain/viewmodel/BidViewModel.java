package com.nnk.springboot.domain.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
public class BidViewModel {
    @NotBlank(message = "Account is mandatory")
    String account;
    @NotBlank(message = "Type is mandatory")
    String type;
    @NotNull(message = "Bid quantity is mandatory")
    @Digits(integer = 10, fraction = 2,
            message = "Value must be numeric and must not have more than (fraction) decimals")
    @Positive(message = "Must be a positive number")
    Double bidQuantity;
}
