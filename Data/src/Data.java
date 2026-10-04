import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Data extends UnicastRemoteObject implements DataIF {
	
	protected static StudentList studentList;
	protected static CourseList courseList;
	
	private static final long serialVersionUID = 1L;
	
	protected Data() throws RemoteException {
		super();
	}
	
	public static void main(String[] arg) throws FileNotFoundException, IOException {
		try {
			Data data = new Data();
			Naming.rebind("Data", data);
			System.out.println("Data is ready !!!");
			
			studentList = new StudentList("Students.txt");
			courseList = new CourseList("Courses.txt");
			
		} catch (RemoteException e) {
			e.printStackTrace();
			
		} catch (MalformedURLException e) {
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
	
}
