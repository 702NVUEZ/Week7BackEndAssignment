package projects.service;

import java.util.List;
import java.util.NoSuchElementException;

import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;

public class ProjectService {

	private ProjectDao projectDao = new ProjectDao();

	  //week 10
	  public Project addProject(Project project) {
	    return projectDao.insertProject(project);
	  }
	  
	  //week 10
	  public List<Project> fetchAllProjects() {
		    return projectDao.fetchAllProjects();
		  }
	  
	  //week 10
	  public Project fetchProjectById(Integer projectId) {
		    return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException(
		        "Project with project ID=" + projectId + " does not exist."));
		  }

	  //week 11
	  public void modifyProjectDetails(Project project) {
			if(!projectDao.modifyProjectDetails(project)) {
				throw new DbException("Project with ID= " + project.getProjectId() + " does not exist." );
			}
		}//end method
	  
	  //week 11
	  public void deleteProject(Integer projectId) {
			if(!projectDao.deleteProject(projectId)) {
				throw new DbException("Project with ID=" + projectId + " does not exist.");
			}
		}//end method
	  
}//end class
