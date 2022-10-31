package com.volnei.parking.controller;

import com.volnei.parking.controller.dto.ParkingCreateDTO;
import com.volnei.parking.controller.dto.ParkingDTO;
import com.volnei.parking.controller.mapper.ParkingMapper;
import com.volnei.parking.exception.PatternError;
import com.volnei.parking.model.Parking;
import com.volnei.parking.service.IParkingService;
import com.volnei.parking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final IParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    @Operation(summary = "Find all parkings", description = "Find all parkings", responses = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatternError.class)
                    )
            })
    })
    public ResponseEntity<List<ParkingDTO>> findAll() {
        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find parking by id", description = "Find parking by id", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatternError.class)
                    )
            })
    })
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
        Parking parking = parkingService.findById(id);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete parking by id", description = "Delete parking by id", responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatternError.class)
                    )
            })
    })
    public ResponseEntity delete(@PathVariable String id) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Register parking", description = "Register new park", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatternError.class)
                    )
            })
    })
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto) {
        var parkingCreate = parkingMapper.toParkingCreate(dto);
        var parking = parkingService.create(parkingCreate);
        var result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update parking", description = "Update parking by id", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatternError.class)
                    )
            })
    })
    public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDTO parkingCreteDTO) {
        Parking parkingUpdate = parkingMapper.toParkingCreate(parkingCreteDTO);
        Parking parking = parkingService.update(id, parkingUpdate);
        return ResponseEntity.ok(parkingMapper.toParkingDTO(parking));
    }

    @PostMapping("/{id}/exit")
    @Operation(summary = "Checkout parking", description = "Checkout", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatternError.class)
                    )
            })
    })
    public ResponseEntity<ParkingDTO> checkOut(@PathVariable String id) {
        Parking parking = parkingService.checkOut(id);
        return ResponseEntity.ok(parkingMapper.toParkingDTO(parking));
    }

}