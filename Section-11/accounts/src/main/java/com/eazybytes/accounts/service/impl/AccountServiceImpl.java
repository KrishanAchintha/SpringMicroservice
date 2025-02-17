package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepo;
import com.eazybytes.accounts.repository.CustomerRepo;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl  implements IAccountsService {


    private AccountRepo accountRepo;
    private CustomerRepo customerRepo;

    /**
     * @param customerDto - customerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> optionalCustomer =  customerRepo.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already register with this phone number : "+
                    customerDto.getMobileNumber());
        }
        Customer saved = customerRepo.save(customer);
        accountRepo.save(createNewAccount(saved));
    }



    private Accounts createNewAccount(Customer customer){
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 10000000000L + new Random().nextInt(900000000);

        newAccounts.setAccountNumber(randomAccountNumber);
        newAccounts.setAccountType(AccountConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountConstants.ADDRESS);
        return newAccounts;
    }

    /**
     * @param mobileNumber - Input mobile Number
     * @return Account details based on a given mobile number
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        Accounts accounts = accountRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","customerID",customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto - Customer Dto Object
     * @return boolean indicating if the update of account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean iSUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto!= null){
            Accounts accounts = accountRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()->new ResourceNotFoundException("Account","AccountNumber",accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accounts = accountRepo.save(accounts);
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    ()->new ResourceNotFoundException("Customer","CustomerID",customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepo.save(customer);
            iSUpdated = true;
        }
        return iSUpdated;
    }

    /**
     * @param mobileNumber -input mobile number
     * @return boolean indicating if the account is deleted successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","Customer mobile Number",mobileNumber)
        );
        accountRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.deleteById(customer.getCustomerId());

        return  true;
    }


}
