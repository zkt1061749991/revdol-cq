package com.example.demo.persistence;

import com.example.demo.domin.Account;
import com.example.demo.domin.Contribute;
import com.example.demo.domin.Member;

import java.util.List;

public interface AccountMapper {

    Account getAccount(String qq);

    Account getAccountByXcxuid(String uid);

    void updateState(Account account);

    void updateAccount(Account account);

    Member getMember(int uid);

    Contribute getContribute(int id);

    void insertMember(Member member);

    void updateMember(Member member);

    void insertContribute(Contribute contribute);

    void updateContribute(Contribute contribute);

}
