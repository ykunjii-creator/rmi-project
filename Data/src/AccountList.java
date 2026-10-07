import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class AccountList {
	private HashMap<String, String> accounts;
	
	public AccountList(String fileName) throws IOException {

        accounts = new HashMap<String, String>();
        
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;
        
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");

            String id = parts[0];
            String password = parts[1];

            accounts.put(id, password);
        }

        reader.close();

    }
	
	public boolean authenticate(String id, String password) {

	    String savedPassword = accounts.get(id);

	    if (savedPassword == null) {
	        return false;
	    }

	    return savedPassword.equals(password);
	}
	
	public static void main(String[] args) {

	    try {
	    		AccountList accountList = new AccountList("Accounts.txt");
	    		
	    		System.out.println(accountList.authenticate("20100123", "pass1234"));
	    		System.out.println(accountList.authenticate("20100123", "wrong"));
	    		System.out.println(accountList.authenticate("99999999", "pass1234"));
	    		
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}

}
