package com.example.demo.service;

import com.example.demo.domin.Forum;
import com.example.demo.domin.Pointlog;

import java.util.List;

public interface PointService {
    void insertPointlog(Pointlog pointlog);
    List<Forum> getForumListByXcxuid(String xcxuid);
    Forum getForum(int id);
    Forum getbookedForum(int id);
    boolean forumExist(int id);
    boolean bookedForumExist(int id);
    void insertForum(Forum forum);
    void insertbookedForum(Forum forum);
    List<Forum> getInformableForumList();
    void insertInformedForum(int forum_id);
}
