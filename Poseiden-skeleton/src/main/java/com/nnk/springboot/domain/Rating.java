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

@Entity
@Table(name = "rating")
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
    @NotBlank(message = "MoodysRating is mandatory")
    String  moodysRating;
    @Size(max = 125, message = "Maximum of {max} characters")
    @NotBlank(message = "SandPRating is mandatory")
    String  sandPRating;
    @Size(max = 125, message = "Maximum of {max} characters")
    @NotBlank(message = "FitchRating is mandatory")
    String  fitchRating;
    @ValidNumericField
    Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
