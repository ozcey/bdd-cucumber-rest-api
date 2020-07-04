package utils;

public class Payloads {

	public static String addUserPayload(String firstname, String lastname, String username, String email,
			String password, String roles) {
		return "{\r\n" + "        \"firstName\": \"" + firstname + "\",\r\n" + "        \"lastName\": \"" + lastname
				+ "\",\r\n" + "        \"username\": \"" + username + "\",\r\n" + "        \"email\": \"" + email
				+ "\",\r\n" + "        \"password\": \"" + password + "\",\r\n" + "        \"roles\": \"" + roles
				+ "\"\r\n" + "    }";
	}

	public static String updateUserPayload(Long id, String firstname, String lastname, String username, String email,
			String password, String roles) {
		return "{\r\n" + "    \"id\": " + id + ",\r\n" + "        \"firstName\": \"" + firstname + "\",\r\n"
				+ "        \"lastName\": \"" + lastname + "\",\r\n" + "        \"username\": \"" + username + "\",\r\n"
				+ "        \"email\": \"" + email + "\",\r\n" + "        \"password\": \"" + password + "\",\r\n"
				+ "        \"roles\": \"" + roles + "\"\r\n" + "}";
	}

	public static String addCustomerPayload(String firstName, String lastName, String email, String password,
			String phone, String street, String city, String state, String zipcode, String country) {
		return "{\n" + "        \"firstName\": \"" + firstName + "\",\r\n" + "        \"lastName\": \"" + lastName
				+ "\",\r\n" + "        \"username\": \"" + phone + "\",\r\n" + "        \"email\": \"" + email
				+ "\",\r\n" + "        \"password\": \"" + password + "\",\r\n" + "        \"address\": {\n"
				+ "            \"street\": \"" + street + "\",\r\n" + "        \"city\": \"" + city + "\",\r\n"
				+ "        \"state\": \"" + state + "\",\r\n" + "        \"zipcode\": \"" + zipcode + "\",\r\n"
				+ "        \"country\": \"" + country + "\",\r\n" + "        }\n" + "    }";
	}

}
