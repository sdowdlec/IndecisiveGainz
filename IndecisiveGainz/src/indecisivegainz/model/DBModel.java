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
public class DBModel implements AutoCloseable
{
	private String mDBName;
	private String mTableName;
	private String[] mFieldNames;
	private String[] mFieldTypes;
	private Connection mConnection;
	private Statement mStmt;
	
	/**
	 * 
	 * @param dbName
	 * @param tableName
	 * @param fieldNames
	 * @param fieldTypes
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
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getAllRecords() throws SQLException
	{
		String selectSQL = "SELECT * FROM " + mTableName;
		return mStmt.executeQuery(selectSQL);
	}
	
	public ResultSet getRecordsOnField(String fieldName, String searchValue) throws SQLException
	{
		String selectSQL = "SELECT * FROM " + mTableName + " WHERE " + fieldName + " = '" + searchValue + "'";
		return mStmt.executeQuery(selectSQL);
	}
	
	/**
	 * 
	 * @param field
	 * @param condition
	 * @param conditionField
	 * @return
	 * @throws SQLException
	 */
	public String getItemOnCondition(String field, String condition, String conditionField) throws SQLException
	{
		String selectSQL = "SELECT FROM " + mTableName + " WHERE " + conditionField + " = " + condition;
		ResultSet rs = mStmt.executeQuery(selectSQL);
		String value = rs.getString(conditionField);
		
		return value;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getRecord(String key) throws SQLException 
	{
		String singleRecord = "SELECT * FROM " + mTableName + " WHERE " + mFieldNames[0] + "=" + key;
		return mStmt.executeQuery(singleRecord);
	}
	
	/**
	 * 
	 * @return
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
	 * 
	 * @param fields
	 * @param values
	 * @return
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
	 * 
	 * @param key
	 * @param fields
	 * @param values
	 * @return
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
	 * 
	 * @throws SQLException
	 */
	public void deleteAllRecords() throws SQLException 
	{
		String deleteSQL = "DELETE FROM " + mTableName;
		mStmt.executeUpdate(deleteSQL);
	}
	
	/**
	 * 
	 * @param key
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
	 * 
	 * @throws SQLException
	 */
	@Override
	public void close() throws SQLException 
	{
		mStmt.close();
		mConnection.close();
	}
}
