package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.entity.Accounts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountsMapperTest {
    @Test
    public void shouldMapAccountsToAccountsDto() {
        //Arrange
        Accounts accounts = new Accounts();
        accounts.setCustomerId(1L);
        accounts.setAccountNumber(123456789L);
        accounts.setAccountType("Savings");
        accounts.setBranchAddress("1234 Main St, New York, NY 10001");

        //Act
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());

        //Assert
        assertEquals(accounts.getAccountNumber(), accountsDto.getAccountNumber());
        assertEquals(accounts.getAccountType(), accountsDto.getAccountType());
        assertEquals(accounts.getBranchAddress(), accountsDto.getBranchAddress());
        assertNotNull(accountsDto);
    }

    @Test
    public void shouldMapAccountsDtoToAccounts() {
        //Arrange
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(123456789L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("1234 Main St, New York, NY 10001");

        //Act
        Accounts accounts = AccountsMapper.mapToAccounts(accountsDto, new Accounts());

        //Assert
        assertEquals(accountsDto.getAccountNumber(), accounts.getAccountNumber());
        assertEquals(accountsDto.getAccountType(), accounts.getAccountType());
        assertEquals(accountsDto.getBranchAddress(), accounts.getBranchAddress());
        assertNotNull(accounts);
    }
}