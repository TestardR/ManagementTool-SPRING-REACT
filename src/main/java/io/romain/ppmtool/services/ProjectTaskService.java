package io.romain.ppmtool.services;

import io.romain.ppmtool.domain.Backlog;
import io.romain.ppmtool.domain.ProjectTask;
import io.romain.ppmtool.repositories.BacklogRepository;
import io.romain.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        // Exceptions: Project not found

        // PTs to be added to a specific project, project != null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        // set the bl to pt
        projectTask.setBacklog(backlog);
        // sequence to be like this : IDPRO-1 IDPRO-2 ... 100 101
        Integer BacklogSequence = backlog.getPTSequence();
        // Update the BL SEQUENCE
        BacklogSequence++;

        // Add Sequence to Project Task
        projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        // Initial priority when prioriy null
  //      if(projectTask.getPriority()==0 || projectTask.getPriority()==null){
     //       projectTask.setPriority(3);
    //    }
        // initial status when status is null
        if(projectTask.getStatus()=="" || projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }
}
