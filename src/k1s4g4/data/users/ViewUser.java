package k1s4g4.data.users;

import k1s4g4.data.messages.Inbox;
import k1s4g4.data.messages.Message;
import k1s4g4.data.messages.Outbox;
import k1s4g4.data.tools.DatabaseManager;
import k1s4g4.data.tools.FileManager;

public class ViewUser {
	private int id;
	private String username;
	private String password;
	private Inbox inbox;
	private Outbox outbox;
	private boolean defaultPassword;
	private String role;
	
	public ViewUser() {
		
	}
	public ViewUser(int id,String username,String password,String role,boolean defaultPassword) {
		this.id=id;
		this.username=username;
		this.password=password;
		this.role=role;
		this.defaultPassword=defaultPassword;
	}
	public ViewUser(int id,String username,String password,Inbox inbox,Outbox outbox,String role,boolean defaultPassword) {
		this.id=id;
		this.username=username;
		this.password=password;
		this.inbox=inbox;
		this.outbox=outbox;
		this.role=role;
		this.defaultPassword=defaultPassword;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Inbox getInbox() {
		
		return inbox;
	}
	public void setInbox(Inbox inbox) {
		this.inbox = inbox;
	}
	public Outbox getOutbox() {
		return outbox;
	}
	public void setOutbox(Outbox outbox) {
		this.outbox = outbox;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isDefaultPassword() {
		return defaultPassword;
	}
	public void setDefaultPassword(boolean defaultPassword) {
		this.defaultPassword = defaultPassword;
	}
	
	public void sendMessage(ViewUser receiver,String text,String subject) {
		DatabaseManager.dbManager().sendMessage(this, receiver, text,subject);
		FileManager.appendAction(this.username, "send-message");
		FileManager.appendMessage(new Message("",text,this,receiver,subject,0,false));
	}
	
}
