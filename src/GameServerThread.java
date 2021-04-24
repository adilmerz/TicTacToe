import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameServerThread extends Thread{
	private Socket clientSocket;
	private GameServer srv;
	private PrintWriter  out;  // pour diffuser des donne au joueurs
	private Scanner in; // pour lire les les donnees des joueurs
	private int[][] MatrixBoard;
	public GameServerThread(GameServer srv,int[][] MatrixBoard) {
		this.srv = srv;
		this.MatrixBoard = MatrixBoard;
	}
	
	
}
