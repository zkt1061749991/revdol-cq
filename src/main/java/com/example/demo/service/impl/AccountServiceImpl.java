package com.example.demo.service.impl;

import com.example.demo.domin.Account;
import com.example.demo.domin.Contribute;
import com.example.demo.domin.Member;
import com.example.demo.persistence.AccountMapper;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account getAccount(String qq) {
        return accountMapper.getAccount(qq);
    }

    @Override
    public Account getAccountByXcxuid(String uid) {
        return accountMapper.getAccountByXcxuid(uid);
    }

    @Override
    public void updateState(Account account) {
        accountMapper.updateState(account);
    }

    @Override
    public void updateAccount(Account Account) {
        accountMapper.updateAccount(Account);
    }

    @Override
    public boolean accountExist(String qq){
        if(accountMapper.getAccount(qq) != null)
            return true;
        else return false;
    }

    @Override
    public Member getMember(int uid) {
        return accountMapper.getMember(uid);
    }

    @Override
    public boolean memberExist(int uid) {
        if(accountMapper.getMember(uid) != null)
            return true;
        else return false;
    }

    @Override
    public void insertMember(Member member) {
        accountMapper.insertMember(member);
    }

    @Override
    public void updateMember(Member member) {
        accountMapper.updateMember(member);
    }

    @Override
    public Contribute getContribute(int id) {
        return accountMapper.getContribute(id);
    }

    @Override
    public boolean contributeExist(int id) {
        if(accountMapper.getContribute(id) != null)
            return true;
        else return false;
    }

    @Override
    public void insertContribute(Contribute contribute) {
        accountMapper.insertContribute(contribute);
    }

    @Override
    public void updateContribute(Contribute contribute) {
        accountMapper.updateContribute(contribute);
    }


}
