package com.example.demo.service.impl;

import com.example.demo.domin.Signon;
import com.example.demo.persistence.SignonMapper;
import com.example.demo.service.SignonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SignonServiceImpl implements SignonService {
    @Autowired
    private SignonMapper signonMapper;

    @Override
    public void insertSignon(Signon signon) {
        signonMapper.insertSignon(signon);
    }


}
