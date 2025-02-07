package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Account",
        description = "Schema to hold bank account information"
)
@Data
public class AccountsDto {

    @Schema(
            description = "Account number of the account",example = "3456789120"
    )
    @NotEmpty(message = "Account Number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Type of the account",example = "Saving"
    )
    @NotEmpty(message = "Account Type can not be null or empty")
    private String accountType;

    @Schema(
            description = "Branch name of the account",example = "Colombo"
    )
    @NotEmpty(message = "Branch Address Type can not be null or empty")
    private String branchAddress;


}
