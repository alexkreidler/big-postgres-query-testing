import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.apache.arrow.adapter.jdbc.ArrowVectorIterator;
import org.apache.arrow.adapter.jdbc.JdbcToArrow;
import org.apache.arrow.adapter.jdbc.JdbcToArrowConfig;
import org.apache.arrow.adapter.jdbc.JdbcToArrowConfigBuilder;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.VectorSchemaRoot;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// you need create database with this name 'github-example-jdbc'
		String url = "jdbc:postgresql://0.0.0.0:6120/nyc-taxi-data?loggerLevel=OFF";

		// user default
		String user = "postgres";
		// your password. root is default
		String password = "pgpass";

		// load driver communication of postgresql.
		// Class.forName("org.postgresql.Driver");
		// open the connection
		Connection connection = DriverManager.getConnection(url, user, password);

		Statement statement = connection.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setFetchSize(500000);

		ResultSet results = statement
				.executeQuery("SELECT id, vendor_id, store_and_fwd_flag, passenger_count FROM trips;");

		int rowcount = 0;
		if (results.last()) {
			rowcount = results.getRow();
			// results.beforeFirst(); // not rs.first() because the rs.next() below will
			// move on, missing the first element
		}
		System.out.println("size: " + rowcount);
		// try {
		// BufferAllocator allocator = new RootAllocator(Integer.MAX_VALUE);

		// ArrowVectorIterator arrow = JdbcToArrow.sqlToArrowVectorIterator(results,
		// allocator);
		// // arrow.toString()
		// int sum = 0;
		// while (arrow.hasNext()) {
		// // VectorSchemaRoot item = arrow.next();
		// sum++;
		// // System.out.println("Result: " + item.getRowCount());
		// }
		// System.out.println("Result: " + sum + " " + arrow.toString());

		// } catch (Exception e) {
		// // IOException | RuntimeException
		// System.out.println("Error: " + e.toString());
		// e.getCause().printStackTrace(System.out);
		// e.printStackTrace(System.out);
		// }
		// results.last();

	}

}
