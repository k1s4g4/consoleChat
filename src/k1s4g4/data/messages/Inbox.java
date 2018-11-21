package k1s4g4.data.messages;

import java.util.ArrayList;

public class Inbox {
	private ArrayList<Message> messages;
	public Inbox() {
		messages=new ArrayList<Message>();
	}
	public Inbox(ArrayList<Message> messages) {
		super();
		this.messages = messages;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	public void deleteMessage(Message msg) {
		messages.remove(msg);
	}
	
	public int unread() {
		int unread=0;
		for(int i=0;i<messages.size();i++) {
			if(!messages.get(i).isReceiverHasRead()) {
				unread++;
			}
		}
		return unread;
	}
	
	public String[][] getTable(){
		String[][] table=new String[messages.size()][5];
		for(int i=0; i < messages.size(); i++) {
			table[i][0]=" "+(i+1)+".";
			table[i][1]=messages.get(i).getSender().getUsername();
			table[i][2]=messages.get(i).getSubject();
			table[i][3]=messages.get(i).getDate();
			table[i][4]=messages.get(i).getTime();
			if(!messages.get(i).isReceiverHasRead()) {
				for(int j=0;j<table[i].length;j++) {
					table[i][j]="@|cyan "+table[i][j]+" |@";
					
				}
			}else {
				for(int j=0;j<table[i].length;j++) {
					String colorDifSpaces="";
					for(int k=0;k<j;k++) {
						colorDifSpaces+=" ";
					}
					table[i][j]=colorDifSpaces+"@|green "+table[i][j]+" |@";
					
				}
			}
		}
		return table;
	}
}
