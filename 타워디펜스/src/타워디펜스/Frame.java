package Ÿ�����潺;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Frame extends JFrame {

	
	
	
	
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) {
		System.out.println("�Ǥ��Ǥ�");
		Frame frame = new Frame(); 
	}
	public static Connection makeConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "tower";
		String password = "tower";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("���Ἲ��");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("����");
		}

		try {
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Frame() {
		// ������� ����
		File file = new File("res/LetsMarch.wav"); //���� ���
		System.out.println(file.exists()); //true
		 try {
	            
	            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
	            Clip clip = AudioSystem.getClip();
	            clip.open(stream);
	            clip.start();
	            
	        } catch(Exception e) {
	            System.out.println("error");
	            e.printStackTrace();
	        }
		//this.setSize(1700, 800);
		this.setTitle("Ÿ�����潺");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		 this.setExtendedState(MAXIMIZED_BOTH); //Ǯ ��ũ��
		// this.setUndecorated(true);
		// this.setResizable(false);
		
		// this.pack();
		this.setFocusable(true);
		this.add(new Screen(this));
		this.setVisible(true);

	}

}
