package tictactoe;

import java.util.Scanner;

/**
 * TODOs :
 * 1 > User defined array (done)(testing)
 * 2 > dynamic order of array
 * 3 > Validations
 * 3 > GUI
 * 4 > Web interface
 * @author ManjotSidhu
 */


public class TicTacToe {
    // Parameterss
    private int ticLength;
    private int[][] ticArray;
    // ticArray predefined
    //private int[][] ticArray = { {1,2,3,1,1},
    //                             {4,5,6,1,1},
    //                            {7,8,9,1,1},
    //                             {7,8,9,1,1},
    //                             {7,8,9,1,1}};
    private int u1 = 1;
    private int u2 = 2;

    private int turn = u1;
    private boolean newLevel = true;
    
    // Scanner
    Scanner scan = new Scanner(System.in);
    
    // Init game parameters
    public void init() {
        System.out.print("Enter Length/Order: ");
        this.ticLength = scan.nextInt();
        ticArray = new int[ticLength][ticLength];
    }
    
    // Render TicTacToe Structure
    public void renderStructure() {
        System.out.print( new String(new char[this.ticLength]).replace("\0", "+---"));
        System.out.println("+");
        
        for(int i = 0; i < this.ticLength; i++) {
            for(int j = 0; j < this.ticLength; j++) {
                System.out.print("| " + ticArray[i][j] + " ");
            }
            System.out.println("|");
        }
        
        System.out.print( new String(new char[this.ticLength]).replace("\0", "+---"));
        System.out.println("+");
        
    }
    
    // if Array isEmpty
    public boolean isEmpty() {
        int empty = 0;
        for(int i = 0; i < this.ticLength; i++) {
            for(int j = 0; j < this.ticLength; j++) {
                if(ticArray[i][j] != 0) {
                    empty++ ;
                }
            }
        }
        return empty == 0;    
    }
    
    // Ask user's input
    public void userInput() {
        System.out.printf("User %s, make your move: ",turn);
    }
    
    // Implement User's turn in TicTacToe
    public void setValue(int i, int j) {
        ticArray[i][j] = turn;
    }
    
    // Set next user's turn accordingly
    public void nextTurn() {
        turn = (turn == u1) ? u2 : u1;
    }
    
    // Game Logic 
    public void logic(int i1, int i2) {        
        // my bad :\
        int result1 = 0;
        int result2 = 0;
        int result3 = 0;
        int result4 = 0;
        
        for(int i = 0; i < this.ticLength; i++) {
            if(ticArray[i][i2] == turn) {
                result1++;
                    if(result1 == this.ticLength) {
                    this.newLevel = false;
                    result();
                    break; }
            } else {
                result1 = 0;
            }
            
            if(ticArray[i1][i] == turn) {
                result2++;
                    if(result2 == this.ticLength) {
                    this.newLevel = false;
                    result();
                    break; }
            } else {
                result2 = 0;
            }
            
            if(ticArray[i][i] == turn) {
                result3++;
                if(result3 == this.ticLength) {
                    this.newLevel = false;
                    result();
                    break; }
            } else {
                result3 = 0;
            }
            
            for(int j = 0; j < this.ticLength; j++) {
                if(i+j == this.ticLength-1) {
                    if(ticArray[i][j] == turn) {
                        result4++;
                        if(result4 == this.ticLength) {
                            this.newLevel = false;
                            result();
                            break; }
                    } else {
                        result4 = 0;
                    }
                }
            }
        }
    }
    
    // Results
    public void result() {
        System.out.printf("user %s won the game.\n", turn);
        System.out.println("Thanks For Playing.");
        renderStructure();
    }
    
    // Start main game
    public void start() {
        // Game starts with user 1
        turn = 1;
        init();
        while(this.newLevel) {
            renderStructure();
            userInput();
            int input1 = scan.nextInt();
            int input2 = scan.nextInt();
            setValue(input1, input2);
            logic(input1, input2);
            nextTurn();
        }
    }
    
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.start();
    }       
    
}
