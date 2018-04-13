package pl.coderslab.program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import pl.coderslab.models.Exercise;
import pl.coderslab.models.User;
import pl.coderslab.models.User_Group;

//Program 1: USERS

public class Program1 {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school?useSSL=false","root","root")) {
			
			boolean program_working=true;
			Scanner scan = new Scanner(System.in);
			
			while (program_working) {
				
				User[] userArray = User.loadAllUsers(conn);
				System.out.println("List of users:");
				for (User user : userArray) {
					System.out.println("User " + user.getId() + ": " + user.getUsername() + ", " + user.getEmail()
										+ ", " + user.getPassword() + ", user group " + user.getUser_group_id());
				}
				
				System.out.println("----------------------------");
				System.out.println("Choose one of the following options: ");
				System.out.println("* add - to add new user");
				System.out.println("* edit - to edit existing user");
				System.out.println("* delete - to delete existing user");
				System.out.println("* quit - to exit");

				String option = scan.nextLine();
				
				if (option.equals("add")) {
					System.out.println("Enter username: ");
					String username = scan.nextLine();
					System.out.println("Enter email: ");
					String email = scan.nextLine();
					System.out.println("Enter password: ");
					String password = scan.nextLine();
					System.out.println("Enter user group id: ");
					int user_group_id = Integer.parseInt(scan.nextLine());
					new User(username, email, password, user_group_id).saveToDB(conn);
					
					System.out.println("User correctly added.");
					System.out.println("======================================");
					
				} else if (option.equals("edit")) {
					System.out.println("Enter user id to edit: ");
					int id = Integer.parseInt(scan.nextLine());
					System.out.println("Enter new username: ");
					String username = scan.nextLine();
					System.out.println("Enter new email: ");
					String email = scan.nextLine();
					System.out.println("Enter new password: ");
					String password = scan.nextLine();
					System.out.println("Enter new user group id: ");
					int user_group_id = Integer.parseInt(scan.nextLine());
					
					User user = User.loadUserById(conn, id);
					user.setUsername(username);
					user.setEmail(email);
					user.setPassword(password);
					user.setUser_group_id(user_group_id);
					user.modifyUser(conn);
					
					System.out.println("User correctly edited.");
					System.out.println("======================================");
					
				} else if (option.equals("delete")) {
					
					System.out.println("Enter user id: ");
					int id = Integer.parseInt(scan.nextLine());
					
					User.loadUserById(conn, id).delete(conn);
					System.out.println("User deleted.");
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
