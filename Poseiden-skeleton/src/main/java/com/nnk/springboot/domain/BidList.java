package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "bidlist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidList {
    Integer       BidListId;
    String        account;
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
}
