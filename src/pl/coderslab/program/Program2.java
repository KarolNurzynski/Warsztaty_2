package pl.coderslab.program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import pl.coderslab.models.Exercise;
import pl.coderslab.models.User_Group;

// Program 2: EXERCISES

public class Program2 {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school?useSSL=false","root","root")) {
			
			boolean program_working=true;
			Scanner scan = new Scanner(System.in);
			
			while (program_working) {
				
				Exercise[] exerciseArray = Exercise.loadAllExercises(conn);
				System.out.println("List of exercises:");
				for (Exercise exercise : exerciseArray) {
					System.out.println("Exercise " + exercise.getId() + ": " + exercise.getTitle() + ", " + exercise.getDescription());
				}
				
				System.out.println("----------------------------");
				System.out.println("Choose one of the following options: ");
				System.out.println("* add - to add new exercise");
				System.out.println("* edit - to edit existing exercise");
				System.out.println("* delete - to delete existing exercise");
				System.out.println("* quit - to exit");
				
				String option = scan.nextLine();
				
				if (option.equals("add")) {
					System.out.println("Enter title: ");
					String title = scan.nextLine();
					System.out.println("Enter description: ");
					String description = scan.nextLine();
					
					new Exercise(title, description).saveToDB(conn);
					System.out.println("Exercise correctly added.");
					System.out.println("======================================");
					
				} else if (option.equals("edit")) {
					System.out.println("Enter exercise id to edit: ");
					int id = Integer.parseInt(scan.nextLine());
					System.out.println("Enter new title: ");
					String title = scan.nextLine();
					System.out.println("Enter new descrption: ");
					String description = scan.nextLine();
					
					Exercise exercise = Exercise.loadById(conn, id);
					exercise.setTitle(title);
					exercise.setDescription(description);
					exercise.modifyExercise(conn);
					System.out.println("Exercise correctly modified.");
					System.out.println("======================================");

				} else if (option.equals("delete")) {
					
					System.out.println("Enter user id: ");
					int id = Integer.parseInt(scan.nextLine());
					
					Exercise.loadById(conn, id).delete(conn);
					System.out.println("Exercise deleted.");
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
