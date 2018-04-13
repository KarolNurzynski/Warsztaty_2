package pl.coderslab.program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import pl.coderslab.models.User_Group;

// Program 3: GROUPS

public class Program3 {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school?useSSL=false","root","root")) {
			
			boolean program_working=true;
			Scanner scan = new Scanner(System.in);
			
			while (program_working) {
				
				User_Group[] groupArray = User_Group.loadAllGroups(conn);
				
				System.out.println("List of groups:");
				for (User_Group group : groupArray) {
					System.out.println("Group " + group.getId() + ": " + group.getName());
				}
				
				System.out.println("----------------------------");
				System.out.println("Choose one of the following options: ");
				System.out.println("* add - to add new group");
				System.out.println("* edit - to edit existing group");
				System.out.println("* delete - to delete existing group");
				System.out.println("* quit - to exit");
				
				String option = scan.nextLine();
				
				if (option.equals("add")) {
					System.out.println("Enter name: ");
					String name = scan.nextLine();
					
					new User_Group(name).saveToDB(conn);
					System.out.println("New group correctly added.");
					System.out.println("======================================");
					
				} else if (option.equals("edit")) {
					System.out.println("Enter group id to edit: ");
					int id = Integer.parseInt(scan.nextLine());
					System.out.println("Enter new group name: ");
					String name = scan.nextLine();
				
					User_Group group = User_Group.loadById(conn, id);
					group.setName(name);
					group.modifyGroup(conn);
					System.out.println("Group correctly edited.");
					System.out.println("======================================");

				} else if (option.equals("delete")) {
					
					System.out.println("Enter group id: ");
					int id = Integer.parseInt(scan.nextLine());
					
					User_Group.loadById(conn, id).delete(conn);
					System.out.println("Group deleted.");
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
