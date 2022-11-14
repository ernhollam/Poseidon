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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Rating")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    Integer id;
    @Size(max = 125, message = "Maximum of {max} characters")
    @Column(name = "moodysRating")
    String  moodysRating;
    @Size(max = 125, message = "Maximum of {max} characters")
    @Column(name = "sandPRating")
    String  sandPRating;
    @Size(max = 125, message = "Maximum of {max} characters")
    @Column(name = "fitchRating")
    String  fitchRating;
    @NotNull(message = "Order number is mandatory")
    @Column(name = "orderNumber")
    Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
