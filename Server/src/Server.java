import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.NotBoundException;

public class Server extends UnicastRemoteObject implements ServerIF {

	private static DataIF data;
	private static final long serialVersionUID = 1L;
	
	protected Server() throws RemoteException {
		super();
	}
	
	public static void main(String[] arg) throws NotBoundException {
		try {
			Server server = new Server();
			Naming.rebind("Server", server);
			System.out.println("Server is ready !!!");
			
			
			data = (DataIF) Naming.lookup("Data");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Student> getAllStudentData() throws RemoteException {
		return data.getAllStudentData();
	}
	
	@Override
	public ArrayList<Course> getAllCourseData() throws RemoteException {
		return data.getAllCourseData();
	}
	
	@Override
	public Student getStudent(String studentId) throws RemoteException {
	    return data.getStudent(studentId);
	}
	
	@Override
	public ArrayList<Course> getCompletedCourseData(String studentId) throws RemoteException {
	    return data.getCompletedCourseData(studentId);
	}
	
	@Override
	public ArrayList<Course> getRegisteredCourses(String studentId) throws RemoteException {
	    return data.getRegisteredCourses(studentId);
	}
	
	@Override
	public ArrayList<Student> getRegisteredStudents(String courseId) throws RemoteException {
	    return data.getRegisteredStudents(courseId);
	}
	
	@Override
	public String registerCourse(String studentId, String courseId) throws RemoteException {
	    return data.registerCourse(studentId, courseId);
	}
	
	@Override
	public boolean authenticate(String id, String password) throws RemoteException {
	    return data.authenticate(id, password);
	}
	
	@Override
	public void logCommand(String userId, String commandType) throws RemoteException {
	    SystemLogger.log(userId, commandType);
	}
	
	@Override
	public String cancelCourse(String studentId, String courseId) throws RemoteException {
	    return data.cancelCourse(studentId, courseId);
	}

}
