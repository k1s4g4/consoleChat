package k1s4g4.menus.admin;


import k1s4g4.data.tools.FileManager;
import k1s4g4.menus.MenuTools;

public class AdminMenu {
	private static boolean running;
	
	public static void start() {
		MenuTools.clear();
		FileManager.appendAction("admin", "log-in");
		running=true;
		while(running) {
			MenuTools.clear();
			String command=printCommands();
			runCommand(command);
		}
		
	}
	
	private static String printCommands() {
		title();
		command();
		MenuTools.set("black", "green");
		String str=System.console().readLine();
		MenuTools.reset();
		return str;
	}
	
	
	
	private static void runCommand(String command) {
		switch(command) {
			case "1":
				UserManagementMenu.start();
				break;
			case "2":
				FileManager.appendAction("admin", "log-out");
				stop();
			default:
		}
	}
	
	private static void stop() {
		running=false;
	}
	
	private static void title() {
		MenuTools.cursor(3, 0);
		MenuTools.print(40, "                                   \n" , "green" , "black");
		MenuTools.print(40, "           Welcome Admin           \n" , "green" , "black");
		MenuTools.print(40, "                                   " , "green" , "black");
	}
	
	private static void command() {
		MenuTools.cursor(8, 0);
		MenuTools.print(46, "1. User management.\n" , "black" , "green");
		MenuTools.print(46, "2. Log out.\n" , "black" , "green");
		System.out.println("");
		MenuTools.print(46, "Enter number of command: " , "black" , "green");
	}
	
	
}
