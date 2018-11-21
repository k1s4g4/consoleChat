package k1s4g4.data.users;

import java.sql.SQLException;

import k1s4g4.data.App;
import k1s4g4.data.tools.DatabaseManager;
import k1s4g4.data.tools.FileManager;

public class Admin{
	public Admin() {
	}
	
	
	public void addUser(ViewUser user) {

		App.app().users().add(user);
		try {
			DatabaseManager.dbManager().addUser(user.getUsername(), user.getPassword(),0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		FileManager.appendAction("admin", "create");
	}
	public void editUserRole(ViewUser user,String role,int roleInt) {
		DatabaseManager.dbManager().setRoleToUser(roleInt,user);
		user=new ViewUser(user.getId(),user.getUsername(),user.getPassword(),role,user.isDefaultPassword());
		App.setUser(user);
	}
	public void deleteUser(ViewUser user) {
		DatabaseManager.dbManager().setDeletedUser(user.getUsername());
		FileManager.appendAction("admin", "delete-user");
		App.app().users().remove(user);
	}
}
