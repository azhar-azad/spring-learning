package com.azad.CampusConnectApi.utils;

import com.azad.CampusConnectApi.models.Link;
import com.azad.CampusConnectApi.models.entities.LinkEntity;
import com.azad.CampusConnectApi.models.entities.PostEntity;
import com.azad.CampusConnectApi.repositories.LinkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiUtils {

    @Autowired
    private ModelMapper modelMapper;

    public void saveLinksForPost(LinkRepository linkRepository, List<String> linkUrls, PostEntity post) {

        // Prepare the LinkEntity from linkUrl
        List<LinkEntity> links = new ArrayList<>();
        linkUrls.forEach(linkUrl -> {
            Link link = new Link(linkUrl);
            links.add(modelMapper.map(link, LinkEntity.class));
        });

        // Set post to link mapping (1-to-n) and save links
        for (LinkEntity link: links) {
            link.setPost(post);
            linkRepository.save(link);
        }
    }
}
