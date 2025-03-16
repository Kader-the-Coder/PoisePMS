package database;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * This class manages all interactions with the database.
 */
public class DatabaseManager {
  private final static String URL = "jdbc:mysql://localhost:3306/PoisePMS?useSSL=false";
  private final static String USER = "otheruser";
  private final static String PASSWORD = "swordfish";

  /**
   * Establishes a connection to the MySQL database.
   *
   * This method attempts to connect using predefined credentials (URL, user, and password).
   * If the connection fails due to invalid credentials, the program prints an error message
   * in red, advises the user to check their MySQL server and database, and then terminates the application.
   *
   * @return Connection The established MySQL database connection.
   * @throws SQLException If a database access error occurs.
   */
  private static Connection getConnection() {
    Scanner scanner = new Scanner(System.in);
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (SQLException e) {
      System.out.println("\u001B[31m" + """
        Invalid credentials.
    
        Please ensure that your MySQL server is running
        and that it has a database called 'PoisePMS'.
    
        To start MySQL manually:
        
        - On Windows: Open Command Prompt and run:
            net start mysql
    
        - On Linux (Ubuntu/Debian): Open Terminal and run:
            sudo systemctl start mysql
    
        - On macOS (Homebrew): Open Terminal and run:
            brew services start mysql
    
        After starting MySQL, navigate to the MySQL installation directory:
        
        - On Windows: Typically located in `C:\\Program Files\\MySQL\\MySQL Server X.X\\bin`
        - On Linux/macOS: You can usually access MySQL by typing `mysql` directly in the terminal, or navigating to `/usr/bin/` where MySQL is installed.
    
        Then, log in to MySQL using the following command:
            mysql -u otheruser -p

            NOTE: The password must be: swordfish
    
        The application will now close...
        """ + "\u001B[0m");


      System.exit(0);
      }

    return connection;
  }

  /**
   * Executes a SQL query and returns a list of results.
   *
   * @param query   The SQL query to execute.
   * @param params  The parameters to be set in the query.
   * @return        A list of maps, where each map represents a row with column names as keys.
   */
  public static List<Map<String, Object>> executeQuery(String query, Object... params) {
    List<Map<String, Object>> results = new ArrayList<>();

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

      // Set parameters dynamically
      for (int i = 0; i < params.length; i++) {
        switch (params[i]) {
          case Integer integer -> statement.setInt(i + 1, integer);
          case String string -> statement.setString(i + 1, string);
          case Double decimal -> statement.setDouble(i + 1, decimal);
          case Boolean bool -> statement.setBoolean(i + 1, bool);
          case Date date -> statement.setDate(i + 1, date);
          case null, default -> statement.setObject(i + 1, params[i]);  // Fallback for other types
        }
      }

      try (ResultSet resultSet = statement.executeQuery()) {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
          Map<String, Object> row = new HashMap<>();

          // Dynamically process each column
          for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            int columnType = metaData.getColumnType(i);

            switch (columnType) {
              case Types.INTEGER:
                row.put(columnName, resultSet.getInt(i));
                break;
              case Types.VARCHAR:
              case Types.CHAR:
                row.put(columnName, resultSet.getString(i));
                break;
              case Types.DECIMAL:
              case Types.NUMERIC:
                row.put(columnName, resultSet.getBigDecimal(i));
                break;
              case Types.DOUBLE:
              case Types.FLOAT:
                row.put(columnName, resultSet.getDouble(i));
                break;
              case Types.BOOLEAN:
                row.put(columnName, resultSet.getBoolean(i));
                break;
              case Types.DATE:
                row.put(columnName, resultSet.getDate(i));
                break;
              case Types.TIMESTAMP:
                row.put(columnName, resultSet.getTimestamp(i));
                break;
              default:
                row.put(columnName, resultSet.getObject(i));  // Handle other types
            }
          }

          results.add(row);
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return results;
  }

  /**
   * Executes a SQL update (INSERT, UPDATE, DELETE) and returns the number of affected rows.
   *
   * @param query       The SQL query to execute.
   * @param parameters  The parameters to be set in the query.
   * @return            The number of affected rows, or -1 if an error occurs.
   */
  public static int executeUpdate(String query, Object... parameters) {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

      for (int i = 0; i < parameters.length; i++) {
        statement.setObject(i + 1, parameters[i]);
      }

      return statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }
}
