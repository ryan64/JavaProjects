import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextPane;


//Ryan Gonzalez - 65% Contributed. 
//Bop Huynh - 35% Contributed. 
public class AutomatedTellerMachine {
	
	//variables to be accessed from anywhere in this Main file. 
	private JFrame frame;  
	private JPasswordField passwordField;
	private JTextField textField;
	private int counter = 4; 
	accountInfo accountInformationObj; //object to handle account information. 
	basicAccountInfo accountUserPassObj; //object to handle basic login information. 
	
	//Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutomatedTellerMachine window = new AutomatedTellerMachine();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the application.
	public AutomatedTellerMachine() { 
		accountUserPassObj = new basicAccountInfo(); //load username and password into object from file. 
		accountInformationObj = new accountInfo(); //load account information into object from file. 
		accountInformationObj.checkIfActive(); //check if the account is active or not. (if active, continue, else terminate). 
		initialize(); //if active, initialize login UI. 
	}

	//Initialize the contents of the main UI.(Handles the login) 
	private void initialize() {
		System.out.print("Account Status: Active\n"); //console check.
		frame = new JFrame("Bank Of Ryamerica\u2122"); //main frame.
		frame.setBounds(450, 200, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField(); //textField for account number.
		textField.setBounds(183, 78, 194, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		passwordField = new JPasswordField(); //passwordField for password.
		passwordField.setBounds(183, 135, 194, 26);
		frame.getContentPane().add(passwordField);
		
		JLabel Account = new JLabel("Account Number: "); //display label for user.
		Account.setBounds(40, 84, 130, 14);
		frame.getContentPane().add(Account);
		Account.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel Password = new JLabel("Password: "); //display label for user. 
		Password.setBounds(90, 141, 76, 14);
		frame.getContentPane().add(Password);
		Password.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JTextPane textPane = new JTextPane(); //display label for user. Only visible, when password is entered incorrectly. 
		textPane.setForeground(Color.RED);
		textPane.setBackground(new Color(240, 240, 240));
		textPane.setBounds(183, 217, 194, 34);
		frame.getContentPane().add(textPane);
		textPane.setText("Incorrect Username or Password.\n" + "Tries Before Account Disabled: " + counter);
		textPane.setVisible(false);
		
		JButton btnLogin = new JButton("Login"); //button for user to invoke action. 
		btnLogin.setBounds(234, 183, 89, 23);
		frame.getContentPane().add(btnLogin); 
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBackground(new Color(240, 240, 240));
		btnLogin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String userInputAccountNumber = textField.getText(); //grab what the user inputed from textField. 
				char[] userInputPassword = passwordField.getPassword(); //grab what the user inputed from passwordField. 
				String userInputPasswordConverted = String.valueOf(userInputPassword); //convert what was grabbed from passwordField (char[] to string). 
				
				//if what the user enters as the account number and password match what was read in from file, access the next UI. 
				if (userInputAccountNumber.equals(accountUserPassObj.getAccountNumber()) && userInputPasswordConverted.equals(accountUserPassObj.getPassword())){
					System.out.println("Account Status: Logged In"); //console check. 
					counter = 4; //counter is reset to 4, in case password = wrong on first try. 
					textPane.setVisible(false); //hide warning label. 
					frame.dispose(); //dispose login frame. 
					accountManagement(); //jump to next UI. 
				}
				
				//if what the user enters as the password is incorrect, this is executed. 
				else if (userInputAccountNumber.equals(accountUserPassObj.getAccountNumber()) && !(userInputPasswordConverted.equals(accountUserPassObj.getPassword()))) {
					counter--; //counter is decremented. 
					textPane.setText("Incorrect Username or Password.\n" + "Tries Before Account Disabled: " + counter); 
					textPane.setVisible(true); //warning label is displayed. 
					
					//if the counter reaches zero, account is set to inactive and file is updated to lock the account. 
					if (counter == 0){
						accountInformationObj.setAtmAccessStatus("Inactive");
						printToFile(); 
						System.exit(0);
					}
				}	
			}
		});
		
		JLabel lblSecuredBySymantec = new JLabel("Secured By Symantec"); //label. 
		lblSecuredBySymantec.setBounds(0, 247, 148, 14);
		frame.getContentPane().add(lblSecuredBySymantec);
		lblSecuredBySymantec.setFont(new Font("Tahoma", Font.PLAIN, 12));
	}

	//Next UI for different options presented to user. 
	private void accountManagement(){
		JFrame frame1 = new JFrame("Account Management"); //second frame. 
		frame1.setBounds(450, 200, 450, 300);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(null);
		
		JButton Deposit = new JButton("Deposit"); //button for user to invoke action.
		Deposit.setBounds(48, 32, 138, 35);
		frame1.getContentPane().add(Deposit); 
		Deposit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Deposit.setBackground(new Color(240, 240, 240));
		Deposit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame1.dispose(); //dispose this frame. 
				Deposit(); //run deposit, if button is pressed. 
			}
		});
		
		JButton checkBalance = new JButton("Check Balance"); //button for user to invoke action.
		checkBalance.setBounds(48, 113, 138, 35);
		frame1.getContentPane().add(checkBalance); 
		checkBalance.setFont(new Font("Tahoma", Font.PLAIN, 13));
		checkBalance.setBackground(new Color(240, 240, 240));
		checkBalance.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame1.dispose(); //dispose this frame. 
				checkBalance(); //run checkBalance, if button is pressed. 
			}
		});
		
		JButton changePassword = new JButton("Change Password"); //button for user to invoke action.
		changePassword.setBounds(48, 189, 138, 35);
		frame1.getContentPane().add(changePassword); 
		changePassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		changePassword.setBackground(new Color(240, 240, 240));
		changePassword.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame1.dispose(); //dispose this frame. 
				changePassword(); //run changePassword, if button is pressed.
			}
		});
		
		JButton Withdraw = new JButton("Withdraw"); //button for user to invoke action.
		Withdraw.setBounds(246, 32, 138, 35);
		frame1.getContentPane().add(Withdraw); 
		Withdraw.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Withdraw.setBackground(new Color(240, 240, 240));
		Withdraw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame1.dispose(); //dispose this frame. 
				Withdraw(); //run withdraw, if button is pressed.
			}
		});
		
		JButton makeATransfer = new JButton("Make A Transfer"); //button for user to invoke action.
		makeATransfer.setBounds(246, 113, 138, 35);
		frame1.getContentPane().add(makeATransfer); 
		makeATransfer.setFont(new Font("Tahoma", Font.PLAIN, 13));
		makeATransfer.setBackground(new Color(240, 240, 240));
		makeATransfer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame1.dispose(); //dispose this frame. 
				makeATransfer(); //run makeATransfer, if button is pressed.
			}
		});
		
		JButton Logout = new JButton("Logout"); //button for user to invoke action.
		Logout.setBounds(246, 189, 138, 35);
		frame1.getContentPane().add(Logout); 
		Logout.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Logout.setBackground(new Color(240, 240, 240));
		Logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Logout Invoked");
				frame1.dispose(); //dispose this frame. 
				textField.setText(""); //set textField to blank. 
				passwordField.setText(""); //set passwordField to blank. 
				frame.setVisible(true); //display main UI. 
			}
		});
		
		JButton Exit = new JButton("Exit"); //button for user to invoke action.
		Exit.setBounds(345, 240, 89, 23);
		frame1.getContentPane().add(Exit); 
		frame1.setVisible(true);
		Exit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Exit.setBackground(new Color(240, 240, 240));
		Exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame1.dispose(); //dispose this frame. 
				System.exit(0); //terminate program. 
			}
		});
		
	}
	
	public void checkBalance(){
		System.out.println("Checking Balance Invoked");
		JFrame frame2 = new JFrame("Checking Balance"); //frame for check balance. 
		frame2.setBounds(450, 200, 450, 300);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		frame2.setVisible(true);
		
		JLabel checkBalance = new JLabel(); //display label for user. 
		checkBalance.setBounds(83, 108, 122, 20);
		frame2.getContentPane().add(checkBalance);
		checkBalance.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkBalance.setText("Balance Available:  $");
		
		textField = new JTextField(); //textField to display balance. 
		textField.setBounds(205, 109, 159, 20);
		frame2.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textField.setText(accountInformationObj.getBalance());
		
		JButton Back = new JButton("Back"); //button for user to invoke action.
		Back.setBounds(345, 240, 89, 23);
		frame2.getContentPane().add(Back); 
		Back.setBackground(new Color(240, 240, 240));
		Back.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame2.setVisible(true);
		Back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame2.dispose(); //dispose this frame. 
				accountManagement(); //go back to second UI for options. 
			}
		});
	}

	public void Deposit(){
		System.out.println("Deposit Invoked");
		JFrame frame3 = new JFrame("Deposit Money"); //frame for check balance. 
		frame3.setBounds(450, 200, 450, 300);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.getContentPane().setLayout(null);
		frame3.setVisible(true);
		
		JLabel depositMoney = new JLabel(); //display label for user. 
		depositMoney.setBounds(75, 108, 130, 20);
		frame3.getContentPane().add(depositMoney);
		depositMoney.setFont(new Font("Tahoma", Font.PLAIN, 14));
		depositMoney.setText("Amount Depositing: "); 
		
		textField = new JTextField(); //textField for amount depositing. 
		textField.setBounds(210, 109, 159, 20);
		frame3.getContentPane().add(textField);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(10);
		
		JButton addBalance = new JButton("Add Balance"); //button for user to invoke action.
		addBalance.setBounds(230, 140, 120, 20);
		frame3.getContentPane().add(addBalance); 
		addBalance.setForeground(new Color(51, 153, 0));
		addBalance.setBackground(new Color(240, 240, 240));
		addBalance.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame3.setVisible(true);
		addBalance.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String grabNumber = textField.getText(); //grab what the user inputed from textField. 
				Double addingBalance = Double.parseDouble(grabNumber); //convert that input to double. 
				addingBalance = addingBalance + Double.parseDouble(accountInformationObj.getBalance()); //take balance and add what was inputed. 
				accountInformationObj.setBalance(addingBalance.toString()); //reset balance to new value.
				printToFile();	//update text file. 
			}
		});
		
		
		JButton Back = new JButton("Back"); //button for user to invoke action.
		Back.setBounds(345, 240, 89, 23);
		frame3.getContentPane().add(Back); 
		Back.setBackground(new Color(240, 240, 240));
		Back.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame3.setVisible(true);
		Back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame3.dispose(); //dispose this frame. 
				accountManagement();
			}
		});
	}
	
	public void Withdraw(){
		System.out.println("Withdraw Invoked");
		JFrame frame4 = new JFrame("Withdraw Money"); //frame for check balance. 
		frame4.setBounds(450, 200, 450, 300);
		frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame4.getContentPane().setLayout(null);
		frame4.setVisible(true);
		
		JLabel withdrawMoney = new JLabel(); //display label for user. 
		withdrawMoney.setBounds(65, 108, 140, 20); 
		frame4.getContentPane().add(withdrawMoney);
		withdrawMoney.setFont(new Font("Tahoma", Font.PLAIN, 14));
		withdrawMoney.setText("Amount Withdrawing: ");  
		
		textField = new JTextField(); //textField for amount withdrawing. 
		textField.setBounds(210, 109, 159, 20);
		frame4.getContentPane().add(textField);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(10);
		
		JLabel insufficient = new JLabel(); //display label for user. 
		insufficient.setBounds(235, 165, 130, 23); 
		frame4.getContentPane().add(insufficient);
		insufficient.setFont(new Font("Tahoma", Font.PLAIN, 13));
		insufficient.setText("Insufficient Funds."); 
		insufficient.setVisible(false);
		
		JButton subtractBalance = new JButton("Subtract Balance"); //button for user to invoke action.
		subtractBalance.setBounds(225, 140, 130, 23);
		frame4.getContentPane().add(subtractBalance); 
		subtractBalance.setFont(new Font("Tahoma", Font.PLAIN, 12));
		subtractBalance.setForeground(Color.RED);
		subtractBalance.setBackground(new Color(240, 240, 240));
		frame4.setVisible(true);
		subtractBalance.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String grabNumber = textField.getText(); //grab what the user inputed from textField. 
				Double subtractingBalance = Double.parseDouble(grabNumber); //convert that input to double. 
				
				//check to see if sufficient funds exist. 
				if (subtractingBalance>Double.parseDouble(accountInformationObj.getBalance())){
					insufficient.setVisible(true); 
				}
				
				else{
					insufficient.setVisible(false);
					subtractingBalance = Double.parseDouble(accountInformationObj.getBalance()) - subtractingBalance; //subtract from balance what was inputed.
					accountInformationObj.setBalance(subtractingBalance.toString()); //set new balance.
					printToFile(); //update file. 
				}
			}
		});
		
		
		JButton Back = new JButton("Back"); //button for user to invoke action.
		Back.setBounds(345, 240, 89, 23);
		frame4.getContentPane().add(Back); 
		Back.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Back.setBackground(new Color(240, 240, 240));
		frame4.setVisible(true);
		Back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame4.dispose(); 
				accountManagement();
			}
		});
	}
	
	public void changePassword(){
		System.out.println("Change Password Invoked");
		JFrame frame5 = new JFrame("Change Password"); //frame for check balance. 
		frame5.setBounds(450, 200, 450, 300);
		frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame5.getContentPane().setLayout(null);
		frame5.setVisible(true);
		
		JLabel changePass = new JLabel(); //display label for user. 
		changePass.setBounds(65, 109, 140, 20); 
		frame5.getContentPane().add(changePass);
		changePass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		changePass.setText("Enter New Password: ");  
		
		textField = new JTextField(); //textField for new password. 
		textField.setBounds(210, 109, 159, 20);
		frame5.getContentPane().add(textField);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textField.setColumns(10);
		
		JButton Confirm = new JButton("Confirm Change"); //button for user to invoke action.
		Confirm.setBounds(230, 140, 120, 20);
		frame5.getContentPane().add(Confirm); 
		Confirm.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Confirm.setBackground(new Color(240, 240, 240));
		frame5.setVisible(true);
		Confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				accountUserPassObj.setPassword(textField.getText()); //account password is updated with what is grabbed from textfield. 
				printToFile();	//update file. 
			}
		});
		
		JButton Back = new JButton("Back"); //button for user to invoke action.
		Back.setBounds(345, 240, 89, 23);
		frame5.getContentPane().add(Back); 
		Back.setBackground(new Color(240, 240, 240));
		Back.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame5.setVisible(true);
		Back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame5.dispose(); //dispose this frame. 
				accountManagement();
			}
		});
	}

	public void makeATransfer(){ 
		System.out.println("Make A Transfer Invoked");
		JFrame frame6 = new JFrame("Transfer Funds"); //frame for transfer funds. 
		frame6.setBounds(450, 200, 450, 300);
		frame6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame6.getContentPane().setLayout(null);
		frame6.setVisible(true);
		
		JLabel displayAccountNumber = new JLabel(); //display label for user. 
		displayAccountNumber.setBounds(50, 20, 140, 20); 
		frame6.getContentPane().add(displayAccountNumber);
		displayAccountNumber.setFont(new Font("Tahoma", Font.PLAIN, 13));
		displayAccountNumber.setText("My Account Number");  
		
		JTextField accountNumber = new JTextField(); //textField for displaying accountNumber. 
		accountNumber.setBounds(50, 45, 115, 20);
		frame6.getContentPane().add(accountNumber);
		accountNumber.setColumns(10);
		accountNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		accountNumber.setText(accountUserPassObj.getAccountNumber());
		
		JLabel displayFirstName = new JLabel(); //display label for user. 
		displayFirstName.setBounds(50, 75, 140, 20); 
		frame6.getContentPane().add(displayFirstName);
		displayFirstName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		displayFirstName.setText("First Name"); 
		
		JTextField firstName = new JTextField(); //textField for displaying first name. 
		firstName.setBounds(50, 100, 115, 20);
		frame6.getContentPane().add(firstName);
		firstName.setColumns(10);
		firstName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		firstName.setText(accountInformationObj.getFirstName());  
		
		JLabel displayLastName = new JLabel(); //display label for user. 
		displayLastName.setBounds(50, 130, 140, 20); 
		frame6.getContentPane().add(displayLastName);
		displayLastName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		displayLastName.setText("Last Name"); 
		
		JTextField lastName = new JTextField(); //textField for displaying last name. 
		lastName.setBounds(50, 155, 115, 20);
		frame6.getContentPane().add(lastName);
		lastName.setColumns(10);
		lastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lastName.setText(accountInformationObj.getLastName()); 
		
		JLabel displayAccountNumber2 = new JLabel(); //display label for user. 
		displayAccountNumber2.setBounds(230, 20, 140, 20); 
		frame6.getContentPane().add(displayAccountNumber2);
		displayAccountNumber2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		displayAccountNumber2.setText("Their Account Number");
		
		JTextField accountNumber2 = new JTextField(); //textField for inputing other account number. 
		accountNumber2.setBounds(230, 45, 130, 20);
		frame6.getContentPane().add(accountNumber2);
		accountNumber2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		accountNumber2.setColumns(10);
		
		JLabel displayFirstName2 = new JLabel(); //display label for user.
		displayFirstName2.setBounds(230, 75, 140, 20); 
		frame6.getContentPane().add(displayFirstName2);
		displayFirstName2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		displayFirstName2.setText("Their First Name"); 
		
		JTextField firstName2 = new JTextField(); //textField for inputing other first name. 
		firstName2.setBounds(230, 100, 130, 20);
		frame6.getContentPane().add(firstName2);
		firstName2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		firstName2.setColumns(10);
		
		JLabel displayLastName2 = new JLabel(); //display label for user.
		displayLastName2.setBounds(230, 130, 140, 20); 
		frame6.getContentPane().add(displayLastName2);
		displayLastName2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		displayLastName2.setText("Their Last Name"); 
		
		JTextField lastName2 = new JTextField(); //textField for inputing other last name. 
		lastName2.setBounds(230, 155, 130, 20);
		frame6.getContentPane().add(lastName2);
		lastName2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lastName2.setColumns(10);
		
		JLabel amountTransferring = new JLabel(); //display label for user.
		amountTransferring.setBounds(145, 185, 140, 20); 
		frame6.getContentPane().add(amountTransferring);
		amountTransferring.setFont(new Font("Tahoma", Font.PLAIN, 13));
		amountTransferring.setText("Amount To Transfer"); 
		
		JTextField amount = new JTextField(); //textField for inputing amount transferring. 
		amount.setBounds(145, 205, 112, 20);
		frame6.getContentPane().add(amount);
		amount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		amount.setColumns(10);
		
		JLabel insufficient = new JLabel(); //display label for user. 
		insufficient.setBounds(145, 221, 112, 20); 
		frame6.getContentPane().add(insufficient);
		insufficient.setFont(new Font("Tahoma", Font.PLAIN, 13));
		insufficient.setText("Insufficient Funds."); 
		insufficient.setVisible(false);
		
		JButton subtractBalance = new JButton("Send Money"); //button for user to invoke action.
		subtractBalance.setBounds(145, 240, 112, 20);
		frame6.getContentPane().add(subtractBalance); 
		subtractBalance.setBackground(new Color(240, 240, 240));
		subtractBalance.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame6.setVisible(true);
		subtractBalance.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String taccountnumber = accountNumber2.getText(); //set other persons account number. 
				String tlastname = lastName2.getText(); //set other persons last name. 
				String tfirstname = firstName2.getText(); //set other persons first name. 
				String tbalance = amount.getText(); //set amount transferring to other persons account. 
				
				Double subtractingBalance = Double.parseDouble(tbalance); //take what was grabbed from textField and parse to double.
				
				//check to see if sufficient funds exist. 
				if (subtractingBalance>Double.parseDouble(accountInformationObj.getBalance())){
					insufficient.setVisible(true); 
				}
				
				else{
						PrintWriter outputStream = null;
						try {
							outputStream = new PrintWriter(new FileOutputStream("TransferredAccount.txt")); //write to a new file, the following information. 
							outputStream.println(taccountnumber);
							outputStream.println(tlastname);
							outputStream.println(tfirstname);
							outputStream.print(tbalance);
						}
					
						catch (FileNotFoundException e2){
							System.out.println("File not found, exiting.");
							System.exit(0);
						}
						outputStream.close();
					
						insufficient.setVisible(false);
						subtractingBalance = Double.parseDouble(accountInformationObj.getBalance()) - subtractingBalance; //subtract from balance what was inputed.
						accountInformationObj.setBalance(subtractingBalance.toString()); //set new balance.
						printToFile(); //update file. 
				}
			}
		});

		JButton Back = new JButton("Back"); //button for user to invoke action.
		Back.setBounds(345, 240, 89, 23);
		frame6.getContentPane().add(Back); 
		Back.setBackground(new Color(240, 240, 240));
		Back.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame6.setVisible(true);
		Back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame6.dispose(); //dispose this frame. 
				accountManagement();
			}
		});
	}	

	//if this function is called, it will print to files, to update them accordingly. 
	public void printToFile(){ 
		PrintWriter outputStream = null;
		try {
            outputStream = new PrintWriter(new FileOutputStream("AccountInformation.txt")); //update this file, with account number, first, last, balance and atm access status.
            outputStream.println(accountInformationObj.getAccountNumber());
            outputStream.println(accountInformationObj.getLastName());
            outputStream.println(accountInformationObj.getFirstName());
            outputStream.println(accountInformationObj.getBalance());
            outputStream.print(accountInformationObj.getAtmAccessStatus()); 
		}
            catch (FileNotFoundException e1) {
			System.out.println("File not found, exiting.");
			System.exit(0); 
		}
		outputStream.close();
		
		try {
			outputStream = new PrintWriter(new FileOutputStream("Password.txt")); //update this file with accountnumber and password. 
			outputStream.print(accountUserPassObj.getAccountNumber()+" ");
			outputStream.print(accountUserPassObj.getPassword());
		}
		
		catch (FileNotFoundException e2){
			System.out.println("File not found, exiting.");
			System.exit(0);
		}
		outputStream.close();
	}
}