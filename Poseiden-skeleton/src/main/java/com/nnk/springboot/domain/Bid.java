package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "bidlist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Bid {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer       bidListId;
    @NotBlank(message = "Account is mandatory")
    String        account;
    @NotBlank(message = "Type is mandatory")
    String        type;
    double        bidQuantity;
    double        askQuantity;
    double        bid;
    double        ask;
    String        benchmark;
    LocalDateTime bidListDate;
    String        commentary;
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

    public Bid(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
