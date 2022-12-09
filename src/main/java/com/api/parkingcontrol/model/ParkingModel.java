package com.api.parkingcontrol.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@ApiModel("ParkingModel")
@Getter
@Setter
@Table(name = "parking", uniqueConstraints = {@UniqueConstraint(columnNames = {"licensePlate", "exitDateTime"})})
@Entity
public class ParkingModel {

    @ApiModelProperty(example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ApiModelProperty(example = "XXX000")
    @Column(nullable = false, length = 7)
    private String licensePlate;

    @ApiModelProperty(example = "2022-01-01T01:00:00")
    @Column(nullable = false)
    private LocalDateTime entryDateTime;

    @ApiModelProperty(example = "2022-01-01T02:00:00")
    @Column
    private LocalDateTime exitDateTime;

    @ApiModelProperty(example = "10.00")
    @Column
    private Double totalParkingValue;

}
