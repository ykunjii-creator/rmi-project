import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Data extends UnicastRemoteObject implements DataIF {
	
	protected static StudentList studentList;
	protected static CourseList courseList;
	protected static HashMap<String, ArrayList<String>> registrationMap;
	protected static AccountList accountList;
	
	private static final long serialVersionUID = 1L;
	
	protected Data() throws RemoteException {
		super();
	}
	
	public static void main(String[] arg) throws FileNotFoundException, IOException {
		try {
			Data data = new Data();
			Naming.rebind("Data", data);
			System.out.println("Data is ready !!!");
			
			studentList = new StudentList("../data/Students.txt");
			courseList = new CourseList("../data/Courses.txt");
			
			registrationMap = new HashMap<String, ArrayList<String>>();
			loadRegistrations("../data/Registrations.txt");
			
			accountList = new AccountList("../data/Accounts.txt");
			
		} catch (RemoteException e) {
			e.printStackTrace();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadRegistrations(String fileName) throws IOException {

	    BufferedReader reader = new BufferedReader(new FileReader(fileName));

	    String line;

	    while ((line = reader.readLine()) != null) {

	        if (line.trim().isEmpty()) {
	            continue;
	        }

	        String[] parts = line.trim().split("\\s+");

	        String studentId = parts[0];
	        String courseId = parts[1];

	        ArrayList<String> courseIds = registrationMap.get(studentId);

	        if (courseIds == null) {
	            courseIds = new ArrayList<String>();
	            registrationMap.put(studentId, courseIds);
	        }

	        if (!courseIds.contains(courseId)) {
	            courseIds.add(courseId);
	        }
	    }

	    reader.close();
	}
	
	public ArrayList<Course> getRegisteredCourses(String studentId) {
	    ArrayList<Course> registeredCourses = new ArrayList<Course>();

	    ArrayList<String> courseIds = registrationMap.get(studentId);

	    if (courseIds == null) {
	        return registeredCourses;
	    }

	    for (String courseId : courseIds) {
	        Course course = courseList.getCourse(courseId);

	        if (course != null) {
	            registeredCourses.add(course);
	        }
	    }

	    return registeredCourses;
	}
	
	private static void saveRegistration(String studentId, Course course) {

	    try (FileWriter writer =
	            new FileWriter("../data/Registrations.txt", true)) {

	        writer.write(
	                studentId + " | "
	                + course.getCourseNum() + " | "
	                + course.getProfessor() + " | "
	                + course.getCourseName() + " | "
	                + course.getDay() + " "
	                + course.getStartTime() + "-"
	                + course.getEndTime()
	                + System.lineSeparator()
	        );

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public ArrayList<Student> getAllStudentData() throws RemoteException {
		return studentList.getAllStudentRecords();
	}
	
	@Override
	public ArrayList<Course> getAllCourseData() throws RemoteException {
		return courseList.getAllCourseRecords();
	}
	
	@Override
	public Student getStudent(String studentId) throws RemoteException {
	    return studentList.getStudent(studentId);
	}
	
	@Override
	public ArrayList<Course> getCompletedCourseData(String studentId) throws RemoteException {

	    Student student = studentList.getStudent(studentId);

	    if (student == null) {
	        return new ArrayList<Course>();
	    }

	    ArrayList<Course> completedCourses = new ArrayList<Course>();

	    for (String courseId : student.getCompletedCourses()) {
	        Course course = courseList.getCourse(courseId);

	        if (course != null) {
	            completedCourses.add(course);
	        }
	    }

	    return completedCourses;
	}
	
	@Override
	public String registerCourse(String studentId, String courseId) throws RemoteException {

	    Student student = studentList.getStudent(studentId);
	    Course course = courseList.getCourse(courseId);

	    if (student == null) {
	        return "Student not found.";
	    }

	    if (course == null) {
	        return "Course not found.";
	    }
	    
	    if (student.getCompletedCourses().contains(courseId)) {
	        return "Course already completed.";
	    }

	    ArrayList<String> courseIds = registrationMap.get(studentId);

	    if (courseIds == null) {
	        courseIds = new ArrayList<String>();
	        registrationMap.put(studentId, courseIds);
	    }

	    if (courseIds.contains(courseId)) {
	        return "Already registered.";
	    }
	    
	    for (String registeredCourseId : courseIds) {

	        Course registeredCourse = courseList.getCourse(registeredCourseId);

	        if (registeredCourse != null) {

	            boolean sameDay =
	                    registeredCourse.getDay().equals(course.getDay());

	            boolean timeOverlap =
	                    course.getStartTime() < registeredCourse.getEndTime()
	                    && course.getEndTime() > registeredCourse.getStartTime();

	            if (sameDay && timeOverlap) {
	                return "Schedule conflict.";
	            }
	        }
	    }

	    courseIds.add(courseId);
	    
	    saveRegistration(studentId, course);


	    return "Registration completed.";
	}
	
	@Override
	public ArrayList<Student> getRegisteredStudents(String courseId) throws RemoteException {

	    ArrayList<Student> registeredStudents = new ArrayList<Student>();

	    for (String studentId : registrationMap.keySet()) {

	        ArrayList<String> courseIds = registrationMap.get(studentId);

	        if (courseIds != null && courseIds.contains(courseId)) {

	            Student student = studentList.getStudent(studentId);

	            if (student != null) {
	                registeredStudents.add(student);
	            }
	        }
	    }

	    return registeredStudents;
	}
	
	@Override
	public boolean authenticate(String id, String password) throws RemoteException {
	    return accountList.authenticate(id, password);
	}
	
}
