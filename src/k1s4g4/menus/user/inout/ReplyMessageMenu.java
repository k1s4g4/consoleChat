package k1s4g4.menus.user.inout;

import k1s4g4.data.users.ViewUser;
import k1s4g4.menus.MenuTools;

public class ReplyMessageMenu {
	
	public static boolean writeSubjectRunning;
	
	public static boolean writeMessageRunning;
	
	public static void start(ViewUser sender,ViewUser receiver) {
		writeSubjectRunning=true;
		while(writeSubjectRunning) {
			MenuTools.clear();
			MenuTools.print(10, "Reply to: "+ receiver.getUsername() , "green" , "black" );
			System.out.println();
			String subject;
			if((subject=getSubject()).length()<=20) {
				if(subject.length()<3) {
					smallSubjectMessage();
				}else {
					writeMessageRunning=true;
					while(writeMessageRunning) {
						String message;
						if((message=getMessage()).length()<=250) {
							if(message.length()<5) {
								smallLengthMessage();
							}else {
								sender.sendMessage( receiver , message, subject);
								MenuTools.delay(1000);
								stop();
							}
						}else {
							wrongMessageMsg();
						}
					}
				}
			}else {
				wrongSubjectMessage();
			}
		}
	}
	
	private static String getMessage() {
		System.out.println("");
		MenuTools.print(10,"Write the message you want to send","black","green");
		System.out.println("");
		System.out.println("");
		MenuTools.print(10,"","green","black");
		MenuTools.set("green","black");
		String str=System.console().readLine();
		MenuTools.reset();
		return str;
	}
	
	private static String getSubject() {
		System.out.println("");
		MenuTools.print(10,"Subject:","black","green");
		MenuTools.set("green","black");
		String str=System.console().readLine();
		MenuTools.reset();
		return str;
	}
	
	
	private static void wrongSubjectMessage() {
		System.out.println("");
		MenuTools.print(10, "Subject must be less than 20 charactes", "red", "black");
		MenuTools.delay(2000);
		System.out.println("");
	}
	
	private static void wrongMessageMsg() {
		System.out.println("");
		MenuTools.print(10, "Message must be less or equal to 250 characters", "red", "black");
		MenuTools.delay(2000);
		System.out.println("");
	}
	
	private static void smallLengthMessage() {
		System.out.println("");
		MenuTools.print(10, "Message must be more than 4 characters", "red", "black");
		MenuTools.delay(2000);
		System.out.println("");
	}
	
	private static void smallSubjectMessage() {
		System.out.println("");
		MenuTools.print(10, "Subject must be more than 2 characters", "red", "black");
		MenuTools.delay(2000);
		System.out.println("");
	}
	
	private static void stop() {
		writeSubjectRunning=false;
		writeMessageRunning=false;
		MenuTools.clear();
	}
	
}
