package com.mymicroservice.accounts.service.impl;

import com.mymicroservice.accounts.constants.AccountsConstants;
import com.mymicroservice.accounts.dto.AccountMsgDto;
import com.mymicroservice.accounts.dto.AccountsDto;
import com.mymicroservice.accounts.dto.CustomerDto;
import com.mymicroservice.accounts.entity.Accounts;
import com.mymicroservice.accounts.entity.Customer;
import com.mymicroservice.accounts.exception.CustomerAlreadyExistsException;
import com.mymicroservice.accounts.exception.ResourceNotFoundException;
import com.mymicroservice.accounts.mapper.AccountsMapper;
import com.mymicroservice.accounts.mapper.CustomerMapper;
import com.mymicroservice.accounts.repository.AccountsRepository;
import com.mymicroservice.accounts.repository.CustomerRepository;
import com.mymicroservice.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final Logger logger= LoggerFactory.getLogger(AccountsServiceImpl.class);

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private final StreamBridge streamBridge;
    /**
     * @param customerDto - CustomerDto Object
     *
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.toCustomer(customerDto, new Customer());
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already register for given number " + customerDto.getMobileNumber());
        }
       // customer.setCreatedAt(LocalDateTime.now());
       // customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount,savedCustomer);

    }

    private void sendCommunication(Accounts accounts,Customer customer){

        var accountMsgDto=new AccountMsgDto(accounts.getAccountNumber(),customer.getName(),customer.getEmail(), customer.getMobileNumber());
        logger.info("Sending communication request for the details: {}",accountMsgDto);
        var result=streamBridge.send("sendCommunication-out-0",accountMsgDto);
        logger.info("Is the communication request successfully triggered: {}",result);
    }

    private Accounts createNewAccount(Customer customer) {

        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        //newAccount.setCreatedAt(LocalDateTime.now());
        //newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(account, new AccountsDto());
        CustomerDto customerDto = CustomerMapper.toCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    /**
     * @param customerDto -CustomerDto Object
     * @return boolean including if the update of Account detail is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts account=  accountsRepository
                    .findById(accountsDto.getAccountNumber()).orElseThrow(() ->
                            new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString()));

             AccountsMapper.mapToAccounts(accountsDto, account);
            account = accountsRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository
                    .findById(customerId).orElseThrow(() ->
                            new ResourceNotFoundException("Customer", "customerId", customerId.toString()));
            CustomerMapper.toCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }

        return isUpdated;
    }

    /**
     * @param mobileNumber Input Mobile Number
     * @return boolean indicating if the delete of account details is successfully or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer=customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        customerRepository.deleteById(customer.getCustomerId());
        accountsRepository.deleteByCustomerId(customer.getCustomerId());

        return true;
    }

    /**
     * Set Communication Switch value as true once get response from MQ
     * @param accountNumber
     * @return boolean
     */
    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated=false;
        if(accountNumber!=null){
            var accounts=accountsRepository.findById(accountNumber)
                    .orElseThrow(()->new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString()));
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated=true;
        }

        return isUpdated;
    }


}
