package tictactoe;

import java.util.Scanner;
import static tictactoe.Colors.*;

/**
 * TODOs : 
 * 1 > Extend game to be played for more than 2 players (done) (testing)
 * 2 > Undo/Redo
 * 3 > Import and export gameplay (Probably as JSON)
 *
 * @author ManjotSidhu
 */
public class TicTacToe {

    // Parameterss
    private int ticSize;
    // ticArray predefined
    /* private int[][] ticArray = {{1,2,1,1,1,2},
                                {7,1,9,1,1,2},
                                {1,8,9,1,1,2},
                                {7,8,9,1,1,2},
                                {7,8,9,1,1,2},
                                {7,8,9,1,1,2}}; */
    private char[][] ticArray;
    
    private char[] userChar;
    private String[] userColor;

    private char turnChar;
    private String turnColor;
    private int head;
    private boolean newLevel = true;
    private final int pattern = 3;
    private boolean restart = true;

    // Scanner
    Scanner scan = new Scanner(System.in);
    
    // Set ticArray size
    public void setSize() {
        System.out.print(GREEN + "Enter Size/Order: " + RESET);

        this.ticSize = scan.nextInt();
        // ticSize predefined
        // this.ticSize = 6;

        ticArray = new char[ticSize][ticSize];
    }
    
    // Set players playing this game
    public void setUsers() {
        System.out.print(GREEN + "Number of players: " + RESET);
        int players = scan.nextInt();
        userChar = new char[players];
        userColor = new String[players];
        
        this.head = 0;
    }
    // Set Player charater and color properties
    public void setUserProp() {
        for(int i = 0; i < userChar.length; i++) {
            int n = i + 1;
            System.out.print(GREEN + "Player " + n + ", enter your character: " + RESET);
            this.userChar[i] = scan.next(".").charAt(0);
            
            System.out.print(GREEN + "Player " + n + ", enter your color: " + RESET);
            this.userColor[i] = scan.next();
        }
    }

    // Custom Player Colors
    public void userColors() {
        for(int i = 0; i < userColor.length; i++) {
            switch(userColor[i].toUpperCase()) {
                case "BLACK" : userColor[i] = BLACK; break;
                case "RED" : userColor[i] = RED; break;
                case "GREEN" : userColor[i] = GREEN; break;
                case "YELLOW" : userColor[i] = YELLOW; break;
                case "BLUE" : userColor[i] = BLUE; break;
                case "PURPLE" : userColor[i] = PURPLE; break;
                case "CYAN" : userColor[i] = CYAN; break;
                case "WHITE" : userColor[i] = WHITE; break;
            }
        }
    }
    
    // Init in-game parameters
    public void init() {
        setSize();
        setUsers();
        setUserProp();
        userColors();
        
        // Game starts with first user
        this.turnChar = userChar[0];
        this.turnColor = userColor[0];
    }
    
    // Validate array size/order values
    public void validateStructure() {
        if(this.ticSize < 3 || this.ticSize > 9) {
            System.out.println(RED + "TicTacToe size cannot be less than 3 and more than 9" + RESET);
            System.out.println(RED + "Please try again" + RESET);
            setSize();
        } 
    }

    // Render TicTacToe Structure
    public void renderStructure() {
        System.out.print(new String(new char[this.ticSize]).replace("\0", GREEN_BG + BLACK + "+---"));
        System.out.println("+" + RESET);

        for (int i = 0; i < this.ticSize; i++) {
            for (int j = 0; j < this.ticSize; j++) {
                if(ticArray[i][j] == 0) {
                    System.out.print(GREEN_BG + BLACK + "| " + "  ");
                } else {
                    System.out.print(GREEN_BG + BLACK + "| " + ticArray[i][j] + " ");
                }
            }
            System.out.println("|" + RESET);
        }

        System.out.print(new String(new char[this.ticSize]).replace("\0", GREEN_BG + BLACK + "+---"));
        System.out.println("+" + RESET);

    }
    
    // Validate Array indexes
    public void validateInput(int i, int j) {
        if(i > ticSize-1 || j > ticSize-1) {
            System.out.println(RED + "Please input valid index number" + RESET);
            this.restart = false;
        } // foreground fix :( to not start indexes from 0  
        /*else if(i == 0 && j == 0) {
            System.out.println(RED + "Invalid index, indexes starts from 1(inclusive)" + RESET);
            this.restart = false;
        }*/ else if(ticArray[i][j] != 0) {
            System.out.println(RED + i + " " + j + " index already has " + ticArray[i][j] + " value." + RESET);
            this.restart = false;
        } else {
            this.restart = true;
        }
    }

    // Ask user's input
    public void userInput() {
        System.out.printf("%sPlayer %s, make your move: %s", turnColor, this.turnChar, RESET);
    }

    // Implement Player's turn in TicTacToe
    public void setValue(int i, int j) {
        ticArray[i][j] = this.turnChar;
    }

    // Set next user's turn and color accordingly
    public void nextTurn() {

        if(head == userChar.length-1) {
            head = 0;
        } else {
            head++;
        }
        this.turnChar = userChar[head];
        this.turnColor = userColor[head];
    }

    // Game Logic 
    public void logic(int i1, int i2) {
        // my bad :/
        int result1 = 0, result2 = 0, result3 = 0, result4 = 0, result5 = 0, 
            result6 = 0;

        for (int i = 0; i < this.ticSize; i++) {
            if (ticArray[i][i2] == this.turnChar) {
                result1++;
                if (result1 == this.pattern) {
                    this.newLevel = false;
                    result();
                    break;
                }
            } else {
                result1 = 0;
            }

            if (ticArray[i1][i] == this.turnChar) {
                result2++;
                if (result2 == this.pattern) {
                    this.newLevel = false;
                    result();
                    break;
                }
            } else {
                result2 = 0;
            }

            if (ticArray[i][i] == this.turnChar) {
                result3++;
                if (result3 == this.pattern) {
                    this.newLevel = false;
                    result();
                    break;
                }
            } else {
                result3 = 0;
            }

            for (int j = 0; j < this.ticSize; j++) {
                if (i + j == this.ticSize - 1) {
                    if (ticArray[i][j] == this.turnChar) {
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

            for (int j = 0; j < this.ticSize; j++) {
                if (i + j == i1 + i2) {
                    if (ticArray[i][j] == this.turnChar) {
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

            for (int j = 0; j < this.ticSize; j++) {
                if (i + j == Math.abs(i1 - i2)) {
                    if (ticArray[i][j] == this.turnChar) {
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
        System.out.printf("%s%sPlayer %s won the game.\n", RED_BG, WHITE, turnChar);
        System.out.println(RED_BG + WHITE + "Thanks For Playing." + RESET);
    }

    // Start main game
    public void start() {
        init();
        validateStructure();
        while (this.newLevel) {
            renderStructure();
            userInput();
            int input1 = scan.nextInt();
            int input2 = scan.nextInt();
            // foreground fix :( to not start indexes from 0
            input1 = input1 - 1;
            input2 = input2 - 1;
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
