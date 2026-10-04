import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface DataIF extends Remote {
	ArrayList<Student> getAllStudentData() throws RemoteException;
	
	ArrayList<Course> getAllCourseData() throws RemoteException;
	
	Student getStudent(String studentId) throws RemoteException;
}
