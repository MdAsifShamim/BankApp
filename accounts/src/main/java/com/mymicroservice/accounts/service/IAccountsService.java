package com.mymicroservice.accounts.service;

import com.mymicroservice.accounts.dto.CustomerDto;

public interface IAccountsService {


    /**
     * @param customerDto - CustomerDto Object
     */
    public void createAccount(CustomerDto customerDto);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    public CustomerDto fetchAccount(String mobileNumber);


    /**
     * @param customerDto -CustomerDto Object
     * @return boolean including if the update of Account detail is successful or not
     */
    public boolean updateAccount(CustomerDto customerDto);


    /**
     * @param mobileNumber Input Mobile Number
     * @return boolean indicating if the delete of account details is successfully or not
     */
    public boolean deleteAccount(String mobileNumber);


    /**
     *  Update Communication switch flag on account table once message has been send using MQ producer
     * @param accountNumber
     * @return Boolean
     */
    public boolean updateCommunicationStatus(Long accountNumber);

}
