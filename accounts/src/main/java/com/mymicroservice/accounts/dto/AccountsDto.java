package com.mymicroservice.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Accounts", description = "Schema to hold Account information")
public class AccountsDto {

    @Schema( description = "Account number of Account Holder Name" ,example = "1234567890")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number can not be null or empty")
    private Long accountNumber;

    @NotEmpty(message = "Account Type can not be null or empty")
    @Schema(description = "Bank Account Type",  example = "Saving")
    private String accountType;

    @Schema( description = "Bank branch Address" ,example = "ABC Bangalore")
    @NotEmpty(message = "Branch Address can not be null or empty")
    private String branchAddress;
}
