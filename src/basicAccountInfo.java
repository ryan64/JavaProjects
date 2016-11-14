import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class basicAccountInfo {
	private String accountNumber; 
	private String password;
	
	//getters and setters to accordingly get, and set data. 
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber1) {
		this.accountNumber = accountNumber1;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	basicAccountInfo(){
		//to read in password and account number. when the object is created or called. 
		Scanner inputStream = null; 
		try {
				inputStream = new Scanner(new FileInputStream("Password.txt"));
				int lines = 0; 
	            String currentLine;
	            
	            //updates the int variable lines. 
	            while(inputStream.hasNextLine()) {
	                inputStream.nextLine(); 
	                lines++; 
	            }
	            
	            inputStream.close();
	            inputStream = new Scanner(new FileInputStream("Password.txt")); 
	            
	            for(int i = 0; i < lines; i++) {
	                currentLine = inputStream.nextLine(); 
	                String[] entireString = currentLine.split(" "); //using space as a delimiter. 
	                
	                this.accountNumber = entireString[0]; //set the variable to the appropriate data from the text file. 
	                this.password = entireString[1];  //set the variable to the appropriate data from the text file. 
	            }
	            inputStream.close();
			}
		
			catch (FileNotFoundException e){
				System.out.println("File not found, exiting.");
				System.exit(0);
			}
		}
}
