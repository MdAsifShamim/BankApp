package com.mymicroservice.loans.mapper;

import com.mymicroservice.loans.dto.LoanDto;
import com.mymicroservice.loans.entity.Loans;

public class LoansMapper {

    public static Loans toLoans(LoanDto loanDto, Loans loans){
        loans.setMobileNumber(loanDto.getMobileNumber());
        loans.setLoanNumber(loanDto.getLoanNumber());
        loans.setTotalLoan(loanDto.getTotalLoan());
        loans.setLoanType(loanDto.getLoanType());
        loans.setAmountPaid(loanDto.getAmountPaid());
        loans.setOutstandingAmount(loanDto.getOutStandingAmount());
        return loans;
    }

    public static LoanDto toLoanDto(Loans loans,LoanDto loanDto){

        loanDto.setMobileNumber(loans.getMobileNumber());
        loanDto.setLoanNumber(loans.getLoanNumber());
        loanDto.setLoanType(loans.getLoanType());
        loanDto.setTotalLoan(loans.getTotalLoan());
        loanDto.setAmountPaid(loans.getAmountPaid());
        loanDto.setOutStandingAmount(loans.getOutstandingAmount());
        return loanDto;
    }
}
