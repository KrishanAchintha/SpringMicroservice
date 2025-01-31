package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;


public interface IAccountsService {
    /**
     *
     * @param customerDto - customerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Account details based on a given mobile number
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     *
     * @param customerDto - Customer Dto Object
     * @return boolean indicating if the update of account details is successful or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber
     * @return boolean indicating if the account is deleted successful or not
     */
    boolean deleteAccount(String mobileNumber);
}
