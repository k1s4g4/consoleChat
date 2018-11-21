package k1s4g4.menus.user.inout;

import k1s4g4.data.messages.Message;
import k1s4g4.data.users.ViewEditUser;
import k1s4g4.menus.MenuTools;

public class EditMessageMenu {
	
	private static boolean running;
	
	public static Message start(Message msg) {
		running=true;
		while(running) {
			MenuTools.clear();
			printSenderAndSubject(msg);
			MenuTools.print(5, " Write the message you want to send:\n", "green","black");
			System.out.println("");
			MenuTools.print(5,"","green","black");
			MenuTools.set("green", "black");
			String newMsg=System.console().readLine();
			MenuTools.reset();
			if(newMsg.length()<250 && newMsg.length()>=5) {
				msg.setText(newMsg);
				((ViewEditUser) msg.getSender()).editMessage(msg, newMsg);;
				break;
			}else {
				System.out.println("Message must be more than 4 characters and less than characters");
			}
		}
		return msg;
	}
	
	private static void printSenderAndSubject(Message msg) {
		MenuTools.print(5,"TO: "+msg.getReceiver().getUsername() ,"green", "black");
		System.out.println();
		System.out.println();
		MenuTools.print(5,"SUBJECT: " +msg.getSubject() ,"green", "black");
		System.out.println();
		System.out.println();
	}
}
