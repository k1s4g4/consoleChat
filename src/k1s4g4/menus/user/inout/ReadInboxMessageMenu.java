package k1s4g4.menus.user.inout;

import k1s4g4.data.App;
import k1s4g4.data.messages.Message;
import k1s4g4.data.users.ViewEditDeleteUser;
import k1s4g4.menus.MenuTools;

public class ReadInboxMessageMenu {
	
	

	private static boolean running;
	private static String[] commands= {"[R] Reply","[D] Delete", "[B] Back"};

	public static void start(Message msg) {
		MenuTools.clear();
		running=true;
		App.dbManager().setRead(msg);
		msg.setReceiverHasRead(true);
		while(running) {
			MenuTools.clear();
			printMessage(msg);
			String command=printCommands(msg);
			runCommand(command,msg);
		}
	}
	
	public static void printMessage(Message msg) {
		
		MenuTools.print(5, "FROM: "+"\n", "green", "black");
		MenuTools.print(5, msg.getSender().getUsername()+"\n", "black", "green");
		System.out.println();
		MenuTools.print(5, "SBJ: "+"\n", "green", "black");
		MenuTools.print(5, msg.getSubject()+"\n", "black", "green");
		System.out.println();
		MenuTools.print(5, "MESSAGE: "+"\n", "green", "black");
		MenuTools.print(5, msg.getText()+"\n", "black", "green");
		System.out.println();
		
		
	}
	
	public static String printCommands(Message msg) {
		String line="";
		for(int i=0;i<commands.length;i++) {
			if(i==1) {
				if(msg.getReceiver() instanceof ViewEditDeleteUser) {
					line+=commands[i]+"  ";
				}
			}else {
				line+=commands[i]+"  ";
			}
		}
		MenuTools.print(5, line, "green", "black");
		System.out.print("\n");
		return System.console().readLine();
	}
	
	
	public static void runCommand(String command,Message msg) {
		switch(command.toUpperCase()) {
			case "R":
				ReplyMessageMenu.start(msg.getReceiver() ,msg.getSender() );
				break;
			case "D":
				
				if(msg.getReceiver() instanceof ViewEditDeleteUser) {
					MenuTools.print(5, "Are you sure you want to delete?(Y/N): ", "red", "black");
					System.out.println();
					String yn=System.console().readLine();
					if(yn.equalsIgnoreCase("y")) {
						((ViewEditDeleteUser)msg.getReceiver()).deleteMessage(msg);
					}else if(yn.equalsIgnoreCase("n")){
						
					}else {
						System.out.println("Invalid input.");
					}
				}
			case "B":
				stop();
				break;
			default:
		}
	}
	
	public static void stop() {
		MenuTools.clear();
		running=false;
	}
}
