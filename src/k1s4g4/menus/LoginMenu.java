package k1s4g4.menus;

import java.util.ArrayList;

import k1s4g4.data.App;
import k1s4g4.data.users.ViewUser;
import k1s4g4.menus.admin.AdminMenu;
import k1s4g4.menus.user.UserMenu;

public class LoginMenu {
	private static boolean running;
	
	private LoginMenu() {
		
	}
	
	public static void start() {
		
		running=true;
		while(running) {
			ViewUser user;
			MenuTools.clear();
			intro();
			if( ( user=existingUser( getUsername(), App.app().users() ) ) != null  ) {
				checkPasswordAndLogin( user, getPassword() );
			}else {
				wrongUsernameMsg();
				continue;
			}
		}
	}
	
	private static void checkPasswordAndLogin(ViewUser user,String password) {
		if(user.getUsername().equals("admin") && password.equals("admin")) {
			AdminMenu.start();
		}else {
			if(password.equals(user.getPassword())) {
				/*==========================================
				 * =========================================
				 * =========================================
				 * ==========================================*/
				//   GET INBOX AND OUTBOX HERE
				user.setInbox(App.dbManager().getInbox(user));
				user.setOutbox(App.dbManager().getOutbox(user));
				
				/*==========================================
				 * =========================================
				 * =========================================
				 * ==========================================*/
				App.setCurrentUser(user);
				UserMenu.start();
			}else {
				wrongPasswordMsg();
			}
		}
	}
	
	private static ViewUser existingUser(String username , ArrayList<ViewUser> users) {
		ViewUser user=null;
		if(username.equals("admin")) {
			user = new ViewUser();
			user.setUsername("admin");
			user.setPassword("admin");
		}else {
			for(int i=0; i<users.size();i++) {
				if(users.get(i).getUsername().equals(username)) {	
					user=users.get(i);
					break;
				}
			}
		}
		return user;
	}
	
	
	private static String getPassword() {
		password();
		MenuTools.set("black", "green");
		char[] pass=System.console().readPassword();
		MenuTools.reset();
		return new String(pass);
	}
	
	
	private static String getUsername() {
		username();
		MenuTools.set("black", "green");
		String username= System.console().readLine();
		MenuTools.reset();
		return username;
	}


	private static void wrongPasswordMsg() {

		MenuTools.cursor(12, 0);
		MenuTools.print(40, " Wrong password. Try again ", "red", "black");
		MenuTools.delay(3000);
	}
	private static void wrongUsernameMsg() {
		MenuTools.cursor(12, 0);
		MenuTools.print(37, " Username does not exist. Try again ", "red", "black");
		MenuTools.delay(3000);
	}

	public static void stop() {
		running=false;
	}
	
	private static void intro() {
		MenuTools.cursor(3,0);
		String bg="green", fg= "black";
		int space =25;
		MenuTools.print(space,"                                                              \n", bg, fg);
		MenuTools.print(space,"                   WELCOME TO ANCIENT CHAT                    \n", bg, fg);
		MenuTools.print(space,"                                                              ", bg, fg);
		MenuTools.delay(1000);
	}
	
	private static void username() {
		MenuTools.cursor(8,0);
		String bg="black", fg= "green";
		int space =45;
		MenuTools.print(space, "USERNAME: ", bg, fg);
	}
	
	private static void password() {
		MenuTools.cursor(10,0);
		String bg="black", fg= "green";
		int space =45;
		MenuTools.print(space, "PASSWORD: ", bg, fg);
	}
	
	
}
