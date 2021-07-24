package com.example.demo.service;


import com.example.demo.domin.Account;
import com.example.demo.domin.Contribute;
import com.example.demo.domin.Member;

import java.util.List;

public interface AccountService {

    Account getAccount(String qq);
    Account getAccountByXcxuid(String uid);
    void updateState(Account account);
    void updateAccount(Account Account);
    boolean accountExist(String qq);


    Member getMember(int id);
    boolean memberExist(int id);
    void insertMember(Member member);
    void updateMember(Member member);
    Contribute getContribute(int id);
    boolean contributeExist(int id);
    void insertContribute(Contribute contribute);
    void updateContribute(Contribute contribute);
}
