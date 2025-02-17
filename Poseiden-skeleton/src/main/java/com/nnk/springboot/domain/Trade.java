package com.nnk.springboot.domain;

import com.nnk.springboot.validators.ValidNumericField;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Table(name = "trade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "TradeId")
    Integer       tradeId;
    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Maximum of {max} characters")
    String        account;
    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Maximum of {max} characters")
    String        type;
    @ValidNumericField
    Double        buyQuantity;
    Double        sellQuantity;
    Double        buyPrice;
    Double        sellPrice;
    String        benchmark;
    LocalDateTime tradeDate;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        security;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        status;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        trader;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        book;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        creationName;
    LocalDateTime creationDate;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        revisionName;
    LocalDateTime revisionDate;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        dealName;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        dealType;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        sourceListId;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        side;

    public Trade(String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
}
