
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Board1 implements  ActionListener, Runnable  {
	private JButton[][] table_board = new JButton[5][5];
	private int port;
	char mark;
	private Socket clientSocket;
	private DataOutputStream outp;
	private DataInputStream inp;
	private JPanel panel;
	private String GamerName;



 	public Board1(String GamerName,int port,char mark){
		this.port = port;
		this.mark = mark;
		this.GamerName = GamerName;
		try {
			clientSocket = new Socket("localhost",port);
			outp = new DataOutputStream(clientSocket.getOutputStream());
			inp = new DataInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
 		JFrame board = new JFrame(GamerName);
		board.setSize(600, 600);
		panel = new JPanel();
		
		panel.setLayout(new GridLayout(6,5,2,2));
		JTextField txt = new JTextField("tu est le joueur " + this.mark,50);
		board.setContentPane(panel);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addintopanel();
		panel.add(txt);
		//listener(this.port);

	    board.setVisible(true);
		Thread t= new Thread(this);
		t.start();
	}
 
 	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
 		try {
 		
			System.out.println(((JButton) e.getSource()).getName());
			String str = ((JButton) e.getSource()).getName();
			outp = new DataOutputStream(clientSocket.getOutputStream());
			outp.writeUTF(str);
			String[] pos = str.split(",");
			int row = Integer.parseInt(pos[0]);
			int col = Integer.parseInt(pos[1]);
			//setChoix(row,col);
			
			
		}catch(Exception e1) {
			System.out.println("error -> " + e1.getMessage());
		}
		
	}

	public void addintopanel() {
		// TODO Auto-generated method stub
		for(int row = 0; row < 5;row++)
		{
			for(int col = 0; col < 5;col++)
			{
				//JButton imgbtn = new JButton(new ImageIcon("/home/escanor/Downloads/circle-ring.png"));
				JButton imgbtn = new JButton();
				imgbtn.setName(row + "," +col);
				imgbtn.setBackground(new java.awt.Color(29, 41, 81));
				imgbtn.addActionListener(this);
				table_board[row][col] =  (JButton) panel.add(imgbtn);				
			}
		}
	}
	
	
	
	public JButton[][] getBoard() {
		return table_board;
	}
	
	
	
	
	
	
	public synchronized void setChoix(int x,int y) {
		//System.out.println(i + "->" + j + " " + this.GamerName);
		//table_board[i][j].setBackground(new java.awt.Color(255, 255, 255));
		//table_board[i][j].setIcon(new ImageIcon("circle-ring.png"));
		for (int i=0 ; i<panel.getComponentCount();i++){
			JButton b = ((JButton)panel.getComponent(i));
			if(b.getName().equals(x+",")){
				if(b.getLabel().isEmpty()) {
					b.setLabel(ch);
					if(ch==X)
						activated(true);
					else
						activated(true);
				}
			}
		}

	}
	
	
	
	@Override
	public  void run() {
	// TODO Auto-generated method stub
		//String line ;

		while(true) {
		System.out.println("client Thread");
			try {

				//line = inp.readUTF();
			System.out.println("client : " + inp.readUTF());
		String[] pos = inp.readUTF().split(",");
		int row = Integer.parseInt(pos[0]);
		int col = Integer.parseInt(pos[1]);
		setChoix(row,col);
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		
}
	
//	   public void processServerCommands()
//	    {
//	        try
//	        {
//	            System.out.println("Processing the server's command...");
//	    		String line = in.nextLine();
//	    		System.out.println("client : " + line);
//	    		String[] pos = line.split(",");
//	    		int row = Integer.parseInt(pos[0]);
//	    		int col = Integer.parseInt(pos[1]);
//	    		//setChoix(row,col);
//	          
//	        }catch(Exception e) {
//	        	
//	        }
//	    }
	public static void main(String args[])
	{
		Board1 j1 = new Board1("abdelmoula",7412,'X');
		Board1 j2 = new Board1("yassine",7412,'O');
		//Board1 j3 = new Board1("adil",7412,'c');
		//ystem.out.println(matrix[1][1].getText());
	}

}
