package k1s4g4.data.messages;

import k1s4g4.data.users.ViewUser;

public class Message {
	
	private int id;
	private String dateTime;
	private String text;
	private ViewUser sender;
	private ViewUser receiver;
	private String subject;
	private boolean receiverHasRead;
	
	public Message() {
		
	}
	public Message(String dateTime, String text, ViewUser sender, ViewUser receiver,String subject,int id, boolean receiverHasRead) {
		super();
		this.dateTime = dateTime;
		this.text = text;
		this.sender = sender;
		this.receiver = receiver;
		this.subject=subject;
		this.receiverHasRead=receiverHasRead;
		this.id=id;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ViewUser getSender() {
		return sender;
	}
	public void setSender(ViewUser sender) {
		this.sender = sender;
	}
	public ViewUser getReceiver() {
		return receiver;
	}
	public void setReceiver(ViewUser receiver) {
		this.receiver = receiver;
	}
	public boolean isReceiverHasRead() {
		return receiverHasRead;
	}
	public void setReceiverHasRead(boolean receiverHasRead) {
		this.receiverHasRead = receiverHasRead;
	}
	public String toString() {
		return "FROM: "+sender.getUsername()+"\tSBJ:"+subject+"      "+dateTime;
	}
	public String OutboxToString() {
		return "TO: "+receiver.getUsername()+ "\tSBJ:"+subject+"      "+dateTime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public static String[] getInboxHeaders() {
		String[] str={"NUM","FROM","SUBJECT","DATE","TIME"};
		return str;
	}
	
	public static String[] getOutboxHeaders() {
		String[] str={"NUM","TO","SUBJECT","DATE","TIME"};
		return str;
	}
	
	
	public String getTime() {
		return dateTime.split(" ")[1];
	}
	
	public String getDate() {
		return dateTime.split(" ")[0];
	}
	
	
	
	
	
	
	
	
	
	
}
