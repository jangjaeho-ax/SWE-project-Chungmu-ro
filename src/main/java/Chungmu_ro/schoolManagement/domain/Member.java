package Chungmu_ro.schoolManagement.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Member {


    @Column(name = "AccountID")
    private String AccountId;
    @Column(name = "AccountPW")
    private String AccountPw;
    @Column(name = "FirstName")
    private String fisrtName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Email")
    private String email;


    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getAccountPw() {
        return AccountPw;
    }

    public void setAccountPw(String accountPw) {
        AccountPw = accountPw;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Member() {
    }

    public Member(String accountId, String accountPw, String fisrtName, String lastName, String email) {
        AccountId = accountId;
        AccountPw = accountPw;
        this.fisrtName = fisrtName;
        this.lastName = lastName;
        this.email = email;
    }
}
