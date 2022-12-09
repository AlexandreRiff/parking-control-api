package com.api.parkingcontrol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel("ParkingUpdateDto")
@Getter
@Setter
public class ParkingUpdateDto {

    @ApiModelProperty(example = "2022-01-01T02:00:00", required = true)
    @NotNull(message = "campo inválido")
    private LocalDateTime exitDateTime;

}
