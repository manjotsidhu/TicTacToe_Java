package tictactoe;

import java.util.Scanner;
import static tictactoe.Colors.*;

/**
 * TODOs : 
 * 2 > dynamic size of tictactoe array (done) (testing)
 * 3 > Validations 
 * 4 > GUI 
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
    private int[][] ticArray;
    
    private int u1 = 1;
    private int u2 = 2;

    private int turn = u1;
    private boolean newLevel = true;
    private int pattern = 3;

    // Scanner
    Scanner scan = new Scanner(System.in);

    // Init game parameters
    public void init() {
        System.out.print(MSS_GREEN + "Enter Length/Order: " + MSS_RESET);

        this.ticLength = scan.nextInt();
        // ticLength predefined
        //this.ticLength = 6;

        ticArray = new int[ticLength][ticLength];

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

    // if Array isEmpty
    public boolean isEmpty() {
        int empty = 0;
        for (int i = 0; i < this.ticLength; i++) {
            for (int j = 0; j < this.ticLength; j++) {
                if (ticArray[i][j] != 0) {
                    empty++;
                }
            }
        }
        return empty == 0;
    }

    // Ask user's input
    public void userInput() {
        System.out.printf("%sUser %s, make your move: ", MSS_GREEN, turn);
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
        turn = 1;
        init();
        while (this.newLevel) {
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
