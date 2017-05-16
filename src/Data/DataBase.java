package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hsqldb.server.Server;

import Exception.NoneDBConnectionException;
import Exception.ReadDataBaseException;

public class DataBase {
	private Server hsqlServer = null;
	private Connection connection = null;
	private ResultSet rs = null;

	public DataBase() throws NoneDBConnectionException {
		try {
			hsqlServer = new Server();
			hsqlServer.setLogWriter(null);
			hsqlServer.setSilent(true);
			hsqlServer.setDatabaseName(0, "Ozlympic1");
			hsqlServer.setDatabasePath(0, "file:MYDB");
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:Ozlympic", "SA", "");
		} catch (Exception e) {
			throw new NoneDBConnectionException();
		}
	}

	public boolean checkDB() {
		try {
			hsqlServer.start();
			hsqlServer.stop();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public ArrayList<String> readDB(String table, ArrayList<String> col) throws ReadDataBaseException {
		hsqlServer.start();
		String sql = new String();
		try {
			ArrayList<String> lists = new ArrayList<String>();
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:Ozlympic", "SA", "");
			sql += "select ";
			for (int i = 0; i < col.size(); i++) {
				sql += col.get(i) + ", ";
			}
			sql = sql.substring(0, sql.length() - 2);
			sql += " from " + table + ";";
			rs = connection.prepareStatement(sql).executeQuery();
			while (rs.next()) {
				for (int i = 0; i < col.size(); i++) {
					lists.add(rs.getString(i + 1));
				}
			}
			connection.commit();
			return lists;
		} catch (Exception e) {
			throw new ReadDataBaseException(table);
		} finally {
			hsqlServer.stop();
		}
	}

	public void writeDB(String table, ArrayList<String> col, ArrayList<String> context, ArrayList<String> type) {
		hsqlServer.start();
		String sql = new String();
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:Ozlympic", "SA", "");
			connection.prepareStatement("drop table " + table + " if exists;").execute();
			sql = "create table " + table + "(";
			for (int i = 0; i < type.size(); i++) {
				sql += col.get(i) + " " + type.get(i) + ",";
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += ");";
			connection.prepareStatement(sql).execute();

			for (int i = 0; i < context.size(); i = i + col.size()) {
				sql = "insert into " + table + " (";
				for (int j = 0; j < col.size(); j++) {
					sql += col.get(j) + ",";
				}
				sql = sql.substring(0, sql.length() - 1);
				sql += ")" + " values (";
				for (int j = 0; j < col.size(); j++) {
					sql += "'" + context.get(i + j) + "',";
				}
				sql = sql.substring(0, sql.length() - 1);
				sql += ");";
				connection.prepareStatement(sql).execute();
			}
			connection.commit();

		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hsqlServer.stop();
		}
	}

	public void writeDB(String table, ArrayList<String> col, ArrayList<String> context) {
		hsqlServer.start();
		String sql = new String();
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:Ozlympic", "SA", "");
			for (int i = 0; i < context.size(); i = i + col.size()) {
				sql = "insert into " + table + " (";
				for (int j = 0; j < col.size(); j++) {
					sql += col.get(j) + ",";
				}
				sql = sql.substring(0, sql.length() - 1);
				sql += ")" + " values (";
				for (int j = 0; j < col.size(); j++) {
					sql += "'" + context.get(i + j) + "',";
				}
				sql = sql.substring(0, sql.length() - 1);
				sql += ");";
				connection.prepareStatement(sql).execute();
			}
			connection.commit();

		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hsqlServer.stop();
		}
	}
}
