package com.mymicroservice.accounts.dto;

/**
 * Account Confirmation text and mail Dto
 * @param AccountNumber
 * @param name
 * @param email
 * @param mobileNumber
 */
public record AccountMsgDto(Long AccountNumber,String name,String email,String mobileNumber) {
}
