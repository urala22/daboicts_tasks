package survey;

import java.sql.SQLException;

public class App {
    	public static void main(String args[]) throws SQLException {
		login login = new login();
		login.loginView();
	}
}