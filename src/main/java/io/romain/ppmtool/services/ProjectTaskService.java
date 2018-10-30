package io.romain.ppmtool.services;

import io.romain.ppmtool.domain.Backlog;
import io.romain.ppmtool.domain.Project;
import io.romain.ppmtool.domain.ProjectTask;
import io.romain.ppmtool.exceptions.ProjectNotFoundException;
import io.romain.ppmtool.repositories.BacklogRepository;
import io.romain.ppmtool.repositories.ProjectRepository;
import io.romain.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        try {
            // PTs to be added to a specific project, project != null, BL exists
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            // set the bl to pt
            projectTask.setBacklog(backlog);
            // sequence to be like this : IDPRO-1 IDPRO-2 ... 100 101
            Integer BacklogSequence = backlog.getPTSequence();
            // Update the BL SEQUENCE
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);

            // Add Sequence to Project Task
            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            // Initial priority when prioriy null
            if(projectTask.getPriority()==null){ // In the future we need projectTask.getPriority() == 0 to handle the form
                projectTask.setPriority(3);
            }
            // initial status when status is null
            if(projectTask.getStatus()=="" || projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e){
            throw new ProjectNotFoundException("Project Not Found");
        }


    }

    public Iterable<ProjectTask>findBacklogById(String id){

        Project project = projectRepository.findByProjectIdentifier(id);
        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '" + id + "' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){

        // make sure we are searching on the right backlog
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog==null){
            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");
        }

        // make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task: '"+pt_id+"' not found");
        }

        // make sure that the backlog/project id in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: '"+backlog_id);
        }

        return projectTask;
    }
}
