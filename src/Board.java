import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;
public class Board  extends MouseAdapter implements  Runnable  {
	private JButton[][] table_board = new JButton[5][5];
	private int port;
	char mark;
	private Socket clientSocket;
	private PrintWriter out;
	private Scanner in;
	private JPanel panel;
	private JPanel panel2;
	private Thread t;

 	public Board(String GamerName,int port,char mark){
		this.port = port;
		this.mark = mark;
		try {
			clientSocket = new Socket("localhost",port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		JFrame board = new JFrame(GamerName);
		board.setSize(600, 600);
		panel = new JPanel();
		panel2 = new JPanel();
		panel.setLayout(new GridLayout(6,5,2,2));
		JTextField txt = new JTextField("tu est le joueur " + this.mark,50);
		board.setContentPane(panel);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addintopanel();
		panel.add(txt);
		listener(this.port);
	    board.setVisible(true);
	}
	public void listener(int port) {
		
		try {
			
			out = new PrintWriter(clientSocket.getOutputStream(),true);
			in = new Scanner(clientSocket.getInputStream());
			JButton[][] matrix = getBoard();
			new Thread(this).start(); 
			for(int i = 0;  i < 5; i++)
			{
				for(int j =  0; j < 5;j++)
				{
					final int row = i;
					final int col = j;
			
					matrix[i][j].addMouseListener(new MouseAdapter() {
						
						public void mousePressed(MouseEvent e) {
							try {
								System.out.println(row+","+col);
								String str = row+","+col;
								out.println(str);
								
								//System.out.println(in.nextLine());
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					});
					
				}
	
			}
			
		
		}catch(Exception e)
		{
			System.out.println("error in Client THread -> " + e.getMessage());
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
				imgbtn.setBackground(new java.awt.Color(29, 41, 81));
				table_board[row][col] =  (JButton) panel.add(imgbtn);				
			}
		}
	}
	public JButton[][] getBoard() {
		return table_board;
	}
	public void setChoix(int i,int j) {
		table_board[i][j].setBackground(new java.awt.Color(120, 15, 3));
		table_board[i][j].setIcon(new ImageIcon("/home/escanor/Downloads/circle-ring.png"));
	}
	@Override
	public final void run() {
	// TODO Auto-generated method stub
		while(true) {
		System.out.println("client Thread");
		String line = in.nextLine();
		System.out.println("client : " + line);
			String[] pos = line.split(",");
			int row = Integer.parseInt(pos[0]);
			int col = Integer.parseInt(pos[1]);
			setChoix(row,col);
		}
		
}
	

	public static void main(String args[])
	{
		Board j1 = new Board("abdelmoula",7412,'X');
		Board j2 = new Board("yassine",7412,'O');
		//ystem.out.println(matrix[1][1].getText());
	}

}
