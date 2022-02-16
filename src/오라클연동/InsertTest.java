package 오라클연동;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * testdb 계정으로 오라클 DB에 접속하여 사원(employee)테이블의
 * 데이터를 삽입(insert)하는 애플리케이션 프로그램
 */

public class InsertTest extends JFrame {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 생성자
	public InsertTest() {
		setTitle("사원 테이블에 데이터 삽입");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new Mypanel());
		setSize(450, 400);
		setLocation(500, 300);
		setVisible(true);
	}
	
	// 디자인을 담당하는 내부 클래스 구현
	class Mypanel extends JPanel implements ActionListener {
		String[] text = {"번호","이름","직급","이메일"};
		JTextField[] tf = new JTextField[4];
		
		// 생성자
		public Mypanel() {
			setBackground(Color.GREEN);
			setLayout(null);  // 개발자 자유 배치
			
			JButton insertBtn = new JButton("추가");
			insertBtn.setSize(70, 30);
			insertBtn.setLocation(300, 200);
			add(insertBtn);
			
			JLabel la = new JLabel();
			
			for (int i = 0; i < text.length; i++) {
				la = new JLabel(text[i]);
				la.setHorizontalAlignment(JLabel.RIGHT);
				la.setSize(60, 30);
				la.setLocation(50, 60+i*30);
				add(la);
				
				tf[i] = new JTextField(30);
				tf[i].setSize(140, 30);
				tf[i].setLocation(120, 60+i*30);
				add(tf[i]);
			}
			// 이벤트 연결
			insertBtn.addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, "testdb", "testdb1234");
				
				// Statement 방식보다 PreparedStatement 방식이 보안에 좋다.
				String sql = "insert into employee values(?,?,?,?)";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(tf[0].getText()));
				pstmt.setString(2, tf[1].getText());
				pstmt.setString(3, tf[2].getText());
				pstmt.setString(4, tf[3].getText());
				
				//insert, update, delete 작업 시에는 executeUpdate() 메소드 호출
				pstmt.executeUpdate();
				
			} catch (Exception ex) {
				System.out.println(ex);
			} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
	}
	

	public static void main(String[] args) {
		// 객체 생성
		InsertTest insertTest = new InsertTest();
	}
}
