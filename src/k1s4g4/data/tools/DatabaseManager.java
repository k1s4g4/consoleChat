package k1s4g4.data.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import k1s4g4.data.App;
import k1s4g4.data.messages.Inbox;
import k1s4g4.data.messages.Message;
import k1s4g4.data.messages.Outbox;
import k1s4g4.data.users.ViewUser;
import k1s4g4.data.users.ViewEditDeleteUser;
import k1s4g4.data.users.ViewEditUser;
import k1s4g4.menus.MenuTools;
import k1s4g4.security.EncryptionDecryptionAES;



public class DatabaseManager {
	private static DatabaseManager dbManager;
	private static Connection conn;
	
	private DatabaseManager() {
		conn=conn();
	}
	
	private static Connection conn() {
		if(conn==null) {
			try {
				conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/individual?autoReconnect=true&useSSL=false","root","1234");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	public static DatabaseManager dbManager() {
		if(dbManager==null) {
			dbManager=new DatabaseManager();
		}
		return dbManager;
	}
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ViewUser> getUsers() throws SQLException {
		ArrayList<ViewUser> users=new ArrayList<ViewUser>();
		
		String query= "select * from user where deleted=false";
		Statement stmnt=conn.createStatement();
		ResultSet rs=stmnt.executeQuery(query);
		
		while(rs.next()) {
			int id=rs.getInt("user_id");
			String name=rs.getString("username");
			String pass=rs.getString("pass");
			boolean defPassword=rs.getBoolean("default_password");
			pass=EncryptionDecryptionAES.decryptPassword(pass);
			int role=rs.getInt("role");
			String roleString="";
			ViewUser user=null;
			switch(role) {
				case 0:
					roleString="View";
					user=new ViewUser(id,name,pass,roleString,defPassword);
					break;
				case 1:
					roleString="View-Edit";
					user=new ViewEditUser(id,name,pass,roleString,defPassword);
					break;
				case 2:
					roleString="View-Edit-Delete";
					user=new ViewEditDeleteUser(id,name,pass,roleString,defPassword);
					break;
				default:
			}
			users.add( user );
		}
		return users;
	}
	
	public void setDeletedUser(String username)  {
		
		
				
		
		try {
			String updateTableSQL= "UPDATE user SET  deleted=true  WHERE user_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(updateTableSQL);
			preparedStatement.setInt(1, getUserID(username));
			preparedStatement .executeUpdate();
			
			updateTableSQL= "update user_message set sender_deleted=true, receiver_deleted=true  WHERE sender_id = ? or receiver_id=?";
			preparedStatement = conn.prepareStatement(updateTableSQL);
			preparedStatement.setInt(1, getUserID(username));
			preparedStatement.setInt(2, getUserID(username));
			preparedStatement .executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addUser(String username,String pass,int role) throws SQLException {
		String sql = "INSERT INTO user VALUES(null,?,?,?,true,false)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, username);
        pstmt.setString(2, EncryptionDecryptionAES.encryptPassword(pass));
        pstmt.setInt(3, role);
		pstmt.executeUpdate();
	}
	
	private int getUserID(String username) throws SQLException {
		String query= "select user_id from user where username=?";
		PreparedStatement stmnt=conn.prepareStatement(query);
		stmnt.setString(1, username);
		ResultSet rs=stmnt.executeQuery();
		int user_id=-1;
		while(rs.next()) {
			user_id=rs.getInt("user_id");
		}
		return user_id;
	}
	
	
	public void editPassword(ViewUser user, String password) {
		try {
			String updateTableSQL; 
			
				
			updateTableSQL= "UPDATE user SET  pass= ?,default_password=false WHERE user_id = ?";
				
			
			PreparedStatement preparedStatement = conn.prepareStatement(updateTableSQL);
			preparedStatement.setString(1, EncryptionDecryptionAES.encryptPassword(password));
			preparedStatement.setInt(2, user.getId());
			preparedStatement .executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			MenuTools.delay(100000);
		}
	}
	
	
	public Inbox getInbox(ViewUser user) {
		Inbox inbox=new Inbox();
		
		String query= "SELECT * FROM user_message where user_message.receiver_id=? and receiver_deleted=false order by date_time desc; ";
		
		ResultSet rs;
		try {
			PreparedStatement stmnt=conn.prepareStatement(query);
			stmnt.setInt(1, user.getId());
			rs = stmnt.executeQuery();
			while(rs.next()) {
				Timestamp timestamp = rs.getTimestamp("date_time");
				int message_id=rs.getInt("message_id");
				String text=rs.getString("text");
				text=EncryptionDecryptionAES.decryptMessage(text);
				String subject=rs.getString("subject");
				int sender_id=rs.getInt("sender_id");
				boolean receiver_has_read=rs.getBoolean("receiver_has_read");
				
				
				ViewUser sender=null;
				for(ViewUser usr:App.app().users()) {
					if(usr.getId()==sender_id) {
						
						sender=usr;
						break;
					}
				}
				
				
				inbox.getMessages().add(new Message(timestamp.toString(),text,sender,user,subject, message_id,receiver_has_read));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return inbox;
	}
	
	
	public Outbox getOutbox(ViewUser user) {
		Outbox outbox=new Outbox();
		
		String query= "SELECT * FROM user_message where user_message.sender_id=? and sender_deleted=false order by date_time desc; ";
		
		ResultSet rs;
		try {
			PreparedStatement stmnt=conn.prepareStatement(query);
			stmnt.setInt(1, user.getId());
			rs = stmnt.executeQuery();
			while(rs.next()) {
				Timestamp timestamp = rs.getTimestamp("date_time");
				int message_id=rs.getInt("message_id");
				String text=rs.getString("text");
				text=EncryptionDecryptionAES.decryptMessage(text);
				String subject=rs.getString("subject");
				int receiver_id=rs.getInt("receiver_id");
				boolean receiver_has_read=rs.getBoolean("receiver_has_read");
				
				ViewUser receiver=null;
				for(ViewUser usr:App.app().users()) {
					if(usr.getId()==receiver_id) {
						
						receiver=usr;
						break;
					}
				}
				
				
				outbox.getMessages().add(new Message(timestamp.toString(),text,user,receiver,subject,message_id,receiver_has_read));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return outbox;
	}
	
	public void sendMessage(ViewUser sender, ViewUser receiver, String text,String subject) {
		String sql = "INSERT INTO user_message VALUES(null,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, EncryptionDecryptionAES.encryptMessage(text));
			pstmt.setString(2, subject);
			pstmt.setTimestamp(3, getCurrentTimeStamp());
	        pstmt.setInt(4,sender.getId());
	        pstmt.setInt(5, receiver.getId());
	        pstmt.setBoolean(6,false);
	        pstmt.setBoolean(7,false);
	        pstmt.setBoolean(8,false);
	        
	        
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public void setDeletedMessage(Message msg,ViewUser user) {
		try {
			String updateTableSQL=""; 
			if(msg.getReceiver().getId()==user.getId()) {
				
				updateTableSQL= "UPDATE user_message SET  receiver_deleted= true WHERE message_id = ?";
				
			}else if(msg.getSender().getId()==user.getId()){
				
				updateTableSQL= "UPDATE user_message SET  sender_deleted=true  WHERE message_id = ?";
				
			}
			PreparedStatement preparedStatement = conn.prepareStatement(updateTableSQL);
			preparedStatement.setInt(1, msg.getId());
			preparedStatement .executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void editMessage(Message msg, String newMsg) {
		try {
			String updateTableSQL; 
			
				
			updateTableSQL= "UPDATE user_message SET  text= ?,receiver_has_read=false,date_time=? WHERE message_id = ?";
				
			
			PreparedStatement preparedStatement = conn.prepareStatement(updateTableSQL);
			preparedStatement.setString(1, EncryptionDecryptionAES.encryptMessage(newMsg));
			preparedStatement.setTimestamp(2, getCurrentTimeStamp());
			preparedStatement.setInt(3, msg.getId());
			preparedStatement .executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setRoleToUser(int role, ViewUser user) {
		try {
			String updateTableSQL; 
			
				
			updateTableSQL= "UPDATE user SET  role= ? WHERE user_id = ?";
				
			
			PreparedStatement preparedStatement = conn.prepareStatement(updateTableSQL);
			preparedStatement.setInt(1, role);
			preparedStatement.setInt(2, user.getId());
			preparedStatement .executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setRead(Message msg) {
		String updateTableSQL = "UPDATE user_message SET receiver_has_read = true WHERE message_id = ?";
		
		
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(updateTableSQL);
			preparedStatement.setInt(1, msg.getId());
			preparedStatement .executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new Timestamp(today.getTime());

	}
	
	
	
	
	
	
}
