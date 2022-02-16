package 오라클연동;

import java.sql.Connection;		// interface
import java.sql.DriverManager;	// class
import java.sql.ResultSet;		// interface
import java.sql.Statement;		// interface

/**
 * testdb 계정으로 오라클 DB에 접속하여 사원(employee) 테이블의
 * 레코드를 검색(select)하여 콘솔창에 보여주기 프로그램
 */

public class SelectTest {
	// select SQL 문장 생성 => sql 클래스 변수로 준다. 
	final static String sql = "select * from employee";
	
	public static void main(String[] args) {
		/*
		 * oracle.jdbc.driver 까지는			ojdbc14.jar 앞축파일 풀면
		 * 자동으로 만들어 지는 폴더 이름들이다.
		 * OracleDriver 이것이 실제 드라이버 파일이다.		확장자가  .class
		 */
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		
		/*
		 * MySQL DB와 연동할 때
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
			rs = stmt.executeQuery(sql);  // executeQuery() 메소드는 select 작업 시 호출하는 메소드
			
			System.out.println("사원번호 \t 이름 \t 직급 \t 이메일");
			System.out.println("------------------------------");
			
			while(rs.next()) {
				System.out.print(rs.getInt(1) + "\t");  // 사원번호
				System.out.print(rs.getString(2) + "\t");  // 이름
				System.out.print(rs.getString(3) + "\t");  // 직급
				System.out.print(rs.getString(4) + "\n");  // 이메일
			}
			
		} catch(Exception e) {
			System.out.println(e);
		} finally {
			// finally{} 블록은 try{} 블록에서 예외가 발생했든 안했든 무조건 처리
			// 위에서 사용한 자원들을 해제하라!!
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
