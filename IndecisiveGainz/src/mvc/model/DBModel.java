package mvc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Model class for the database tables.
 * Contains various methods to query and update the tables in SQLite.
 * @author Sean Dowdle
 *
 */
public class DBModel implements AutoCloseable
{
	private String mDBName;
	private String mTableName;
	private String[] mFieldNames;
	private String[] mFieldTypes;
	private Connection mConnection;
	private Statement mStmt;
	
	/**
	 * Constructs a DBModel object.
	 * @param dbName The name of the database
	 * @param tableName The name of the table
	 * @param fieldNames The field names for the table
	 * @param fieldTypes The data types for each field
	 * @throws SQLException
	 */
	public DBModel(String dbName, String tableName, String[] fieldNames, String[] fieldTypes) throws SQLException 
	{
		super();
		mDBName = dbName;
		mTableName = tableName;
		mFieldNames = fieldNames;
		mFieldTypes = fieldTypes;
		
		if (mFieldNames == null || mFieldTypes == null || mFieldNames.length == 0 || mFieldNames.length != mFieldTypes.length)
			throw new SQLException("Database field names and types must exist and have the same number of elements.");
		
		mConnection = connectToDB();
		mStmt = mConnection.createStatement();
		createTable();
	}
	
	private void createTable() throws SQLException 
	{
		StringBuilder createSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		createSQL.append(mTableName).append("(");
		
		for (int i = 0; i < mFieldNames.length; i++)
			createSQL.append(mFieldNames[i]).append(" ").append(mFieldTypes[i]).append((i < mFieldNames.length - 1) ? "," : ")");
		
		mStmt.executeUpdate(createSQL.toString());
	}
	
	/**
	 * Queries the table to return a ResultSet of all the records.
	 * @return A ResultSet of all the records from the table
	 * @throws SQLException
	 */
	public ResultSet getAllRecords() throws SQLException
	{
		String selectSQL = "SELECT * FROM " + mTableName;
		return mStmt.executeQuery(selectSQL);
	}
	
	/**
	 * Queries the table on a specified field to return all records containing the given search value.
	 * @param fieldName The field name to query on
	 * @param searchValue The value to query the table/field on
	 * @return A ResultSet containing all of the records that were found on the specified field and value
	 * @throws SQLException
	 */
	public ResultSet getRecordsOnField(String fieldName, String searchValue) throws SQLException
	{
		String selectSQL = "SELECT * FROM " + mTableName + " WHERE " + fieldName + " = '" + searchValue + "'";
		return mStmt.executeQuery(selectSQL);
	}
	
	/**
	 * Queries the table on a field and condition to return the value
	 * @param condition The condition to query on
	 * @param conditionField The field to query on
	 * @return The value from the field that was found from the query
	 * @throws SQLException
	 */
	public String getItemOnCondition(String condition, String conditionField) throws SQLException
	{
		String selectSQL = "SELECT FROM " + mTableName + " WHERE " + conditionField + " = " + condition;
		ResultSet rs = mStmt.executeQuery(selectSQL);
		String value = rs.getString(conditionField);
		
		return value;
	}
	
	/**
	 * Queries the table on the key to return its record.
	 * @param key The primary key to query on
	 * @return The record matching the specified primary key
	 * @throws SQLException
	 */
	public ResultSet getRecord(String key) throws SQLException 
	{
		String singleRecord = "SELECT * FROM " + mTableName + " WHERE " + mFieldNames[0] + "=" + key;
		return mStmt.executeQuery(singleRecord);
	}
	
	/**
	 * Returns the number of records in a table.
	 * @return The number of records in a table.
	 * @throws SQLException
	 */
	public int getRecordCount() throws SQLException 
	{
		int count = 0;
		try (ResultSet rs = getAllRecords())
		{
			while (rs.next())
				count++;
		}
		return count;
	}
	
	/**
	 * Inserts a new record into the table.
	 * @param fields An array containing all of the field names for the table
	 * @param values An array containing all of the values for the new record
	 * @return The primary key that was assigned to the newly created record
	 * @throws SQLException
	 */
	public int createRecord(String[] fields, String[] values) throws SQLException 
	{
		if (fields == null || values == null || fields.length == 0 || fields.length != values.length)
			return -1;

		StringBuilder insertSQL = new StringBuilder("INSERT INTO ");
		insertSQL.append(mTableName).append(" (");
		
		for (int i = 0; i < fields.length; i++)
			insertSQL.append(fields[i]).append((i < fields.length - 1) ? "," : ") VALUES(");
		for (int i = 0; i < values.length; i++)
			insertSQL.append(convertToSQLText(fields[i], values[i])).append((i < values.length - 1) ? "," : ")");

		System.out.println(insertSQL);
		mStmt.executeUpdate(insertSQL.toString());
		
		return mStmt.getGeneratedKeys().getInt(1);
	}
	
	/**
	 * Updates a record in the table based on its primary key.
	 * @param key The primary key of the record to update.
	 * @param fields An array containing all of the field names for the table
	 * @param values An array containing all of the values to update the record with
	 * @return A boolean based on if we could update the record or not
	 * @throws SQLException
	 */
	public boolean updateRecord(String key, String[] fields, String[] values) throws SQLException 
	{
		if (fields == null || values == null || fields.length == 0 || values.length == 0 || fields.length != values.length)
			return false;

		StringBuilder updateSQL = new StringBuilder("UPDATE ");
		updateSQL.append(mTableName).append(" SET ");
		
		for (int i = 0; i < fields.length; i++)
			updateSQL.append(fields[i]).append("=").append(convertToSQLText(fields[i], values[i]))
					.append((i < values.length - 1) ? "," : " ");

		updateSQL.append("WHERE ").append(mFieldNames[0]).append("=").append(key);
		mStmt.executeUpdate(updateSQL.toString());

		return true;
	}
	
	/**
	 * Deletes all records from the table.
	 * @throws SQLException
	 */
	public void deleteAllRecords() throws SQLException 
	{
		String deleteSQL = "DELETE FROM " + mTableName;
		mStmt.executeUpdate(deleteSQL);
	}
	
	/**
	 * Deletes a specific record based on the given primary key.
	 * @param key The primary key of the record to delete
	 * @throws SQLException
	 */
	public void deleteRecord(String key) throws SQLException 
	{
		String deleteRecord = "DELETE FROM " + mTableName + " WHERE " + mFieldNames[0] + "=" + key;
		mStmt.executeUpdate(deleteRecord);
	}
	
	private String convertToSQLText(String field, String value) 
	{
		for (int i = 0; i < mFieldNames.length; i++)
		{
			if (field.equalsIgnoreCase(mFieldNames[i])) 
			{
				if (mFieldTypes[i].toUpperCase().trim().equals("TEXT"))
					return "'" + value + "'";
				else
					break;
			}
		}
		return value;
	}
	
	private Connection connectToDB() throws SQLException 
	{
		try 
		{
			Class.forName("org.sqlite.JDBC");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:sqlite:" + mDBName);
	}
	
	/**
	 * Closed connection to the SQLite database and Statement.
	 * @throws SQLException
	 */
	@Override
	public void close() throws SQLException 
	{
		mStmt.close();
		mConnection.close();
	}
}
