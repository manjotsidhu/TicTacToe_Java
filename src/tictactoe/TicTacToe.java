package tictactoe;

import java.util.Scanner;
import static tictactoe.Colors.*;

/**
 * TODOs : 
 * 1 > ticArray to char or to generics
 * 2 > user defined characters and colors
 * 3 > more in-game color formattings
 * 3 > GUI 
 * 5 > Web interface
 *
 * @author ManjotSidhu
 */
public class TicTacToe {

    // Parameterss
    private int ticLength;
    // ticArray predefined
    /* private int[][] ticArray = {{1,2,1,1,1,2},
                                {7,1,9,1,1,2},
                                {1,8,9,1,1,2},
                                {7,8,9,1,1,2},
                                {7,8,9,1,1,2},
                                {7,8,9,1,1,2}}; */
    private char[][] ticArray;
    
    private char u1 = 'X';
    private char u2 = 'O';

    private char turn = u1;
    private boolean newLevel = true;
    private int pattern = 3;
    private boolean restart = true;

    // Scanner
    Scanner scan = new Scanner(System.in);

    // Init game parameters
    public void init() {
        System.out.print(MSS_GREEN + "Enter Size/Order: " + MSS_RESET);

        this.ticLength = scan.nextInt();
        // ticLength predefined
        //this.ticLength = 6;

        ticArray = new char[ticLength][ticLength];

    }
    
    // Validate array size/order values
    public void validateStructure() {
        if(this.ticLength < 3 || this.ticLength > 9) {
            System.out.println(MSS_RED + "TicTacToe size cannot be less than 3 and more than 9" + MSS_RESET);
            System.out.println(MSS_RED + "Please try again" + MSS_RESET);
            init();
        } 
    }

    // Render TicTacToe Structure
    public void renderStructure() {
        System.out.print(new String(new char[this.ticLength]).replace("\0", MSS_GREEN_BG + MSS_BLACK + "+---"));
        System.out.println("+");

        for (int i = 0; i < this.ticLength; i++) {
            for (int j = 0; j < this.ticLength; j++) {
                System.out.print(MSS_GREEN_BG + MSS_BLACK + "| " + ticArray[i][j] + " ");
            }
            System.out.println("|");
        }

        System.out.print(new String(new char[this.ticLength]).replace("\0", MSS_GREEN_BG + MSS_BLACK + "+---"));
        System.out.println("+" + MSS_RESET);

    }

    // if Array isEmpty (not used for now)
    /* public boolean isEmpty() {
        int empty = 0;
        for (int i = 0; i < this.ticLength; i++) {
            for (int j = 0; j < this.ticLength; j++) {
                if (ticArray[i][j] != 0) {
                    empty++;
                }
            }
        }
        return empty == 0;
    } */
    
    // Validate Array indexes
    public void validateInput(int i, int j) {
        if(i > ticLength-1 || j > ticLength-1) {
            System.out.println(MSS_RED + "Please input valid index number" + MSS_RESET);
            restart = false;
        } else if(i == 0 && j == 0) {
            System.out.println(MSS_RED + "Invalid index, indexes starts from 1(inclusive)" + MSS_RESET);
            restart = false;
        } else if(ticArray[i][j] != 0) {
            System.out.println(MSS_RED + i + " " + j + " index already has " + ticArray[i][j] + " value." + MSS_RESET);
        } else {
            restart = true;
        }
    }

    // Ask user's input
    public void userInput() {
        System.out.printf("%sUser %s, make your move: %s", MSS_GREEN, turn, MSS_RESET);
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
        // my bad :/
        int result1 = 0, result2 = 0, result3 = 0, result4 = 0, result5 = 0, 
            result6 = 0;

        for (int i = 0; i < this.ticLength; i++) {
            if (ticArray[i][i2] == turn) {
                result1++;
                if (result1 == this.pattern) {
                    this.newLevel = false;
                    result();
                    break;
                }
            } else {
                result1 = 0;
            }

            if (ticArray[i1][i] == turn) {
                result2++;
                if (result2 == this.pattern) {
                    this.newLevel = false;
                    result();
                    break;
                }
            } else {
                result2 = 0;
            }

            if (ticArray[i][i] == turn) {
                result3++;
                if (result3 == this.pattern) {
                    this.newLevel = false;
                    result();
                    break;
                }
            } else {
                result3 = 0;
            }

            for (int j = 0; j < this.ticLength; j++) {
                if (i + j == this.ticLength - 1) {
                    if (ticArray[i][j] == turn) {
                        result4++;
                        if (result4 == this.pattern) {
                            this.newLevel = false;
                            result();
                            break;
                        }
                    } else {
                        result4 = 0;
                    }
                }
            }

            for (int j = 0; j < this.ticLength; j++) {
                if (i + j == i1 + i2) {
                    if (ticArray[i][j] == turn) {
                        result5++;
                        if (result5 == this.pattern) {
                            this.newLevel = false;
                            result();
                            break;
                        }
                    } else {
                        result5 = 0;
                    }
                }
            }

            for (int j = 0; j < this.ticLength; j++) {
                if (i + j == Math.abs(i1 - i2)) {
                    if (ticArray[i][j] == turn) {
                        result6++;
                        if (result6 == this.pattern) {
                            this.newLevel = false;
                            result();
                            break;
                        }
                    } else {
                        result6 = 0;
                    }
                }
            }
        }
    }

    // Results
    public void result() {
        renderStructure();
        System.out.printf("%s%sUser %s won the game.\n", MSS_RED_BG, MSS_WHITE, turn);
        System.out.println(MSS_RED_BG + MSS_WHITE + "Thanks For Playing.");
    }

    // Start main game
    public void start() {
        // Game starts with user 1
        turn = 'X';
        init();
        validateStructure();
        while (this.newLevel) {
            renderStructure();
            userInput();
            int input1 = scan.nextInt();
            int input2 = scan.nextInt();
            validateInput(input1, input2);
            if(!restart) { continue; } 
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
