package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {

	//week 9 
	private Scanner scanner = new Scanner(System.in);
	  private ProjectService projectService = new ProjectService();
	  private Project curProject;

	  // @formatter:off
	  private List<String> operations = List.of(
	      "1) Add a project",
		  "2) List projects",
		  "3) Select a project",
		  "4) Update project details",
		  "5) Delete a project"
	  );
	  
	  // @formatter:on
	  public static void main(String[] args) {
		    new ProjectsApp().processUserSelections();
		  }//end method

	  private void processUserSelections() {
		    boolean done = false;

		    while(!done) {
		      try {
		        int selection = getUserSelection();

		        switch(selection) {
		          case -1:
		            done = exitMenu();
		            break;

		          case 1:
		            createProject();
		            break;
		            
		           //week 10
		          case 2:
		            listProjects();
		            break;

		          //week 10
		          case 3:
		            selectProject();
		            break;
		            
		          //week 11
		          case 4:
						updateProjectDetails();
						break;
					
				  //week 11	
		          case 5:
						deleteProject();
						break;


		          default:
		            System.out.println("\n" + selection + " is not a valid selection. Try again.");
		            break;
		        }
		      }
		      catch(Exception e) {
		    	  System.out.println("\nError: " + e + " Try again.");
  }
 }
}

	  //week 10
	  private void selectProject() {
		    listProjects();
		    Integer projectId = getIntInput("Enter a project ID to select a project");

		    //Unselects the current project. 

		    curProject = null;

		    //This will throw an exception if an invalid project ID is entered. 
		    curProject = projectService.fetchProjectById(projectId);
		    
		  }//end method
	  
	  
	  //week 10
	  private void listProjects() {
		    List<Project> projects = projectService.fetchAllProjects();

		    System.out.println("\nProjects:");

		    projects.forEach(project -> System.out
		        .println("   " + project.getProjectId() + ": " + project.getProjectName()));
		  }//end method
	 
	  //week 11
	  private void deleteProject() {
			listProjects();
			Integer projectId = getIntInput("Enter the project ID of the project you wish to delete.");
			
			projectService.deleteProject(projectId);
			System.out.println("Project " + projectId + " was successfully deleted.");
			
			if(Objects.nonNull(curProject) && curProject.getProjectId().equals(projectId)) {
				curProject = null;
			}

	}//end method
	  
	  
	  //week 11 
	  private void updateProjectDetails() {
			if(Objects.isNull(curProject)) {
				System.out.println("\nPlease select a project.");
				return;
			}
			
			String projectName = getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
			
			BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
			
			BigDecimal actualHours = getDecimalInput("Enter the actual hours + [" + curProject.getActualHours() + "]");
			
			Integer difficulty = getIntInput("Enter the project difficulty (1-5) [" + curProject.getDifficulty() + "]");
			
			String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");
			
			Project project = new Project();
			
			project.setProjectId(curProject.getProjectId());
			project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
			
			project.setEstimatedHours(Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);
			
			project.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
			project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty() : difficulty);
			project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
			
			projectService.modifyProjectDetails(project);
			
			curProject = projectService.fetchProjectById(curProject.getProjectId());
	}//end method
	  
	  
	  private void createProject() {
		    String projectName = getStringInput("Enter the project name");
		    BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		    BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		    Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		    String notes = getStringInput("Enter the project notes");

		    Project project = new Project();

		    project.setProjectName(projectName);
		    project.setEstimatedHours(estimatedHours);
		    project.setActualHours(actualHours);
		    project.setDifficulty(difficulty);
		    project.setNotes(notes);

		    Project dbProject = projectService.addProject(project);
		    System.out.println("You have successfully created project: " + dbProject);
		  }//end method 
	  
	  
	  private BigDecimal getDecimalInput(String prompt) {
		    String input = getStringInput(prompt);

		    if(Objects.isNull(input)) {
		      return null;
		    }

		    try {
		      /* Create the BigDecimal object and set it to two decimal places (the scale). */
		      return new BigDecimal(input).setScale(2);
		    }
		    catch(NumberFormatException e) {
		      throw new DbException(input + " is not a valid decimal number.");
		    }
		  }//end method
	  
	  private boolean exitMenu() {
		    System.out.println("Exiting the menu.");
		    return true;
		  }//end method
	  
	  private int getUserSelection() {
		    printOperations();

		    Integer input = getIntInput("Enter a menu selection");

		    return Objects.isNull(input) ? -1 : input;
		  }//end method
	  
	  private Integer getIntInput(String prompt) {
		    String input = getStringInput(prompt);

		    if(Objects.isNull(input)) {
		      return null;
		    }

		    try {
		      return Integer.valueOf(input);
		    }
		    catch(NumberFormatException e) {
		      throw new DbException(input + " is not a valid number.");
		    }
		  }//end method
	  
	  private String getStringInput(String prompt) {
		    System.out.print(prompt + ": ");
		    String input = scanner.nextLine();

		    return input.isBlank() ? null : input.trim();
		  }//end method
	  
	  
		private void printOperations() {
			System.out.println();
			System.out.println("\nThese are the available selections. Press the Enter key to quit: ");

			operations.forEach(line -> System.out.println("   " + line));
			
			if(Objects.isNull(curProject)) {
				System.out.println("\nYou are not working with a project.");
			}
			else {
				System.out.println("\nYou are working with project: " + curProject);
			}
		} //end method
	  
}//end class
