package com.mymicroservice.accounts.controller;


import com.mymicroservice.accounts.dto.CustomerDetailsDto;
import com.mymicroservice.accounts.dto.ErrorResponseDto;
import com.mymicroservice.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "REST APIs for Customer details in Bank Application",
        description = "REST APIs in Bank application to gets customers all details")
public class CustomerController {

    private static final Logger logger= LoggerFactory.getLogger(CustomerController.class);
    private final ICustomerService iCustomerService;

    public CustomerController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }


    @Operation(summary = "Fetch Customer Detail REST API"
            ,description = "REST API to fetch data customer,Account,Cards & loans Details based on a mobile number")

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content=@Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("mybank-correlation-id") String correlationId,
                                                                    @RequestParam
                                                                       @Pattern(regexp = "^$|[0-9]{10}",message = "Mobile number must be 10 digit")
                                                                   String mobileNumber){
        logger.debug("myBank- correlation-id found: {}",correlationId);
        CustomerDetailsDto customerDetailsDto=iCustomerService.fetchCustomerDetails(mobileNumber,correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
