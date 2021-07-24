package com.example.demo.persistence;


import com.example.demo.domin.Image;
import com.example.demo.domin.Topic;
import com.example.demo.domin.Zf;

import java.util.List;

public interface ResourceMapper {
    List<Topic> getZfList();
    void insertZfTopic(Topic topic);
    Topic getZfTopic(Topic topic);
    Zf getLastZf(Zf zf);
    List<Topic> getSendTopicList();
    List<Topic> getActTopicList();
    void insertZf(Zf zf);
    List<Image> getImageListByForum_id(int id);
    List<Image> getThumbListByForum_id(int id);
    void insertImage(Image image);
}
