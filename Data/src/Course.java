
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Course implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String courseNum;
    protected String professor;
    protected String courseName;
    
    protected String day;
    protected int startTime;
    protected int endTime;
    
    protected ArrayList<String> preCoursesList;

    
    public Course(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
    	this.courseNum = stringTokenizer.nextToken();
    
    	this.professor = stringTokenizer.nextToken();
   
    	this.courseName = stringTokenizer.nextToken();
    	
    	this.day = stringTokenizer.nextToken();
    	this.startTime = Integer.parseInt(stringTokenizer.nextToken());
    	this.endTime = Integer.parseInt(stringTokenizer.nextToken());
    	
    	this.preCoursesList = new ArrayList<String>();
    	
    	while (stringTokenizer.hasMoreTokens()) {
    		this.preCoursesList.add(stringTokenizer.nextToken());
    	}
    }
    public boolean match(String courseNum) {
        return this.courseNum.equals(courseNum);
    }
    public String getCourseNum() {
        return this.courseNum;
    }
    public String getProfessor() {
        return this.professor;
    }
    public String getCourseName() {
		return this.courseName;
    }
    public String getDay() {
        return this.day;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }
    public ArrayList<String> getPreCourses() {
        return this.preCoursesList;
    }
    public String toString() {
        String stringReturn = this.courseNum + " " + this.professor + " " + this.courseName;
        for (int i = 0; i < this.preCoursesList.size(); i++) {
            stringReturn = stringReturn + " " + this.preCoursesList.get(i).toString();
        }
        return stringReturn;
    }
}
