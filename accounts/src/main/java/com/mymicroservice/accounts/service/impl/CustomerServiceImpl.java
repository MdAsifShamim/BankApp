package com.mymicroservice.accounts.service.impl;

import com.mymicroservice.accounts.dto.AccountsDto;
import com.mymicroservice.accounts.dto.CardsDto;
import com.mymicroservice.accounts.dto.CustomerDetailsDto;
import com.mymicroservice.accounts.dto.LoanDto;
import com.mymicroservice.accounts.entity.Accounts;
import com.mymicroservice.accounts.entity.Customer;
import com.mymicroservice.accounts.exception.ResourceNotFoundException;
import com.mymicroservice.accounts.mapper.AccountsMapper;
import com.mymicroservice.accounts.mapper.CustomerMapper;
import com.mymicroservice.accounts.repository.AccountsRepository;
import com.mymicroservice.accounts.repository.CustomerRepository;
import com.mymicroservice.accounts.service.ICustomerService;
import com.mymicroservice.accounts.service.client.CardsFeignClient;
import com.mymicroservice.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;


    /**
     *
     * @param mobileNumber Input Mobile Number
     * @return CustomerDetailsDto Customer details based on mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.toCustomerDetailsDto(customer,new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account,new AccountsDto()));

        ResponseEntity<LoanDto> loanDtoResponseEntity =loansFeignClient.fetchLoanDetail(correlationId,mobileNumber);
        if(null!=loanDtoResponseEntity){
            customerDetailsDto.setLoanDto(loanDtoResponseEntity.getBody());
        }

        ResponseEntity<CardsDto> cardsDtoResponseEntity=cardsFeignClient.getCardDetail(correlationId,mobileNumber);
        if(null!=cardsDtoResponseEntity){
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }


        return customerDetailsDto;
    }
}
