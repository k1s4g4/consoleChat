package k1s4g4.menus.user.inout;

import k1s4g4.data.App;
import k1s4g4.data.users.ViewUser;
import k1s4g4.menus.MenuTools;

public class NewMessageMenu {
	public static boolean chooseReceiverRunning;
	public static boolean writeSubjectRunning;
	public static boolean writeMessageRunning;
	
	private NewMessageMenu() {
		
	}
	
	public static void start() {
		chooseReceiverRunning=true;
		ViewUser receiver;
		while(chooseReceiverRunning){

			MenuTools.clear();
			if( (receiver=checkReceiverExist(getReceiverUsername()))!=null ) {
				writeSubjectRunning=true;
				while(writeSubjectRunning) {
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
										App.currentUser().sendMessage( receiver , message, subject);
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
				
			}else {
				wrongReceiverMsg();
			}
			
		}
	}
	
	private static String getReceiverUsername() {
		MenuTools.print(10,"New message to: ","black","green");
		MenuTools.set("green","black");
		String str=System.console().readLine();
		MenuTools.reset();
		return str;
	}
	
	private static ViewUser checkReceiverExist(String username) {
		ViewUser user=null;
		for(int i=0; i<App.app().users().size();i++) {
			if(App.app().users().get(i).getUsername().equalsIgnoreCase(username)) {
				user=App.app().users().get(i);
				break;
			}
		}
		return user;
	}
	
	private static String getSubject() {
		System.out.println("");
		MenuTools.print(10,"Subject:","black","green");
		MenuTools.set("green","black");
		String str=System.console().readLine();
		MenuTools.reset();
		return str;
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
	
	
	
	private static void wrongReceiverMsg() {
		System.out.println("");
		MenuTools.print(10, "No user found. Try again.", "red", "black");
		MenuTools.delay(2000);
		System.out.println("");
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
		chooseReceiverRunning=false;
		writeMessageRunning=false;
		MenuTools.clear();
	}
	
}
