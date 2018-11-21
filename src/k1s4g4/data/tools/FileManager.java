package k1s4g4.data.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import k1s4g4.data.messages.Message;
import k1s4g4.security.EncryptionDecryptionAES;


public class FileManager {
	private static final String actionFilename="actionLog.txt";
	private static final String messageFilename="messageLog.txt";
	private static final String spaces="      ";
	
	public static void start() {
		if(!exists(actionFilename)) {
			initializeActionFile();
		}
		if(!exists(messageFilename)) {
			initializeMessageFile();
		}
	}
	private static boolean exists(String filename) {
		File f = new File(filename);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}else {
			return false;
		}
	}
	
	private static void initializeActionFile() {
		try {
			
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(actionFilename), true )); 
			String line="DATE     "+spaces+" TIME    "+spaces+"USERNAME  "+spaces+"ACTION\r\n";
			pw.append(line);
	    	pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		}
	}
	
	private static void initializeMessageFile() {
		try {
			
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(messageFilename), true )); 
			String line="DATE      "+spaces+"TIME    "+spaces+"SENDER    "+spaces+"RECEIVER  "+spaces+"SUBJECT             "+spaces+"MESSAGE\r\n";
			pw.append(line);
	    	pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		}
	}
	
	
	
	
	public static String time(){
		LocalTime localTime = LocalTime.now();
		String time=localTime+"";
		time=time.substring(0,time.length()-4);
		return time;
	}

	public static boolean appendAction(String username,String action){
		String pattern = "dd/MM/yyyy";
		Date date=new Date();
		DateFormat dateFormat = new SimpleDateFormat(pattern);
        String strDate = dateFormat.format(date);
        
        int nameLen=username.length();
        for(int i=0;i<10-nameLen;i++) {
        	username+=" ";
        }
    	
		try {
			
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(actionFilename), true )); 
			String line=strDate+spaces+time()+spaces+username+spaces+action+"\r\n";
			pw.append(line);
	    	pw.close();
	    	return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public static boolean appendMessage(Message msg){
		String pattern = "dd/MM/yyyy";
		Date date=new Date();
		DateFormat dateFormat = new SimpleDateFormat(pattern);
        String strDate = dateFormat.format(date);
        
        String sen=msg.getSender().getUsername().trim();
        int senLen=sen.length();
        for(int i=0;i< 10-senLen;i++) {
        	sen+=" ";
        }
        String rec=msg.getReceiver().getUsername().trim();
        int recLen=rec.length();
        for(int i=0;i< 10-recLen;i++) {
        	rec+=" ";
        }
    	String sbj=msg.getSubject().trim();
    	int sbjLen=sbj.length();
    	for(int i=0;i< 20-sbjLen;i++) {
        	sbj+=" ";
        }
        
        
        
		try {
			
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(messageFilename), true )); 
			String line=strDate+spaces+time()+spaces+sen+spaces+rec+spaces+sbj+spaces+EncryptionDecryptionAES.encryptMessage(msg.getText())+"\r\n";
			pw.append(line);
	    	pw.close();
	    	return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	
}
