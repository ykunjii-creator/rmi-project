import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerIF extends Remote {
	ArrayList<Student> getAllStudentData() throws RemoteException;
	
	ArrayList<Course> getAllCourseData() throws RemoteException;
	
	Student getStudent(String studentId) throws RemoteException;
	
	ArrayList<Course> getCompletedCourseData(String studentId) throws RemoteException;
	
	ArrayList<Course> getRegisteredCourses(String studentId) throws RemoteException;
	
	String registerCourse(String studentId, String courseId) throws RemoteException;

	ArrayList<Student> getRegisteredStudents(String courseId) throws RemoteException;
}
