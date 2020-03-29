package com.danish.pmt.services;

import com.danish.pmt.domain.Project;
import com.danish.pmt.exceptions.ProjectIdException;
import com.danish.pmt.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch(Exception ex) {
          throw new ProjectIdException("Project Identifier '" + project.getProjectIdentifier().toUpperCase()+ "' already exists");
        }
    }

    public Project findProjectByIdentifier(String Identifier){
        Project project = projectRepository.findByProjectIdentifier(Identifier.toUpperCase());

        if(project == null){
            throw new RuntimeException("Project with Identifier '" + Identifier + "' Not found");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null)
            throw new RuntimeException("Project with Id '" + projectId+"' not found");

        projectRepository.delete(project);
    }
}
