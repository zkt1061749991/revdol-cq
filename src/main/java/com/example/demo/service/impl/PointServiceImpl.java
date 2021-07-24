package com.example.demo.service.impl;

import com.example.demo.domin.Forum;
import com.example.demo.domin.Pointlog;
import com.example.demo.persistence.PointMapper;
import com.example.demo.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PointServiceImpl implements PointService {
    @Autowired
    private PointMapper pointMapper;

    @Override
    public void insertPointlog(Pointlog pointlog) {
        pointMapper.insertPointlog(pointlog);
    }

    @Override
    public List<Forum> getForumListByXcxuid(String xcxuid) {
        return pointMapper.getForumListByXcxuid(xcxuid);
    }

    @Override
    public Forum getForum(int id) {
        return pointMapper.getForum(id);
    }

    @Override
    public Forum getbookedForum(int id) {
        return pointMapper.getbookedForum(id);
    }

    @Override
    public boolean forumExist(int id) {
        if(pointMapper.getForum(id) != null) {
            return true;
        }
        else return false;
    }

    @Override
    public boolean bookedForumExist(int id) {
        if(pointMapper.getbookedForum(id) != null) {
            return true;
        }
        else return false;
    }

    @Override
    public void insertForum(Forum forum) {
        pointMapper.insertForum(forum);
    }

    @Override
    public void insertbookedForum(Forum forum) {
        pointMapper.insertbookedForum(forum);
    }

    @Override
    public List<Forum> getInformableForumList() {
        return pointMapper.getInformableForumList();
    }

    @Override
    public void insertInformedForum(int forum_id) {
        pointMapper.insertInformedForum(forum_id);
    }
}
