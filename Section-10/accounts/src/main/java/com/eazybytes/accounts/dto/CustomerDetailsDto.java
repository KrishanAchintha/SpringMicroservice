package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer ,Account and Card information"
)
public class CustomerDetailsDto {
    @Schema(
            description = "name of the customer",example = "John doe"
    )
    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 30 ,message = "The Length of name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email address of  the customer",example = "johndoe@gmail.com"
    )
    @NotEmpty(message = "Email can not be null or empty")
    @Email(message = "Email address should be valid value")
    private String email;

    @Schema(
            description = "Mobile Number of the customer",example = "0112345698"
    )
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details for the customer"
    )
    private AccountsDto accountsDto;
    @Schema(
            description = "Card details for the customer"
    )
    private CardsDto cardsDto;
    @Schema(
            description = "Loan details for the customer"
    )
    private LoansDto loansDto;

}
