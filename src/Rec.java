
public class Rec {
//	public int top;
//	public int bottom;
//	public int right;
//	public int left;
//	public int top_right;
//	public int top_left;
//	public int bottom_right;
//	public int bottom_left;
	public void printMatrix(Integer[][] matrixBoard)
	{
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5 ;j++)
			{
		        System.out.print(matrixBoard[i][j] + " ");
			}
		    System.out.println();
		}
	}
	public void init_Matrix(Integer[][] matrixBoard)
	{
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5 ;j++)
			{
		       matrixBoard[i][j] = 0;
			}
		
		}
	}
	public boolean checking_validity(Integer[][] MatrixBoard,int row,int col,int ptr,int top,int bottom,int right,int left,int top_right,int top_left,int bottom_right,int bottom_left)
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[][] MatrixBoard = new Integer[5][5];
		Rec test = new Rec();
		test.init_Matrix(MatrixBoard);
		//MatrixBoard[0][2] = 1;
		MatrixBoard[0][0] = 1;
		//MatrixBoard[1][1] = 1;
		MatrixBoard[1][2] = 1;
		//MatrixBoard[1][3] = 1;
		//MatrixBoard[1][4] = 1;
		//MatrixBoard[2][0] = 1;
		//MatrixBoard[2][1] = 1;
		MatrixBoard[2][2] = 1;
		//MatrixBoard[3][3] = 1;
		MatrixBoard[2][4] = 1;
		MatrixBoard[3][2] = 1;
		//MatrixBoard[4][2] = 1;
		test.printMatrix(MatrixBoard);
		if(test.checking_validity(MatrixBoard,1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0)) {
			System.out.println("match");
		}else {
			System.out.println("not match");
		}
	}
	
//	System.out.println("ok");
//	System.out.println("top " + top);
//	System.out.println("bot " + bottom);
//	System.out.println("right " + right);
//	System.out.println("left " + left);
//	System.out.println("top_right " + top_right);
//	System.out.println("top_left "+ top_left);
//	System.out.println("bot_right " + bottom_right);
//	System.out.println("bot_left " + bottom_right);
//	System.out.println("top + bottom " + (top + bottom));
//	System.out.println("right + left" + (right + left));
//	System.out.println("top_right + bot_left" + (top_right + bottom_left));
//	System.out.println("top_left + bot_right" + (top_left + bottom_right));
}
