package k1s4g4.menus.user.inout;


import k1s4g4.data.messages.Message;
import k1s4g4.data.users.ViewEditDeleteUser;
import k1s4g4.data.users.ViewEditUser;
import k1s4g4.menus.MenuTools;

public class ReadOutboxMessageMenu {
	private static boolean running;
	private static String[] commands= {"[E] Edit","[D] Delete","[B] Back"};
	
	public static void start(Message msg) {
		MenuTools.clear();
		running=true;
		while(running) {
			MenuTools.clear();
			printMessage(msg);
			String command=printCommands(msg);
			runCommand(command,msg);
		}
	}
	
	public static void printMessage(Message msg) {
		MenuTools.print(5, "TO: "+"\n", "green", "black");
		MenuTools.print(5, msg.getReceiver().getUsername()+"\n", "black", "green");
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
			if(i==0) {
				if(msg.getSender() instanceof ViewEditDeleteUser || msg.getSender() instanceof ViewEditUser) {
					line+=commands[i]+"  ";
				}
			}else if(i==1){
				if(msg.getSender() instanceof ViewEditDeleteUser) {
					line+=commands[i]+"  ";
				}
			}else{
				line+=commands[i]+"  ";
			}
		}
		MenuTools.print(5, line, "green", "black");
		System.out.print("\n");
		return System.console().readLine();
	}
	public static void runCommand(String command,Message msg) {
		switch(command.toUpperCase()) {
			case "D":
				if(msg.getSender() instanceof ViewEditDeleteUser) {
					MenuTools.print(5, "Are you sure you want to delete?(Y/N): ", "red", "black");
					System.out.println();
					String yn=System.console().readLine();
					if(yn.equalsIgnoreCase("y")) {
						((ViewEditDeleteUser)msg.getSender()).deleteMessage(msg);
					}else if(yn.equalsIgnoreCase("n")){
						
					}else {
						System.out.println("Invalid input.");
					}
				}
				running=false;
				break;
			case "E":
				if(msg.getSender() instanceof ViewEditDeleteUser || msg.getSender() instanceof ViewEditUser) {
					msg=EditMessageMenu.start(msg);
				}
				running=false;
				break;
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
