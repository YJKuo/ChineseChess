package game;
import java.io.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

	public static Socket c1;
	public static DataInputStream in ;
	public static DataOutputStream out;
	public Client() {
		
	}
	public boolean creatClient() {
		
		try {
			
			c1 = new Socket("localhost",1111);
			in = new DataInputStream(c1.getInputStream());
			out = new DataOutputStream(c1.getOutputStream());
	
		}catch(IOException e) {return false;}
		
		return true;
		
	}
	public void sentMSG(String msg) {
		try {
		
		out.writeUTF(msg);
		}catch(IOException e) {System.out.println("client sent msg error");} 
		
	}
	public String receiveMSG() {
		String msg="";
		try {
			
			msg = new String(in.readUTF());
			
			
		}catch(IOException e) {
			System.out.println("client receive msg error");
			System.exit(0);
		} 
		
		
		return msg;
	}
}
