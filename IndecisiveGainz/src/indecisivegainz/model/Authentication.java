package indecisivegainz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 
 * @author Sean Dowdle
 * 
 */
public class Authentication 
{
	private static int logRounds = 4;
	
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
	

	
	public static String hashPassword(String plaintextPassword)
	{
		String salt = BCrypt.gensalt(logRounds);
		String hashedPassword = BCrypt.hashpw(plaintextPassword, salt);
		
		return hashedPassword;
	}
	
	public static boolean verifyPassword(String plaintextPassword, String hashedPassword)
	{
		boolean isValidPassword = BCrypt.checkpw(plaintextPassword, hashedPassword);
		
		return isValidPassword;
	}
	
	public static boolean authenticateLogin(String username, String password)
	{
		String retrievedHash = getUserHashedPassword(username);
		if(retrievedHash != null)
			return verifyPassword(password, retrievedHash);
		else
			return false;
	}
	
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
					System.out.println(e.getMessage());
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
