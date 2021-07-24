package com.example.demo.service.impl;

import com.example.demo.domin.Event;
import com.example.demo.persistence.NoticeMapper;
import com.example.demo.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Event> getNowIndexBanner() {
        return noticeMapper.getNowIndexBanner();
    }
}
