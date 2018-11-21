package k1s4g4.menus.admin;


import k1s4g4.data.App;
import k1s4g4.data.tools.FileManager;
import k1s4g4.data.users.ViewUser;
import k1s4g4.menus.MenuTools;
import k1s4g4.security.EncryptionDecryptionAES;

public class ViewUserMenu {
	private static boolean running;
	private static boolean editRunning;
	private static String[] commands= {"[R] Edit Role"," [D] Delete","[B] Back "};
	
	public static void start(ViewUser user) {
		running=true;
		while(running) {
			MenuTools.clear();
			title();
			printUser(user);
			run(printCommands(),user);
		}
		
	}
	
	public static String printCommands() {
		
		String line="";
		for(int i=0; i<commands.length;i++) {
			if(i!=commands.length-1) {
				line+=" "+commands[i]+"  ";
			}else {
				line+=" "+commands[i];
			}
		}
		
		
		MenuTools.print(10, line, "green", "green");
		System.out.println("");
		MenuTools.print(10, line, "green", "black");
		System.out.println("");
		MenuTools.print(10, line, "green", "green");
		return System.console().readLine("\n");
	}
	
	public static void run(String command,ViewUser user) {
		switch(command.toUpperCase()) {
			case "R":
				editRunning=true;
				while(editRunning) {
					runEdit(printEditCommands(),user);
				}
				break;
			case "D":
				deleteUser(user);
			case "B":
				stop();
				break;
		}
	}
	
	public static String printEditCommands() {
		MenuTools.print(10," 1. View\n","black","green");
		MenuTools.print(10," 2. View-Edit\n","black","green");
		MenuTools.print(10," 3. View-Edit-Delete\n","black","green");
		MenuTools.print(10," B. Back\n","black","green");
		return System.console().readLine();
	}
	
	public static void runEdit(String command,ViewUser user) {
		FileManager.appendAction("admin", "edit-user");
		switch(command.toUpperCase()) {
			case "1":
				App.admin().editUserRole(user, "View", 0);
				user.setRole("View");
				stopEdit();
				break;
			case "2":
				App.admin().editUserRole(user, "View-Edit", 1);
				user.setRole("View-Edit");
				stopEdit();
				break;
			case "3":
				App.admin().editUserRole(user, "View-Edit-Delete", 2);
				user.setRole("View-Edit-Delete");
				stopEdit();
				break;
			case "B":
				stopEdit();
				break;
		}
	}
	
	private static void deleteUser(ViewUser user) {
		App.admin().deleteUser(user);
	}
	
	public static void printUser(ViewUser user) {
		MenuTools.cursor(5, 0);
		MenuTools.print(10," USERNAME: "+user.getUsername()+"\n","black","green");
		System.out.println("");
		MenuTools.print(10, " PASSWORD: "+EncryptionDecryptionAES.encryptPassword(user.getPassword())+"\n", "black", "green");
		System.out.println("");
		MenuTools.print(10, " ROLE:     "+user.getRole()+"\n", "black", "green");
	}
	
	public static void title() {
		String title="";
		
		for(int i=0;i<commands.length;i++) {
			title+=" "+commands[i]+" ";
		}
		MenuTools.cursor(2, 0);
		MenuTools.print(10, title+"\n", "green", "green");
		MenuTools.print(10, title+"\n", "green", "green");
		MenuTools.print(10, title+"\n", "green", "green");
		MenuTools.cursor(3, 0);
		MenuTools.print(10, "               USER MENU", "green", "black");
	}
	
	public static void stopEdit() {
		MenuTools.clear();
		editRunning=false;
	}
	
	public static void stop() {
		running=false;
	}
	
}
