package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "bidlist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class BidList {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer       bidListId;
    @NotBlank(message = "Account is mandatory")
    String        account;
    @NotBlank(message = "Type is mandatory")
    String        type;
    Double        bidQuantity;
    Double        askQuantity;
    Double        bid;
    Double        ask;
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

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
