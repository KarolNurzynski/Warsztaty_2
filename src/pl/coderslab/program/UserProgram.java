package pl.coderslab.program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import pl.coderslab.models.Solution;


public class UserProgram {
	
	public static void main(String[] args) {
		
		if (args.length!=0) {
			
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school?useSSL=false","root","root")) {
				
				int users_id = Integer.parseInt(args[0]);
				
				boolean program_working = true;
				Scanner scan = new Scanner(System.in);
				
				while (program_working) {
					
					System.out.println("Choose one of the following options: ");
					System.out.println("* add - to add a new solution to exercise");
					System.out.println("* view - to show your solutions");
					System.out.println("* quit - to exit");
					String option = scan.nextLine();
					
					if (option.equals("add")) {
						
						Solution[] sArray = Solution.loadAllByUserId(conn,users_id);
						
						System.out.println("----------------------------");
						System.out.println("List of solutions to be added:");
						for (Solution solution : sArray) {
							if (solution.getUpdated()==null) {
								System.out.println("Exercise " + solution.getExercise_id() + "- created: " + solution.getCreated()
								+ ", description: " + solution.getDescription());	
							}
						}
						
						System.out.println("----------------------------");
						System.out.println("Choose the exercise id to which you want to add a solution: ");
						int exercise_id = Integer.parseInt(scan.nextLine());
						
						for (Solution solution : sArray) {
							if (solution.getExercise_id() == exercise_id) {
								if (solution.getUpdated()==null) {
									System.out.println("Enter your solution:");
									String description = scan.nextLine();
									solution.setDescription(description);
									solution.setUpdated(Solution.getCurrentDateTime());
									solution.modifySolution(conn);
									System.out.println("Solution correctly added.");
									System.out.println("======================================");
								} else {
									System.out.println("Solution to this exercise already provided.");
									System.out.println("======================================");
								}
							}
						}

					} else if (option.equals("view")) {
						
						Solution[] sArray = Solution.loadAllByUserId(conn,users_id);
						
						System.out.println("List of your solutions: ");
						for (Solution solution : sArray) {
							if (solution.getUpdated()!=null) {
								System.out.println("Exercise " + solution.getExercise_id() + ": "
													+ solution.getDescription());
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
				// TODO: handle exception
			}
			
		} else {
			System.out.println("You need to introduce your id as program parameter to log in!");
		}

	}
}
