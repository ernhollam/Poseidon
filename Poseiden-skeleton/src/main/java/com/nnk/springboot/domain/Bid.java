package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "BidList")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Bid {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "BidListId")
    Integer       bidListId;
    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Maximum of {max} characters")
    String        account;
    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Maximum of {max} characters")
    String        type;
    @NotNull(message = "Bid quantity is mandatory")
    @Column(name = "bidQuantity")
    Double        bidQuantity;
    @Column(name = "askQuantity")
    Double        askQuantity;
    Double        bid;
    Double        ask;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        benchmark;
    @Column(name = "bidListDate")
    LocalDateTime bidListDate;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        commentary;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        security;
    @Size(max = 10, message = "Maximum of {max} characters")
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

    public Bid(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
