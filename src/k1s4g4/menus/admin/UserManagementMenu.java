package k1s4g4.menus.admin;


import java.util.ArrayList;

import k1s4g4.data.App;
import k1s4g4.data.users.ViewUser;
import k1s4g4.menus.MenuTools;
import k1s4g4.security.EncryptionDecryptionAES;

public class UserManagementMenu{
	static final String username=" USERNAME";
	static final String password="PASSWORD";
	static final String role="ROLE";
	static final String number=" NUM";
	private static boolean running;
	private static boolean createRunning;
	private static int lineLength;
	private static String[] commands= {"[User Num] Choose User","[N] Create User","[B] Back"};
	
	
	public static void start() {
		running=true;
		while(running) {
			MenuTools.clear();
			printUsers(App.app().users());
			String command=printCommands();
			runCommand(command);
		}
	}
	
	private static String printCommands() {
		String line="";
		for(int i=0; i<commands.length;i++) {
			if(i!=commands.length-1) {
				line+=" "+commands[i]+"  ";
			}else {
				line+=" "+commands[i];
			}
		}
		int thisLineLength=line.length();
		for(int i=0;i<(lineLength-thisLineLength);i++) {
			line+=" ";
		}
		
		MenuTools.print(3, line, "green", "green");
		System.out.println("");
		MenuTools.print(3, line, "green", "black");
		System.out.println("");
		MenuTools.print(3, line, "green", "green");
		return System.console().readLine("\n");
	}
	
	private static void runCommand(String command) {
		if(command.matches("[\\d]+") && command.length()<=App.app().users().size()) {
			int user_index=Integer.parseInt(command);
			if(user_index-1<=App.app().users().size()) {
				ViewUserMenu.start(App.app().users().get(user_index-1));
			}else {
				System.out.println("No user found");
				MenuTools.delay(1000);
			}
		}else {
			switch(command.toUpperCase()) {
				case "N":
					MenuTools.clear();
					createUser();
					break;
				case "B":
					MenuTools.clear();
					stop();
					break;
				default:
					MenuTools.clear();
			}
		}
		
	}
	
	private static void createUser() {
		createRunning=true;
		while(createRunning) {
			newUserTitle();
			System.out.println("");
			MenuTools.print(10, " name of new user: ", "black", "green");
			MenuTools.set("black", "green");
			String name=System.console().readLine();
			MenuTools.reset();
			if(name.length()<3 || name.length()>=10) {
				System.out.println("");
				MenuTools.print(5, "Username must be at least 3 and less than 10 characters.", "red", "black");
				MenuTools.delay(1500);
				MenuTools.clear();
				continue;
			}
			
			if(exists(name)) {
				System.out.println("");
				MenuTools.print(5, "Username already exists. pick another name", "red", "black");
				
			}else {
				System.out.println("");
				MenuTools.print(10, " password of new user:", "black", "green");
				MenuTools.set("black", "green");
				String password=new String(System.console().readPassword());
				MenuTools.reset();
				if(password.length()<3 || password.length()>=20) {
					System.out.println("");
					MenuTools.print(5, "Password must be at least 3 and less than 20 characters.", "red", "black");
					MenuTools.delay(1500);
					MenuTools.clear();
					continue;
				}
				System.out.println("");
				MenuTools.print(10, " Confirm password:", "black", "green");
				MenuTools.set("black", "green");
				String passwordConfirm=new String(System.console().readPassword());
				MenuTools.reset();
				if(password.equals(passwordConfirm)) {
					ViewUser user=new ViewUser(0,name,password,"View",true);
					App.admin().addUser(user);
					createRunning=false;
					
				}else {
					System.out.println("");
					MenuTools.print(10, "password did not match. try again", "red", "black");
					MenuTools.delay(1500);
					MenuTools.clear();
				}
			}
		}
		
		
	}
	
	private static void newUserTitle() {
		MenuTools.print(10, "                                     \n", "green", "black");
		MenuTools.print(10, "            CREATE USER              \n", "green", "black");
		MenuTools.print(10, "                                     \n", "green", "black");
	}
	
	
	private static boolean exists(String name) {
		boolean exists=false;
		for(ViewUser user:App.app().users()) {
			if(user.getUsername().equals(name)) {
				exists=true;
				break;
			}
		}
		return exists;
	}
	
	public static void printUsers(ArrayList<ViewUser> users) {
		
		
		
		
		String[] numbers=new String[users.size()];
		String[] passwords=new String[users.size()];
		String[] usernames=new String[users.size()];
		String[] roles=new String[users.size()];
		for(int i=0; i<users.size(); i++) {
			numbers[i]=" "+(i+1)+"";
			usernames[i]=" "+users.get(i).getUsername();
			passwords[i]=EncryptionDecryptionAES.encryptPassword(users.get(i).getPassword()).trim();
			roles[i]=users.get(i).getRole();
			
		}
		
		int maxNumberLength=number.length();
		int maxNameLength=username.length();
		int maxPasswordLength=password.length();
		int maxRoleLength=role.length();
		
		for(int i=0; i<users.size(); i++) {
			if(numbers[i].length()>maxNumberLength) {
				maxNumberLength=numbers[i].length();
			}
			if(users.get(i).getUsername().length()>maxNameLength) {
				maxNameLength=usernames[i].length();
			}
			if(passwords[i].length()>maxPasswordLength) {
				maxPasswordLength=passwords[i].length();
			}
			if(users.get(i).getRole().length()>maxRoleLength) {
				maxRoleLength=roles[i].length();
			}
		}
		
		String numberSpaces="";
		for(int i=0;i<maxNumberLength-number.length();i++) {
			numberSpaces+=" ";
		}
		String usernameSpaces="";
		for(int i=0;i<maxNameLength-username.length();i++) {
			usernameSpaces+=" ";
		}
		String passwordSpaces="";
		for(int i=0;i<maxPasswordLength-password.length();i++) {
			passwordSpaces+=" ";
		}
		String roleSpaces="";
		for(int i=0;i<maxRoleLength-role.length();i++) {
			roleSpaces+=" ";
		}
		
		String headLine=number+numberSpaces+"\t"+username+usernameSpaces+"\t"+password+passwordSpaces+"\t"+role+roleSpaces;
		lineLength=headLine.length()+3*4+1;
		printTableHead(headLine);
		
		System.console().writer().println();
		for(int i=0; i<users.size(); i++) {
			numberSpaces="";
			usernameSpaces="";
			passwordSpaces="";
			roleSpaces="";
			for(int j=0;j<maxNumberLength-numbers[i].length();j++) {
				numberSpaces+=" ";
			}
			for(int j=0;j<maxNameLength-usernames[i].length();j++) {
				usernameSpaces+=" ";
			}
			for(int j=0;j<maxPasswordLength-passwords[i].length();j++) {
				passwordSpaces+=" ";
			}
			for(int j=0;j<maxRoleLength-roles[i].length();j++) {
				roleSpaces+=" ";
			}
			String line=numbers[i]+numberSpaces+"\t"+usernames[i]+usernameSpaces+"\t"+passwords[i]+passwordSpaces+"\t"+roles[i]+roleSpaces+"\n";
			MenuTools.print(3, line, "black", "green");
		}
		
	}
	
	
	private static void printTableHead(String head) {
		MenuTools.print(3, head, "green", "green");
		System.out.println("");
		MenuTools.print(3, head, "green", "black");
		System.out.println("");
		MenuTools.print(3, head, "green", "green");
	}
	
	
	
	public static void stop() {
		running=false;
	}
	
}
