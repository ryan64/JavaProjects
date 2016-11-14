import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class accountInfo{
	private String accountNumber; 
	private String lastName;
	private String firstName;
	private String balance;
	private String atmAccessStatus; 
	
	//getters and setters to accordingly get, and set data. 
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAtmAccessStatus() {
		return atmAccessStatus;
	}
	public void setAtmAccessStatus(String atmAccessStatus) {
		this.atmAccessStatus = atmAccessStatus;
	}

	accountInfo() {
		//to read in password and account number. when the object is created or called. 
		Scanner inputStream = null; 
        try {
             inputStream = new Scanner(new FileInputStream("AccountInformation.txt"));
             int lines = 0; 
             
             //updates the int variable lines. 
             while(inputStream.hasNextLine()) {
                inputStream.nextLine(); 
                lines++; 
             }
             
             String[] currentLine = new String [lines]; //array of size lines to hold data. 
             inputStream.close();
             
             inputStream = new Scanner(new FileInputStream("AccountInformation.txt")); 
             
             for(int i = 0; i < lines; i++) {
                currentLine[i] = inputStream.nextLine(); //each index of the array is set to whatever is stored in the text file. 		
             }
             inputStream.close(); 
             this.accountNumber = currentLine[0]; //set account number to what is in the 1st index of the array. 
             this.lastName = currentLine[1]; //set account number to what is in the 2nd index of the array. 
             this.firstName = currentLine[2]; //set account number to what is in the 3rd index of the array. 
             this.balance = currentLine[3]; //set account number to what is in the 4th index of the array. 
             this.atmAccessStatus = currentLine[4];//set account number to what is in the 5th index of the array. 
        }
            catch (FileNotFoundException e) {
			System.out.println("File not found, exiting.");
			System.exit(0);
		}
	}
	//function call to check if the account has 'Inactive' in the text file, if it does, terminate program. 
	public void checkIfActive() {  
		if (atmAccessStatus.equals("Inactive")){ 
			System.out.println("Account Status: Inactive"); 
			System.exit(0);
		}
	}
}
