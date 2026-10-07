import java.rmi.Naming;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client {

	public static void main(String[] args) throws NotBoundException, IOException {
		ServerIF server;
		
		BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			server = (ServerIF) Naming.lookup("Server");
			String currentUserId = null;

			
			while (true) {

			    System.out.print("Login ID: ");
			    String id = objReader.readLine().trim();

			    System.out.print("Password: ");
			    String password = objReader.readLine().trim();

			    if (server.authenticate(id, password)) {
			    		currentUserId = id;
			        System.out.println("Login successful.");
			        break;
			    }

			    System.out.println("Invalid ID or password.");
			}
			
			while (true) {

			    System.out.println("\n******************** MENU ********************");
			    System.out.println("1. List Students");
			    System.out.println("2. List Courses");
			    System.out.println("3. List Registered Courses by Student");
			    System.out.println("4. List Registered Students by Course");
			    System.out.println("5. List Completed Courses by Student");
			    System.out.println("6. Register Course");
			    System.out.println("X. Exit");

			    String sChoice = objReader.readLine().trim();

			    if (sChoice.equals("1")) {
			    		server.logCommand(currentUserId, "LIST_STUDENTS");
			    		
			        ArrayList<Student> students = server.getAllStudentData();
			        showstuinfo(students);
			    }

			    else if (sChoice.equals("2")) {
			    		server.logCommand(currentUserId, "LIST_COURSES");
			    	
			        ArrayList<Course> courses = server.getAllCourseData();
			        showcourinfo(courses);
			    }
			    
			    else if (sChoice.equals("3")) {
			    		server.logCommand(currentUserId, "LIST_REGISTERED_COURSES");
			    		
			        System.out.print("Student ID: ");
			        String studentId = objReader.readLine().trim();

			        ArrayList<Course> registeredCourses =
			                server.getRegisteredCourses(studentId);

			        if (!registeredCourses.isEmpty()) {
			            showcourinfo(registeredCourses);
			        } else {
			            System.out.println("No registered courses.");
			        }
			    }
			    
			    else if (sChoice.equals("4")) {
			    		server.logCommand(currentUserId, "LIST_REGISTERED_STUDENTS");
			    	

			        System.out.print("Course ID: ");
			        String courseId = objReader.readLine().trim();

			        ArrayList<Student> registeredStudents =
			                server.getRegisteredStudents(courseId);

			        if (!registeredStudents.isEmpty()) {
			            showstuinfo(registeredStudents);
			        } else {
			            System.out.println("No registered students.");
			        }
			    }
			    
			    else if (sChoice.equals("5")) {
			    		server.logCommand(currentUserId, "LIST_COMPLETED_COURSES");
			        
			    		System.out.print("Student ID: ");
			        String studentId = objReader.readLine().trim();

			        ArrayList<Course> completedCourses =
			                server.getCompletedCourseData(studentId);

			        if (!completedCourses.isEmpty()) {
			            showcourinfo(completedCourses);
			        } else {
			            System.out.println("Student not found or no completed courses.");
			        }
			    }
			    
			    else if (sChoice.equals("6")) {
			    	server.logCommand(currentUserId, "REGISTER_COURS");
			    	
			        System.out.print("Student ID: ");
			        String studentId = objReader.readLine().trim();

			        System.out.print("Course ID: ");
			        String courseId = objReader.readLine().trim();

			        String result = server.registerCourse(studentId, courseId);

			        System.out.println(result);
			    }

			    else if (sChoice.equalsIgnoreCase("X")) {
			        System.out.println("Program terminated.");
			        break;
			    }
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	

	// showcourinfo랑 showstuinfo랑 모양 똑같이 만들기
	private static void showcourinfo(ArrayList<Course> courses) {
		System.out.printf("%-10s | %-10s | %-35s | %-5s | %-5s | %-5s | %s%n",
				        "CourseNum", "Professor", "CourseName",
				        "Day", "Start", "End", "PreCourseList");
		
				System.out.println("------------------------------------------------------------------------------------------------------");
		
		for (Course course : courses) {
			
			System.out.printf("%-10s | %-10s | %-35s | %-5s | %-5d | %-5d | %s%n",
			        course.getCourseNum(),
			        course.getProfessor(),
			        course.getCourseName(),
			        course.getDay(),
			        course.getStartTime(),
			        course.getEndTime(),
			        course.getPreCourses());
		}
	}

	private static void showstuinfo(ArrayList<Student> students) {
		System.out.printf("%-10s | %-15s | %-5s | %s%n",
				"StudentID", "Name", "Dpt", "CompletedCourseList");
		
		System.out.println("------------------------------------------------------------------");
		
		for (Student student : students) {
			System.out.printf("%-10s | %-15s | %-5s | %s%n",
					student.getStudentId(),
					student.getName(),
					student.getdepartment(),
					student.getCompletedCourses());
		}
	}
}
