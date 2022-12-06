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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
    @Digits(integer = 10, fraction = 2,
            message = "Value must be numeric and must not have more than (fraction) decimals")
    @Positive(message = "Must be a positive number")
    Double        bidQuantity;
    Double        askQuantity;
    Double        bid;
    Double        ask;
    @Size(max = 125, message = "Maximum of {max} characters")
    String        benchmark;
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

    public Bid(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
