package data;

public class User {
	
	private String username;
	
	public User(String username) {
		if (username == null || username.isEmpty() || username.isBlank()) {
			throw new IllegalArgumentException("Username cannot be blank");
		}
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	@Override
	public String toString() {
		return "Username " + username;
	}

}
