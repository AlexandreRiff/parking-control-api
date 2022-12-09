package com.api.parkingcontrol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@ApiModel("ParkingSaveDto")
@Getter
@Setter
public class ParkingSaveDto {

    @ApiModelProperty(example = "XXX0000", required = true)
    @Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}", message = "campo inválido, não segue o padrão XXX0X00 ou " +
            "XXX0000")
    private String licensePlate;

}
