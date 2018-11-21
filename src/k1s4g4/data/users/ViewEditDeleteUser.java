package k1s4g4.data.users;

import k1s4g4.data.messages.Inbox;
import k1s4g4.data.messages.Message;
import k1s4g4.data.messages.Outbox;
import k1s4g4.data.tools.DatabaseManager;
import k1s4g4.data.tools.FileManager;

public class ViewEditDeleteUser extends ViewEditUser {
	public ViewEditDeleteUser() {
		super();
	}
	public ViewEditDeleteUser(int id,String username,String password,String role, boolean defPass) {
		super(id,username,password,role,defPass);
	}
	public ViewEditDeleteUser(int id,String username,String password,Inbox inbox,Outbox outbox,String role, boolean defPass) {
		super(id,username,password,inbox,outbox,role,defPass);
	}
	
	public void deleteMessage(Message msg) {

		
		if(msg.getSender().getOutbox()==null) {
			msg.getReceiver().getInbox().deleteMessage(msg);
			DatabaseManager.dbManager().setDeletedMessage(msg,msg.getReceiver());
			FileManager.appendAction(msg.getReceiver().getUsername(), "delete");
		}else {
			msg.getSender().getOutbox().deleteMessage(msg);
			DatabaseManager.dbManager().setDeletedMessage(msg,msg.getSender());
			FileManager.appendAction(msg.getSender().getUsername(), "delete");
		}
	}
}
