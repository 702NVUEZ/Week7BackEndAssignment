package projects.service;

import java.util.List;
import java.util.NoSuchElementException;
import projects.dao.ProjectDao;
import projects.entity.Project;

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

}//end class
