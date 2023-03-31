package com.azad.service;

import com.azad.data.models.entities.AccessEntity;
import com.azad.data.models.entities.AppUserEntity;
import com.azad.data.models.entities.TaskListEntity;
import com.azad.data.repos.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceUtils {

    @Autowired
    private AccessRepository accessRepository;

    public String getLoggedInUserAccess(AppUserEntity loggedInUser, TaskListEntity taskListEntity) {

        Optional<List<AccessEntity>> optionalAccessEntities = accessRepository.findByAppUserId(loggedInUser.getId());
        if (optionalAccessEntities.isPresent()) {
            List<AccessEntity> accessEntities = optionalAccessEntities.get();
            for (AccessEntity accessEntity: accessEntities) {
                if (accessEntity.getTaskListId().equals(taskListEntity.getId())) {
                    return accessEntity.getAccessName();
                }
            }
        }
        return null;
    }

    public AccessEntity getAccessByTaskListId(AppUserEntity loggedInUser, Long taskListId) {

        Optional<List<AccessEntity>> optionalAccessEntities = accessRepository.findByTaskListId(taskListId);
        if (!optionalAccessEntities.isPresent())
            return null;
        for (AccessEntity accessEntity: optionalAccessEntities.get()) {
            if (accessEntity.getAppUserId().equals(loggedInUser.getId()))
                return accessEntity;
        }
        return null;
    }
}
