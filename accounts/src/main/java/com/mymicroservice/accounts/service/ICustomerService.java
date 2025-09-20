package com.mymicroservice.accounts.service;

import com.mymicroservice.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    /**
     *
     * @param mobileNumber Input Mobile Number
     * @return CustomerDetailsDto Customer details based on mobileNumber
     */
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String CorrelationId);
}
