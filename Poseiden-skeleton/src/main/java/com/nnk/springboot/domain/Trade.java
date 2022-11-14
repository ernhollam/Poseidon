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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Table(name = "Trade")
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
    @Column(name = "buyQuantity")
    Double        buyQuantity;
    @Column(name = "sellQuantity")
    Double        sellQuantity;
    @Column(name = "buyPrice")
    Double        buyPrice;
    @Column(name = "sellPrice")
    Double        sellPrice;
    String        benchmark;
    @Column(name = "tradeDate")
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
    @Column(name = "creationName")
    String        creationName;
    @Column(name = "creationDate")
    LocalDateTime creationDate;
    @Size(max = 125, message = "Maximum of {max} characters")
    @Column(name = "revisionName")
    String        revisionName;
    @Column(name = "revisionDate")
    LocalDateTime revisionDate;
    @Size(max = 125, message = "Maximum of {max} characters")
    @Column(name = "dealName")
    String        dealName;
    @Size(max = 125, message = "Maximum of {max} characters")
    @Column(name = "dealType")
    String        dealType;
    @Size(max = 125, message = "Maximum of {max} characters")
    @Column(name = "sourceListId")
    String        sourceListId;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        side;

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}
