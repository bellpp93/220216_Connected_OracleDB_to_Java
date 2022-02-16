package ����Ŭ����;

import java.sql.Connection;		// interface
import java.sql.DriverManager;	// class
import java.sql.ResultSet;		// interface
import java.sql.Statement;		// interface

/**
 * testdb �������� ����Ŭ DB�� �����Ͽ� ���(employee) ���̺���
 * ���ڵ带 �˻�(select)�Ͽ� �ܼ�â�� �����ֱ� ���α׷�
 */

public class SelectTest {
	// select SQL ���� ���� => sql Ŭ���� ������ �ش�. 
	final static String sql = "select * from employee";
	
	public static void main(String[] args) {
		/*
		 * oracle.jdbc.driver ������			ojdbc14.jar �������� Ǯ��
		 * �ڵ����� ����� ���� ���� �̸����̴�.
		 * OracleDriver �̰��� ���� ����̹� �����̴�.		Ȯ���ڰ�  .class
		 */
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		
		/*
		 * MySQL DB�� ������ ��
		 * String driver = "com.mysql.jdbc.Driver";
		 * String url = "jdbc:mysql://localhost:3306/jdbc";
		 */
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, "testdb", "testdb1234");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);  // executeQuery() �޼ҵ�� select �۾� �� ȣ���ϴ� �޼ҵ�
			
			System.out.println("�����ȣ \t �̸� \t ���� \t �̸���");
			System.out.println("------------------------------");
			
			while(rs.next()) {
				System.out.print(rs.getInt(1) + "\t");  // �����ȣ
				System.out.print(rs.getString(2) + "\t");  // �̸�
				System.out.print(rs.getString(3) + "\t");  // ����
				System.out.print(rs.getString(4) + "\n");  // �̸���
			}
			
		} catch(Exception e) {
			System.out.println(e);
		} finally {
			// finally{} ����� try{} ��Ͽ��� ���ܰ� �߻��ߵ� ���ߵ� ������ ó��
			// ������ ����� �ڿ����� �����϶�!!
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}
}
