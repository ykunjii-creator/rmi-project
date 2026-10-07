import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SystemLogger {
	
	public static void log(String userId, String commandType) {

	    String timestamp = LocalDateTime.now()
	            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

	    String logMessage =
	            userId + " | " + commandType + " | " + timestamp
	            + System.lineSeparator();

	    try (FileWriter writer = new FileWriter("SystemLog.txt", true)) {
	        writer.write(logMessage);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	

}
