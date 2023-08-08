package com.azad.simpleprojectmanagement.models.project;

import com.azad.simpleprojectmanagement.models.auth.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProjectDto extends Project {

    private Long id;
    private AppUser projectManager;
}
