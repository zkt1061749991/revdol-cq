package com.example.demo.service.impl;

import com.example.demo.domin.Image;
import com.example.demo.domin.Topic;
import com.example.demo.domin.Zf;
import com.example.demo.persistence.ResourceMapper;
import com.example.demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Topic> getZfList() {
        return resourceMapper.getZfList();
    }

    @Override
    public Topic getZfTopic(Topic topic) {
        return resourceMapper.getZfTopic(topic);
    }

    @Override
    public Boolean ZfTopicExist(Topic topic) {
        if(resourceMapper.getZfTopic(topic) != null)
            return true;
        else return false;
    }

    @Override
    public void insertZfTopic(Topic topic) {
        resourceMapper.insertZfTopic(topic);
    }

    @Override
    public List<Topic> getSendTopicList() {
        return  resourceMapper.getSendTopicList();
    }

    @Override
    public List<Topic> getActTopicList() {
        return resourceMapper.getActTopicList();
    }

    @Override
    public Zf getLastZf(Zf zf) {
        return resourceMapper.getLastZf(zf);
    }


    @Override
    public void insertZf(Zf zf) {
        resourceMapper.insertZf(zf);
    }

    @Override
    public List<Image> getImageListByForum_id(int id) {
        return resourceMapper.getImageListByForum_id(id);
    }

    @Override
    public List<Image> getThumbListByForum_id(int id) {
        return resourceMapper.getThumbListByForum_id(id);
    }

    @Override
    public void insertImage(Image image) {
        resourceMapper.insertImage(image);
    }

}
