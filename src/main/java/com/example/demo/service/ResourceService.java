package com.example.demo.service;


import com.example.demo.domin.Image;
import com.example.demo.domin.Topic;
import com.example.demo.domin.Zf;

import java.util.List;

public interface ResourceService {
    List<Topic> getZfList();

    Topic getZfTopic(Topic topic);

    Boolean ZfTopicExist(Topic topic);

    void insertZfTopic(Topic topic);

    List<Topic> getSendTopicList();

    List<Topic> getActTopicList();

    Zf getLastZf(Zf zf);

    void insertZf(Zf zf);

    List<Image> getImageListByForum_id(int id);

    List<Image> getThumbListByForum_id(int id);

    void insertImage(Image image);
}
