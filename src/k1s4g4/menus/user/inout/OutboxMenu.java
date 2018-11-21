package k1s4g4.menus.user.inout;

import java.util.Collections;

import k1s4g4.data.App;
import k1s4g4.data.compare.CompareByDate;
import k1s4g4.data.compare.CompareByReceiver;
import k1s4g4.data.messages.Message;
import k1s4g4.menus.MenuTools;

public class OutboxMenu {
	private static boolean running;
	private static boolean sortRunning;
	private static String[] commands= {"[Message Number] Open","[S] Sort by","[B] Back"};
	private static String[] sortChoices= {"By Date","By Receiver Name","Back"};
	private static int lineLength;
	public static void start() {
		if(App.currentUser().getOutbox().getMessages().size()==0) {
			MenuTools.clear();
			noOutboxMsg();
		}else {
			running=true;
			while(running) {
				MenuTools.clear();
				printMessages();
				String command=printCommands();
				runCommand(command);
			}
		}
		
	}
	private static void printMessages() {/*
		for(int i=0;i<outbox.getMessages().size();i++) {
			System.out.println((i+1)+"\t"+outbox.getMessages().get(i).OutboxToString());
		}*/
		lineLength=MenuTools.printTable(Message.getOutboxHeaders(), App.currentUser().getOutbox().getTable());
	}
	private static String printCommands() {
		String line="";
		for(int i=0; i<commands.length;i++) {
			if(i!=commands.length-1) {
				line+=" "+commands[i]+"  ";
			}else {
				line+=" "+commands[i];
			}
		}
		int thisLineLength=line.length();
		for(int i=0;i<(lineLength-thisLineLength);i++) {
			line+=" ";
		}
		
		MenuTools.print(5, line, "green", "green");
		System.out.println("");
		MenuTools.print(5, line, "green", "black");
		System.out.println("");
		MenuTools.print(5, line, "green", "green");
		return System.console().readLine("\n");
	}
	
	private static void runCommand(String command) {
		if(command.matches("[\\d]+")) {
			int message_index=Integer.parseInt(command);
			if(message_index-1<App.currentUser().getOutbox().getMessages().size()) {
				ReadOutboxMessageMenu.start(App.currentUser().getOutbox().getMessages().get(message_index-1));
			}else {
				System.out.println("No message was found");
				MenuTools.delay(1000);
			}
		}else {
			switch(command.toUpperCase()) {
				case "S":
					sortRunning=true;
					while(sortRunning) {
						runSortCommand(printSortChoices());
					}
				break;
				case "B":
					stop();
					break;
				default:
			}
		}
	}
	
	public static String printSortChoices() {
		for(int i=0;i< sortChoices.length;i++) {
			MenuTools.print(5, (i+1)+"."+sortChoices[i]+"\n", "black", "green");
		}
		return System.console().readLine();
	}
	
	public static void runSortCommand(String command) {
		switch(command.toUpperCase()) {
			case "1":
				Collections.sort(App.currentUser().getOutbox().getMessages(), new CompareByDate());
				sortStop();
				break;
			case "2":
				Collections.sort(App.currentUser().getOutbox().getMessages(), new CompareByReceiver());
				sortStop();
				break;
			case "3":
				sortStop();
				break;
			default:
		}
	}
	
	public static void noOutboxMsg() {
		System.out.println("You have no outbox messages");
		MenuTools.delay(1000);
	}
	
	public static void sortStop() {
		sortRunning=false;
	}
	
	public static void stop() {
		running=false;
		MenuTools.clear();
	}	
}
