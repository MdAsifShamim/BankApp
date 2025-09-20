package com.mymicroservice.loans.service;

import com.mymicroservice.loans.dto.LoanDto;

public interface ILoansService {

    /**
     *
     * @param mobileNumber accept contact Number  Type of String
     */
    void createLoan(String mobileNumber);


    /**
     * @param mobileNumber accept contact Number Type of String
     * @return LoanDto As an Response
     */
    LoanDto fetchLoan(String mobileNumber);

    /**
     * @param loanDto Have update request Data ,which needs to update into the DB
     * @return Boolean Update Flag
     */
    boolean updateLoan(LoanDto loanDto);


    /**
     * @param mobileNumber accept contact Number Type of String
     * @return Boolean Delete Flag
     */
    boolean deleteLoan(String mobileNumber);

}
