package mvc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class responsible for user login authentication and
 * account creation.
 * @author Sean Dowdle
 * 
 */
public class Authentication 
{
	private static final int logRounds = 4;
	
	private static Connection connectToUsersDB() throws SQLException
	{
		try 
		{
			Class.forName("org.sqlite.JDBC");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:sqlite:indecisive_gainz.db");
	}
	
	private static String getUserHashedPassword(String username)
	{
		String query = "SELECT * FROM users WHERE LOWER(username) = '" + username.toLowerCase() + "'";
		
		try(Connection connect = connectToUsersDB();
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(query);)
		{
			if(rs.next())
				return rs.getString("hashed_password");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	private static boolean isUniqueUsername(String username)
	{
		String query = "SELECT * FROM users WHERE LOWER(username) = '" + username.toLowerCase() + "'";
		//System.out.println(query);
		
		try(Connection connect = connectToUsersDB();
				Statement stmt = connect.createStatement();
				ResultSet rs = stmt.executeQuery(query);)
		{
			return !rs.next();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	private static String hashPassword(String plaintextPassword)
	{
		String salt = BCrypt.gensalt(logRounds);
		String hashedPassword = BCrypt.hashpw(plaintextPassword, salt);
		
		return hashedPassword;
	}
	
	private static boolean verifyPassword(String plaintextPassword, String hashedPassword)
	{
		boolean isValidPassword = BCrypt.checkpw(plaintextPassword, hashedPassword);
		
		return isValidPassword;
	}
	
	/**
	 * Verifies the login info given.
	 * @param username The entered username for the login
	 * @param password The entered password for the login
	 * @return A boolean based on if the user entered a correct login
	 */
	public static boolean authenticateLogin(String username, String password)
	{
		String retrievedHash = getUserHashedPassword(username);
		if(retrievedHash != null)
			return verifyPassword(password, retrievedHash);
		else
			return false;
	}
	
	/**
	 * Returns the id of the username from the users table in the SQLite database.
	 * @param username The username to get the id from
	 * @return The id of the given username
	 */
	public static int getUserId(String username)
	{
		String query = "SELECT * FROM users WHERE LOWER(username) = '" + username.toLowerCase() + "'";
				
		try(Connection connect = connectToUsersDB();
				Statement stmt = connect.createStatement();
				ResultSet rs = stmt.executeQuery(query);)
		{
			return rs.getInt("id");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return 0;
	}
	
	/**
	 * Creates the account with the given information if there is no issue
	 * with the username already existsing or the password and confirmed password
	 * being different.
	 * @param username The username of the new account to create
	 * @param password The password of the new account to create
	 * @param confirmedPassword The confirmed password of the new account to create
	 * @return A boolean based on if the account was created successfully
	 */
	public static boolean createAccount(String username, String password, String confirmedPassword)
	{
		if(isUniqueUsername(username))
		{
			if(password.equals(confirmedPassword))
			{
				String hashedPassword = hashPassword(password);
				String insert = "INSERT INTO users (username, hashed_password) VALUES ('" + username +"', '" + hashedPassword + "')";
				System.out.println(insert);
				
				try(Connection connect = connectToUsersDB();
						Statement stmt = connect.createStatement();)
				{
					stmt.executeUpdate(insert);
				}
				catch(SQLException e)
				{
					// TODO SQLITE_BUSY being caught here
					System.out.println(e.getMessage());
					return false;
				}
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
}
