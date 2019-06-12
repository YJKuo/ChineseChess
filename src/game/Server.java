package game;
import java.io.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static ServerSocket s;
	public static Socket s1;
	public static DataInputStream in ;
	public static DataOutputStream out;
	public Server() {
		
		try {
			s = new ServerSocket(1111);
			System.out.println("Server is created.");
			
			s1 = s.accept();
			System.out.println("Client is connected , ip"+s1.getInetAddress());
			
			
			
			in = new DataInputStream(s1.getInputStream());
			out = new DataOutputStream(s1.getOutputStream());
			

			
		//	s1.close(); 
			
			
		}catch(IOException e) {System.out.println("Connection Error");} 
		
	}
	public void sentMSG(String msg) {
		try {
		
		out.writeUTF(msg);
		}catch(IOException e) {System.out.println("Server sent msg error");} 
		
	}
	public String receiveMSG() {
		String msg="";
		try {
			
			msg = new String(in.readUTF());
			
			
		}catch(IOException e) {
			System.out.println("server receive msg error");
			System.exit(0);
		} 
		
		
		return msg;
	}
	
}
