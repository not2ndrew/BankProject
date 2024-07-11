package com.example.demo.Entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {

    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    private Long id;

    @Column(name = "first_name")
    private String fname;

    @Column(name = "last_name")
    private String lname;

    private String email;
    private String password;

    // Format is YYYY-MM-DD
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dob;

    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer age;

    @OneToMany
    private List<Account> listOfAccounts;

    // There is no setter for age.
    // The only way to modify your age is through the dob setter.
    public int getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }


    public MyUser(String fname, String lname, String email, String password, LocalDate dob, List<Account> listOfAccounts) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.listOfAccounts = listOfAccounts;
    }

    @Override
    public String toString() {
        return "[id: " 
        + id + 
        ", fname: " + fname + 
        ", lname: " + lname + 
        ", email: "+ email + 
        ", password: " + password + 
        ", dob: " + dob + 
        ", age: " + this.getAge() + "]";
    }
}
