package com.example.demo.Dto;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/* DTO is Data Transfer Object */
/* DTO is used for simplicity and security purposes */
/* In this case, the age and Bank Accounts are unnecessary for the user and/or server to see */
/* DTO's purpose is to transfer data to the client OR the server. It does not need to be modified while transfering. */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyUserRequest {

    private String fname;
    private String lname;
    private String email;
    private String password;

    // Format is YYYY-MM-DD
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dob;

    public int getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }
}
