package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "trade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Trade {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer       tradeId;
    String        account;
    String        type;
    Double        buyQuantity;
    Double        sellQuantity;
    Double        buyPrice;
    Double        sellPrice;
    String        benchmark;
    LocalDateTime tradeDate;
    String        security;
    String        status;
    String        trader;
    String        book;
    String        creationName;
    LocalDateTime creationDate;
    String        revisionName;
    LocalDateTime revisionDate;
    String        dealName;
    String        dealType;
    String        sourceListId;
    String        side;

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}
