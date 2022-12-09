package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dto.ParkingSaveDto;
import com.api.parkingcontrol.dto.ParkingUpdateDto;
import com.api.parkingcontrol.model.ParkingModel;
import com.api.parkingcontrol.service.ParkingService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "Parking")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/parking")
@RequiredArgsConstructor
@RestController
public class ParkingController {

    private final ParkingService parkingService;

    @ApiOperation(value = "Adicionar um novo registro de estacionamento",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created",
                    response = ParkingModel.class,
                    examples = @Example(
                            value = @ExampleProperty(
                                    value =
                                            "{\n" +
                                                    "   \"id\": 1,\n" +
                                                    "   \"licensePlate\": \"XXX0000\",\n" +
                                                    "   \"entryDateTime\": \"2022-01-01T01:00:00\",\n" +
                                                    "   \"exitDateTime\": null,\n" +
                                                    "   \"totalParkingValue\": null\n" +
                                                    "}",
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            ),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request",
                    examples = @Example(
                            value = @ExampleProperty(
                                    value = "{\n    \"message\": \"campo inválido, não segue o padrão XXX0X00 ou XXX0000\"\n}",
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            ),
            @ApiResponse(
                    code = 409,
                    message = "Conflict",
                    examples = @Example(
                            value = @ExampleProperty(
                                    value = "{\n    \"message\": \"existe um estacionamento ativo para a placa: XXX000\"\n}",
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            )
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ParkingModel> saveParking(@Valid @RequestBody ParkingSaveDto parkingSaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.save(parkingSaveDto));
    }

    @ApiOperation(
            value = "Retornar todos os registros de estacionamento",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponse(
            code = 200,
            message = "Ok",
            response = ParkingModel.class,
            responseContainer = "List"
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ParkingModel>> findAllParking() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.findAll());
    }

    @ApiOperation(
            value = "Encontrar um registro de estacionamento por Id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Ok",
                    response = ParkingModel.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Not Found",
                    examples = @Example(
                            value = @ExampleProperty(
                                    value = "{\n    \"message\": \"recurso não encontrado para ao id: 1\"\n}",
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            ),
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ParkingModel> findByIdParking(@PathVariable(value = "id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.findById(id));
    }


    @ApiOperation(
            value = "Atualizar um registro de estacionamento",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Ok",
                    response = ParkingModel.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Not Found",
                    examples = @Example(
                            value = @ExampleProperty(
                                    value = "{\n    \"message\": \"recurso não encontrado par ao id: 1\"\n}",
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            ),
            @ApiResponse(
                    code = 422,
                    message = " Unprocessable Entity",
                    examples = @Example(
                            value = @ExampleProperty(
                                    value = "{\n    \"message\": \"a data e hora informada não pode ser inferior a data e hora de entrada\"\n}",
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            ),
    })
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ParkingModel> updateParking(@PathVariable(value = "id") int id,
                                                      @Valid @RequestBody ParkingUpdateDto parkingUpdateDto) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.update(id, parkingUpdateDto));
    }

    @ApiOperation(
            value = "Deletar um registro de estacionamento",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Ok",
                    examples = @Example(
                            value = @ExampleProperty(
                                    value = "{\n    \"message\": \"estacionamento excluído com sucesso\"\n}",
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            ),
            @ApiResponse(
                    code = 404,
                    message = "Not Found",
                    examples = @Example(
                            value = @ExampleProperty(
                                    value = "{\n    \"message\": \"recurso não encontrado par ao id: 1\"\n}",
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            )
    })
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> deleteParking(@PathVariable(value = "id") int id) {
        parkingService.delete(id);

        Map<String, String> message = new HashMap<>();
        message.put("message", "estacionamento excluído com sucesso");

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
