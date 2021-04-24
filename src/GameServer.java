import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class GameServer implements Runnable{
	private int port; // numero de port
	private DataOutputStream  outp;  // pour diffuser des donne au joueurs
	private DataInputStream inp; // pour lire les les donnees des joueurs
	private ServerSocket ss; // Serveur socket
	private Socket clientSocket; // socket client
	private Thread t; // C'est pour gerer un serveur Thread
	private int[][] MatrixBoard = new int[5][5]; // la Matrice dans laquelle j'ai stocke 'X' ET 'O'
	private Collection<Socket> gamers = new ArrayList<>();
	public GameServer(int port)
	{
		/**
		 * le serveur doit ecouter au port lors l'instantiation d'objet
		 */
		
		listener(port); 
	}
	
	/**
	 *	cette methode permet d'ecouter et accpeter les requette des joueurs
	 * @param port
	 * @return void
	 * 
	 */
	public void listener(int port) {
		try {
			ss = new ServerSocket(port);
			
			while(true) {
				System.out.println("about accepting client Request .....");
				clientSocket = ss.accept();
				System.out.println("connection accepted from : " + clientSocket);
				//outp = new DataOutputStream(clientSocket.getOutputStream());
				//inp = new DataInputStream(clientSocket.getInputStream());
				//gamers.put(clientSocket,out);
				//GameServerThread t = new GameServerThread(this,MatrixBoard);
				 new Thread(this).start();
			}
		}catch(Exception e)
		{
			System.out.println("error => : " + e.getMessage());
		}
	}
	/**
	 *	cette methode permet d'afficher la matrice de jeux dans la console
	 */
	public void printMatrix()
	{
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5 ;j++)
			{
		        System.out.print(MatrixBoard[i][j] + " ");
			}
		    System.out.println();
		}
	}
	/**
	 * la methode run() pour gerer les thread 
	 */
	public boolean checking_validity(int[][] MatrixBoard,int row,int col,int ptr,int top,int bottom,int right,int left,int top_right,int top_left,int bottom_right,int bottom_left)
	{
		top = (row - ptr >= 0) && (MatrixBoard[row-ptr][col] == 1) ? top + 1  : top  - 1;
		bottom = (row + ptr <= 4) && (MatrixBoard[row + ptr][col] == 1) ? bottom + 1 : bottom - 1;
		right =  (col + ptr <= 4) && (MatrixBoard[row][col + ptr] == 1) ? right + 1 : right - 1;
		left = (col - ptr >=0 ) && (MatrixBoard[row][col - ptr] == 1) ? left + 1 : left - 1;
		top_right = (row - ptr >= 0) && (col + ptr <= 4) && (MatrixBoard[row - ptr][col + ptr] == 1) ? top_right + 1 : top_right - 1;
		top_left =  (row - ptr >= 0) && (col - ptr >=0 ) && (MatrixBoard[row - ptr][col  - ptr] == 1) ? top_left + 1 : top_left - 1;
		bottom_right  = (row + ptr <= 4) && (col + ptr <= 4) && (MatrixBoard[row + ptr][col + ptr] == 1) ? bottom_right + 1 : bottom_right - 1;	
		bottom_left =  (row + ptr <= 4) && (col - ptr >=0 ) && (MatrixBoard[row + ptr][col  - ptr] == 1) ? bottom_left + 1 : bottom_left - 1;
		if((ptr <= 2 ) && ((top_right + bottom_left >= 2 ) || (top_left + bottom_right >=2 ) || (top == 2) || (bottom == 2)||(top + bottom >= 2) || (right + left >= 2) || (right == 2) || (left == 2) || (bottom_right == 2) || (bottom_left == 2) || (top_right == 2) || (top_left == 2)))
		{
				return true;
		}else if((ptr == 2 ) && !((top_right + bottom_left >= 2) || (top_left + bottom_right >=  2) ||  (top == 2) || (bottom == 2) ||(top + bottom >= 2) || (right + left >= 2) || (right == 2) || (left == 2) || (bottom_right == 2) || (bottom_left == 2) || (top_right == 2) || (top_left == 2))){

			return false;
		}		
	
		return checking_validity(MatrixBoard,row,col,ptr + 1,top,bottom,right,left,top_right,top_left,bottom_right,bottom_left);
		

	}
	
	public void run() {
		try {
			gamers.add(clientSocket);
			System.out.println("I'm inside Server thread");
			outp = new DataOutputStream(clientSocket.getOutputStream());
			String line = inp.readUTF();
			System.out.println("position : " + line);
			String[] pos = line.split(",");
			int row = Integer.parseInt(pos[0]);
			int col = Integer.parseInt(pos[1]);
			MatrixBoard[row][col] = 1;
			printMatrix();
		if(checking_validity(MatrixBoard,row,col,1,0,0,0,0,0,0,0,0))
		{
			System.out.println("matched");
		}else {
			System.out.println("not yet");
		}

		while(inp != null) {

		sendtoAll(line);
		}
	
				
			
		}catch(Exception e) {
			System.out.println("reading from gamer error : " + e.getMessage());
		}
}
	public void sendtoAll(String message) {
		
	synchronized (gamers) {
		gamers.forEach((clientSocket) -> {

		try {
			DataOutputStream out1 = new DataOutputStream(clientSocket.getOutputStream());
			out1.writeUTF(message);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	});
}
	}
	public static void main(String args[])
	{
		new GameServer(7412);
		
	}
}
