package k1s4g4.data;


import java.sql.SQLException;
import java.util.ArrayList;

import k1s4g4.data.tools.DatabaseManager;
import k1s4g4.data.users.Admin;
import k1s4g4.data.users.ViewUser;

public class App {
	private static App singleton_app;
	private static ArrayList<ViewUser> users;
	private static ViewUser currentUser;
	private static Admin admin;
	private static DatabaseManager dbManager;
	
	private App() {
		admin=new Admin();
		users=new ArrayList<ViewUser>();
		dbManager=DatabaseManager.dbManager();
		readUsers();
	}
	public static App app() {
		if(singleton_app==null) {
			singleton_app=new App();
		}
		return singleton_app;
	}
	public ArrayList<ViewUser> users(){
		readUsers();
		return users;
	}
	public static Admin admin() {
		return admin;
	}
	public static DatabaseManager dbManager() {
		return dbManager;
	}
	public static ViewUser currentUser() {
		return currentUser;
	}
	public static void setCurrentUser(ViewUser user) {
		currentUser=user;
	}
	private static boolean readUsers() {
		boolean done=false;
		try {
			users=dbManager.getUsers();
			done=true;
		} catch (SQLException e) {
			done=false;
			e.printStackTrace();
		}
		return done;
		
	}
	public static void setUser(ViewUser user) {
		for(int i=0;i<users.size();i++ ) {
			if(users.get(i).getUsername().equals(user.getUsername())) {
				users.set(i, user);
				break;
			}
		}
	}
	
}
