package pl.coderslab.program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;
import pl.coderslab.models.User;

// Program 4: SOLUTIONS

public class Program4 {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school?useSSL=false","root","root")) {
			
			boolean program_working=true;
			Scanner scan = new Scanner(System.in);
			
			while (program_working) {
				
				System.out.println("Choose one of the following options: ");
				System.out.println("* add - to assign an exercise to a user");
				System.out.println("* view - to show user's solutions");
				System.out.println("* quit - to exit");
				String option = scan.nextLine();
				
				if (option.equals("add")) {
					
					User[] userArray = User.loadAllUsers(conn);
					System.out.println("----------------------------");
					System.out.println("List of users:");
					for (User user : userArray) {
						System.out.println("User " + user.getId() + ": " + user.getUsername());
					}
					System.out.println("----------------------------");
					System.out.println("Select id of a user:");
					int users_id = Integer.parseInt(scan.nextLine());
					
					System.out.println("----------------------------");
					Exercise[] exerciseArray = Exercise.loadAllExercises(conn);
					System.out.println("List of exercises:");
					for (Exercise exercise : exerciseArray) {
						System.out.println("Exercise " + exercise.getId() + ": " + exercise.getTitle());
					}
					System.out.println("----------------------------");
					System.out.println("Select id of an exercise:");
					int exercise_id = Integer.parseInt(scan.nextLine());
					
					Solution[] sArray = Solution.loadAllByUserId(conn,users_id);
					
					if (sArray.length==0) {
						Solution newSolution = new Solution(Solution.getCurrentDateTime(), null, null, exercise_id, users_id);
						newSolution.saveToDB(conn);
						System.out.println("Solution correctly assigned to a user.");
						System.out.println("======================================");
					} else {
						boolean solutionExists = false;
						for (Solution solution : sArray) {
							if (solution.getExercise_id()==exercise_id) {
								System.out.println("Exercise is currently assigned to this user!");
								System.out.println("======================================");
								solutionExists = true;
								break;
							}
						}
						if (solutionExists==false) {
							Solution newSolution = new Solution(Solution.getCurrentDateTime(), null, null, exercise_id, users_id);
							newSolution.saveToDB(conn);
							System.out.println("Solution correctly assigned to a user.");
							System.out.println("======================================");
						}
						
					}
						
				} else if (option.equals("view")) {
					System.out.println("Enter user id to view her/his solutions: ");
					int users_id = Integer.parseInt(scan.nextLine());
					Solution[] solutionsArray = Solution.loadAllByUserId(conn, users_id);
					
					for (Solution solution : solutionsArray) {
						if (solution.getUpdated()!=null) {
							System.out.println("Exercise " + solution.getExercise_id() + ": created: " + solution.getCreated()
							+ ", updated: " + solution.getUpdated()
							+ ", description: " + solution.getDescription());
						}
					}
					
					System.out.println("======================================");
					

				} else if (option.equals("quit")) {
					
					program_working=false;

				} else {
					System.out.println("Wrong value!");
					System.out.println("======================================");
				}
			}
			
			scan.close();
			
			System.out.println("======================================");
			System.out.println("End of program");
			System.out.println("======================================");
			
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		
	}

}
