package utils;

public class Payloads {

	public static String addUserPayload(String firstname, String lastname, String username, String email, String password, String roles) {
		return "{\r\n" + 
				"        \"firstName\": \""+firstname+"\",\r\n" + 
				"        \"lastName\": \""+lastname+"\",\r\n" + 
				"        \"username\": \""+username+"\",\r\n" + 
				"        \"email\": \""+email+"\",\r\n" + 
				"        \"password\": \""+password+"\",\r\n" + 
				"        \"roles\": \""+roles+"\"\r\n" +
				"    }";
	}
	
	public static String updateUserPayload(int id,String firstname, String lastname, String username, String email, String password, String roles) {
		return "{\r\n" + 
				"    \"id\": "+id+",\r\n" + 
				"        \"firstName\": \""+firstname+"\",\r\n" + 
				"        \"lastName\": \""+lastname+"\",\r\n" + 
				"        \"username\": \""+username+"\",\r\n" + 
				"        \"email\": \""+email+"\",\r\n" + 
				"        \"password\": \""+password+"\",\r\n" + 
				"        \"roles\": \""+roles+"\"\r\n" + 
				"}";
	}

}
