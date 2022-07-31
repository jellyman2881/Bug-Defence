package 타워디펜스;


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
		System.out.println("ㅗㅑㅗㅑ");
		Frame frame = new Frame(); 
	}
	public static Connection makeConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "tower";
		String password = "tower";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("연결성공");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("실패");
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
		// 배경음악 설정
		File file = new File("res/LetsMarch.wav"); //음악 경로
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
		this.setTitle("타워디펜스");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		 this.setExtendedState(MAXIMIZED_BOTH); //풀 스크린
		// this.setUndecorated(true);
		// this.setResizable(false);
		
		// this.pack();
		this.setFocusable(true);
		this.add(new Screen(this));
		this.setVisible(true);

	}

}
