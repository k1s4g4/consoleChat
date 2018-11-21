
package k1s4g4.menus.user;


import k1s4g4.data.App;
import k1s4g4.data.tools.DatabaseManager;
import k1s4g4.data.tools.FileManager;
import k1s4g4.menus.MenuTools;
import k1s4g4.menus.user.inout.InboxMenu;
import k1s4g4.menus.user.inout.NewMessageMenu;
import k1s4g4.menus.user.inout.OutboxMenu;

public class UserMenu {
	
	private static boolean menuRunning;
	private static String[] commands= {"New message","Inbox","Outbox","Refresh","Logout"};
	
	protected UserMenu() {
		
	}
	public static void start() {
		
		if(App.currentUser().isDefaultPassword()) {
			boolean passwordChanging=true;
			while(passwordChanging) {
				MenuTools.clear();
				MenuTools.print(5,"You must change your password for privacy.","red","black");
				System.out.println("");
				System.out.println("");
				MenuTools.print(5,"New password:    ","green","black");
				MenuTools.set("green", "black");
				String password = new String(System.console().readPassword());
				MenuTools.reset();
				System.out.println("");
				if(password.length()<3) {
					System.out.println();
					MenuTools.print(5, "Password must be more than 3 characters", "red", "black");
					continue;
				}
				MenuTools.print(5,"Confirm password:","green","black");
				MenuTools.set("green", "black");
				String conPass=new String(System.console().readPassword());
				MenuTools.reset();
				if(password.equals(conPass)) {
					passwordChanging=false;
					App.dbManager().editPassword(App.currentUser(),password);
				}else {
					System.out.println();
					MenuTools.print(5, "Try again. Confirmation Failed", "red", "black");
					MenuTools.delay(1000);
				}
			}
		}
		menuRunning=true;
		FileManager.appendAction(App.currentUser().getUsername(), "log-in");
		
		while(menuRunning) {
			MenuTools.clear();
			String command=printCommands();
			runCommand(command);
		}
		
	}
	
	private static String printCommands() {
		titleAndUsername();
		for(int i=0;i< commands.length;i++) {
			
			if(commands[i].equals("Inbox") && App.currentUser().getInbox().unread()!=0) {
				MenuTools.print(30," "+ (i+1)+". "+commands[i]+" ("+ App.currentUser().getInbox().unread()+" Unread)"+"\n", "black", "green");
			}else {
				MenuTools.print(30," "+ (i+1)+". "+commands[i] +"\n", "black" , "green");
			}
		}
		System.out.println("");
		MenuTools.print(30,"   "+ App.currentUser().getUsername().toUpperCase()+"              \n", "green", "green");
		MenuTools.print(30,"   "+ App.currentUser().getUsername().toUpperCase()+"              \n", "green", "green");
		MenuTools.print(30,"   "+ App.currentUser().getUsername().toUpperCase()+"              \n", "green", "green");
		return System.console().readLine();
	}
	
	private static void runCommand(String command) {
		switch(command) {
			case "1":
				NewMessageMenu.start();
				break;
			case "2":
				App.currentUser().setInbox(App.dbManager().getInbox(App.currentUser()));
				InboxMenu.start();
				break;
			case "3":
				App.currentUser().setOutbox(App.dbManager().getOutbox(App.currentUser()));
				OutboxMenu.start();
				break;
			case "4":
				App.currentUser().setInbox(DatabaseManager.dbManager().getInbox(App.currentUser()));
				App.currentUser().setOutbox(DatabaseManager.dbManager().getOutbox(App.currentUser()));
				break;
			case "5":
				stop();
				break;
			default:
				
		}
	}
	
	public static void titleAndUsername() {
		MenuTools.cursor(5, 0);
		MenuTools.print(30,"   "+ App.currentUser().getUsername().toUpperCase()+"              \n", "green", "green");
		MenuTools.print(30,"   "+ App.currentUser().getUsername().toUpperCase()+"              \n", "green", "black");
		MenuTools.print(30,"   "+ App.currentUser().getUsername().toUpperCase()+"              \n", "green", "green");
		System.out.println("");
	}
	
	public static void stop() {
		menuRunning=false;
		FileManager.appendAction(App.currentUser().getUsername(), "log-out");
		MenuTools.clear();
	}
}
