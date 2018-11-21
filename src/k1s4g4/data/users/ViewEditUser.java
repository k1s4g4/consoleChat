package k1s4g4.data.users;

import k1s4g4.data.messages.Inbox;
import k1s4g4.data.messages.Message;
import k1s4g4.data.messages.Outbox;
import k1s4g4.data.tools.DatabaseManager;
import k1s4g4.data.tools.FileManager;

public class ViewEditUser extends ViewUser {
	public ViewEditUser() {
		super();
	}
	public ViewEditUser(int id,String username,String password,String role, boolean defPass) {
		super(id,username,password,role,defPass);
	}
	public ViewEditUser(int id,String username,String password,Inbox inbox,Outbox outbox,String role, boolean defPass) {
		super(id,username,password,inbox,outbox,role,defPass);
	}
	
	public void editMessage(Message msg,String newMsg) {
		DatabaseManager.dbManager().editMessage(msg,newMsg);
		FileManager.appendMessage(msg);
		FileManager.appendAction(msg.getSender().getUsername(), "edit");
	}
}
