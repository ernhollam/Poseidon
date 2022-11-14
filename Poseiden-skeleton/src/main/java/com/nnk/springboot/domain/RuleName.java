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
import javax.validation.constraints.Size;

@Entity
@Table(name = "RuleName")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class RuleName {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "Id")
    Integer id;
    @Size(max = 125, message = "Maximum of {max} characters")
    @NotBlank(message = "Name is mandatory")
    String  name;
    @Size(max = 125, message = "Maximum of {max} characters")
    @NotBlank(message = "Description is mandatory")
    String  description;
    @Size(max = 125, message = "Maximum of {max} characters")
    @NotBlank(message = "json is mandatory")
    String  json;
    @Size(max = 512, message = "Maximum of {max} characters")
    @NotBlank(message = "Template is mandatory")
    String  template;
    @Size(max = 125, message = "Maximum of {max} characters")
    @NotBlank(message = "sqlStr is mandatory")
    String  sqlStr;
    @Size(max = 125, message = "Maximum of {max} characters")
    @NotBlank(message = "sqlPart is mandatory")
    String  sqlPart;

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
