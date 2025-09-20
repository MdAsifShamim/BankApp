package com.mymicroservice.loans.service.impl;

import com.mymicroservice.loans.constants.LoansConstants;
import com.mymicroservice.loans.dto.LoanDto;
import com.mymicroservice.loans.entity.Loans;
import com.mymicroservice.loans.exception.LoanAlreadyExistsException;
import com.mymicroservice.loans.exception.ResourceNotFoundException;
import com.mymicroservice.loans.mapper.LoansMapper;
import com.mymicroservice.loans.repository.LoansRepository;
import com.mymicroservice.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    /**
     * @param mobileNumber accept contact Number  Type of String
     */
    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> loan=loansRepository.findByMobileNumber(mobileNumber);
        if(loan.isPresent()){
            throw  new LoanAlreadyExistsException("Loan already exists with given mobile number "+mobileNumber);
        }

        loansRepository.save(createNewLoan(mobileNumber));
    }



    private Loans createNewLoan(String mobileNumber){

        Loans loans=new Loans();
        long randomLoanNumber=100000000000L+new Random().nextInt(900000000);
        loans.setLoanNumber(Long.toString(randomLoanNumber));
        loans.setMobileNumber(mobileNumber);
        loans.setLoanType(LoansConstants.HOME_LOAN);
        loans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
       // loans.setCreatedAt(LocalDateTime.now());
        return loans;
    }


    /**
     * @param mobileNumber accept contact Number Type of String
     * @return LoanDto As an Response
     */
    @Override
    public LoanDto fetchLoan(String mobileNumber) {

        Loans loans=loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan","mobileNumber",mobileNumber));
        return LoansMapper.toLoanDto(loans,new LoanDto());
    }

    /**
     * @param loanDto Have update request Data ,which needs to update into the DB
     * @return Boolean Update Flag
     */
    @Override
    public boolean updateLoan(LoanDto loanDto) {

       Loans loans= loansRepository.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(
                ()->new ResourceNotFoundException("Loans","LoanNumber",loanDto.getLoanNumber())
        );
        LoansMapper.toLoans(loanDto,loans);
        loansRepository.save(loans);
        return true;
    }

    /**
     * @param mobileNumber accept contact Number Type of String
     * @return Boolean Delete Flag
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {

        Loans loans=loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Loans","MobileNumber",mobileNumber));
        loansRepository.deleteById(loans.getLoadId());
        return true;
    }


}
