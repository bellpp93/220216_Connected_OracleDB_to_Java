package ����Ŭ����;

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
 * testdb �������� ����Ŭ DB�� �����Ͽ� ���(employee)���̺���
 * �����͸� ����(insert)�ϴ� ���ø����̼� ���α׷�
 */

public class InsertTest extends JFrame {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// ������
	public InsertTest() {
		setTitle("��� ���̺� ������ ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new Mypanel());
		setSize(450, 400);
		setLocation(500, 300);
		setVisible(true);
	}
	
	// �������� ����ϴ� ���� Ŭ���� ����
	class Mypanel extends JPanel implements ActionListener {
		String[] text = {"��ȣ","�̸�","����","�̸���"};
		JTextField[] tf = new JTextField[4];
		
		// ������
		public Mypanel() {
			setBackground(Color.GREEN);
			setLayout(null);  // ������ ���� ��ġ
			
			JButton insertBtn = new JButton("�߰�");
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
			// �̺�Ʈ ����
			insertBtn.addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, "testdb", "testdb1234");
				
				// Statement ��ĺ��� PreparedStatement ����� ���ȿ� ����.
				String sql = "insert into employee values(?,?,?,?)";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(tf[0].getText()));
				pstmt.setString(2, tf[1].getText());
				pstmt.setString(3, tf[2].getText());
				pstmt.setString(4, tf[3].getText());
				
				//insert, update, delete �۾� �ÿ��� executeUpdate() �޼ҵ� ȣ��
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
		// ��ü ����
		InsertTest insertTest = new InsertTest();
	}
}
