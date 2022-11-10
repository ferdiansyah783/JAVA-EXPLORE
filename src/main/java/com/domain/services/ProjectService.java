package com.domain.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.exception.NotFoundException;
import com.domain.models.dto.ProjectRequest;
import com.domain.models.entities.Project;
import com.domain.models.repositories.ProjectRepository;

@Service
@Transactional
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Project save(ProjectRequest projectRequest) {
        Project project = modelMapper.map(projectRequest, Project.class);

        return projectRepository.save(project);
    }

    public List<Project> find() {
        return projectRepository.findAll();
    }

    public Project findOne(Long id) throws NotFoundException {
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()) {
            throw new NotFoundException("Project Not Found");
        }

        return project.get();
    }

    public Project update(Long id, ProjectRequest projectRequest) throws NotFoundException {
        Optional<Project> projectDb = projectRepository.findById(id);

        if (!projectDb.isPresent()) {
            throw new NotFoundException("Project Not Found");
        }

        Project project = Project.build(id, projectRequest.getName(), projectRequest.getSource(),
                projectDb.get().getStatus(), projectRequest.getStart_date(), projectRequest.getEnd_date(), null);

        return projectRepository.save(project);
    }

    public void remove(Long id) throws Exception {
        try {
            this.findOne(id);
            projectRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }
}
