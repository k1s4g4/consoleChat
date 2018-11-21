package k1s4g4.data.messages;

import java.util.ArrayList;

public class Outbox {
	private ArrayList<Message> messages;
	public Outbox() {
		messages=new ArrayList<Message>();
	}
	public Outbox(ArrayList<Message> messages) {
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
	
	public String[][] getTable(){
		String[][] table=new String[messages.size()][5];
		for(int i=0;i<messages.size();i++) {
			table[i][0]=" "+(i+1)+".";
			table[i][1]=messages.get(i).getReceiver().getUsername()+" ";
			table[i][2]=messages.get(i).getSubject();
			table[i][3]=messages.get(i).getDate();
			table[i][4]=messages.get(i).getTime();
			for(int j=0;j<table[i].length;j++) {
				String colorDifSpaces="";
				for(int k=0;k<j;k++) {
					colorDifSpaces+=" ";
				}
				table[i][j]=colorDifSpaces+"@|green "+table[i][j]+" |@";
				
			}
		}
		return table;
	}
}
