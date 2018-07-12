/**
 * Copyright (C) 2018, Manjot Sidhu <manjot.techie@gmail.com>
 */
package com.org.manjotsidhu.tictactoe;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import com.github.manjotsidhu.beautifyjson.beautifyjson;
import static com.org.manjotsidhu.tictactoe.Colors.*;
import java.util.Arrays;
import org.fusesource.jansi.AnsiConsole;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The one and only class that comprises the TicTacToe game 
 *
 * @author ManjotSidhu
 */
public class TicTacToe {

    // Board Array and board size    
    private String[][] board;
    private int boardSize;

    // Events Array
    ArrayList events = new ArrayList();

    private String[] playerChar;
    private String[] playerColor;

    // in-game params
    private String turnChar;
    private String turnColor;
    private int head;
    private boolean doNextMove = true;
    private final int pattern = 3;
    private boolean restart = false;
    private boolean doJsonImport = true;
    private boolean doJsonExport = false;

    // Moves data
    ArrayList<Integer> input1 = new ArrayList<>();
    ArrayList<Integer> input2 = new ArrayList<>();

    // json.simple objects
    JSONObject PlayingData;
    JSONArray moves;
    JSONObject MainJSON;
    
    // Scanner
    Scanner scan = new Scanner(System.in);
    
    // Help Object
    help helpMe = new help();
    
    /**
     * Welcome Page of TicTacToe
     */
    public void welcome() {
        System.out.println( GREEN + "+--------------------------------------+" + RESET);
        System.out.println( GREEN + "|     Welcome to TicTacToe Game,       |" + RESET);
        System.out.println( GREEN + "| type P to Play or type H to see help |" + RESET);
        System.out.println( GREEN + "+--------------------------------------+" + RESET);
        
        char in = scan.next(".").charAt(0);
        if(in == 'H') {
            helpMe.printString("TicTacToe");
            welcome();
        } else if(in == 'P') {
            System.out.println(RED_BG + WHITE + "Lets Play !!!" + RESET);
        } else {
            System.out.println("P to Play or H to help!");
            welcome();
        }
    }
    
    /**
     * Adds help of all the commands player needs to know about it
     */
    public void newHelp() {
        helpMe.addToHelp("TicTacToe", "TicTacToe is a game between 2 players, the player who succeeds in placing three of their marks in a horizontal, vertical, or diagonal row wins the game");
        helpMe.addToHelp("Game parameters(shortened as params)", "Usually data of all the players like colors, characters amd moves ");
        helpMe.addToHelp("Json", "A commonly used format to store game parameters");
        helpMe.addToHelp("Import game params", "Imports saved game params ONLY in a Json format");
        helpMe.addToHelp("Export game params", "Export/Save game in a Json format in order to continue/replay your game");
        helpMe.addToHelp("Size/Order", "The square dimensions of tictactoe board as a number which can be in the range of 3 to 9 inclusive");
        helpMe.addToHelp("Number Of Players", "Number of players who wants to play this game which should be always less than 1");
        helpMe.addToHelp("Character of player", "Character to be used as a mark on the board which is always unique of a player");
        helpMe.addToHelp("Color of player", "Color to be used just for the representation of the player's turn");
        helpMe.addToHelp("Input Move", "Index of the tictactoe board starting from 1 and syntax \"X Y\" where X is the element of Xth row and Y is the element of Yth element");
    }
    
    /**
     * Get player's input to import JSON file
     */
    public void askJsonImport() {
        System.out.print(GREEN + "Do you want to load game params from Json:[Y/n] " + RESET);
        char in = scan.next(".").charAt(0);
        switch (in) {
            case 'Y':
                this.doJsonImport = true;
                break;
            case 'n':
                this.doJsonImport = false;
                break;
            case 'h':
                helpMe.printString("Game parameters(shortened as params)");
                helpMe.printString("Json");
                helpMe.printString("Import game params");
                askJsonImport();
            default:
                System.out.println(RED + "Enter correct response" + RESET);
                askJsonImport();
        }
    }

    /**
     * Get player's input to export JSON file
     */
    public void askJsonExport() {
        System.out.print(GREEN + "Do you want to export game params to Json:[Y/n] " + RESET);
        char out = scan.next(".").charAt(0);
        switch (out) {
            case 'Y':
                this.doJsonExport = true;
                break;
            case 'n':
                this.doJsonExport = false;
                break;
            case 'h':
                helpMe.printString("Export game params");
                askJsonExport();
            default:
                System.out.println(RED + "Enter correct response" + RESET);
                askJsonExport();
                break;
        }
    }
    
    /**
     * initialize necessary JSON stuff
     */
    public void jsonInit() {

        PlayingData = new JSONObject();
        moves = new JSONArray();
        MainJSON = new JSONObject();

        JSONObject Board = new JSONObject();
        Board.put("Size X", this.boardSize);
        Board.put("Size Y", this.boardSize);

        JSONObject Players = new JSONObject();
        Players.put("Count", this.playerChar.length);

        JSONArray PlayersData = new JSONArray();
        for (int i = 0; i < this.playerChar.length; i++) {
            JSONObject n = new JSONObject();
            n.put("color", this.playerColor[i]);
            n.put("character", this.playerChar[i]);
            PlayersData.add(n);
        }

        Players.put("PlayersData", PlayersData);
        MainJSON.put("Board", Board);
        MainJSON.put("Players", Players);
    }

    /**
     * Reads Json input file, parses it to a String and sets
     * variables acc to their superclass variables
     *
     * @throws FileNotFoundException if <b>input.json</b> file is not found
     * @throws IOException if any I/O error occurs
     * @throws org.json.simple.parser.ParseException if json.simple fails to
     * parse to JSONObjects and JSONArrays
     */
    public void jsonRead() throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        System.out.println(GREEN + "Enter File Location (with file extension): " + RESET);
        Object obj = parser.parse(new FileReader(scan.next()));

        JSONObject myJson = (JSONObject) obj;
        JSONObject boardJson = (JSONObject) myJson.get("Board");

        JSONObject PlayingDataJson = (JSONObject) myJson.get("PlayingData");
        JSONArray movess = (JSONArray) PlayingDataJson.get("Moves");

        ArrayList<Integer> input1Json = new ArrayList<>();
        ArrayList<Integer> input2Json = new ArrayList<>();

        for (int i = 0; i < movess.size(); i++) {
            JSONObject temp = (JSONObject) movess.get(i);
            input1Json.add(Math.toIntExact((Long) temp.get("row")));
            input2Json.add(Math.toIntExact((Long) temp.get("column")));
        }

        JSONObject players = (JSONObject) myJson.get("Players");
        int count = Integer.parseInt(players.get("Count").toString());

        JSONArray data = (JSONArray) players.get("PlayersData");

        String[] playerCharJson = new String[count];
        String[] playerColorJson = new String[count];

        for (int j = 0; j < count; j++) {
            JSONObject insideData = (JSONObject) data.get(j);
            playerCharJson[j] = (String) insideData.get("character");
            playerColorJson[j] = (String) insideData.get("color");
        }

        this.boardSize = Integer.parseInt(boardJson.get("Size X").toString());
        this.input1 = input1Json;
        this.input2 = input2Json;
        this.playerChar = playerCharJson;
        this.playerColor = playerColorJson;
    }

    /**
     * Inputs size/order of board array and initialize the array
     */
    public void setSize() {
        System.out.print(GREEN + "Enter Size/Order: " + RESET);

        this.boardSize = scan.nextInt();

        board = new String[boardSize][boardSize];
    }

    /**
     * Inputs number of players playing this game
     */
    public void setUsers() {
        System.out.print(GREEN + "Number of players: " + RESET);
        int players = scan.nextInt();
        validateUsers(players);
        playerChar = new String[players];
        playerColor = new String[players];

        this.head = 0;
    }
    
    /**
     * Validate number of players from by player
     * 
     * @param i number of players from Scanner
     */
    public void validateUsers(int i) {
        int temp = this.boardSize-1;
        if(i < 1 || i > temp) {
            System.out.println(RED + "Number of players should not be less than 1"
                + " and more than " + temp + RESET);
            System.out.println(RED + "Please try again" + RESET);
            setUsers();
        }
    }
    
    /**
     * Validate player's character by the following criteria:
     * <ol><li>Character must be unique of each player, give a error</li>
     * <li>If character is more than one character, give a warning</li>
     * </ol>
     * @param chr player's character to validate
     * @return true or false if character passes the above criteria
     */
    public boolean validateChars(String chr) {
        if(chr.length() > 1) {
            System.out.println(YELLOW + "NOTE: Only First Character will be assigned as your board marker" + RESET);
        } 
        if(Arrays.toString(this.playerChar).contains(chr)) {
            System.out.println(RED + "Character is already been used by some other player, Please try again." + RESET);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Inputs player-wise characters and colors to choose from
     */
    public void setUserProp() {
        for (int i = 0; i < playerChar.length; i++) {
            int n = i + 1;
            System.out.print(GREEN + "Player " + n + ", enter your name/character: " + RESET);
            String tempChar = scan.next();

            if(validateChars(tempChar)) {
                this.playerChar[i] = tempChar.substring(0, 1);
            } else {
                i--;
                continue;
            }
                        
            System.out.print(GREEN + "Player " + n + ", enter your color: " + RESET);
            this.playerColor[i] = scan.next();
        }
    }

    /**
     * Sets the player's color to the ANSI color variable referred in Colors
     * class
     */
    public void playerColors() {
        for (int i = 0; i < playerColor.length; i++) {
            switch (playerColor[i].toUpperCase()) {
                case "BLACK":
                    playerColor[i] = BLACK;
                    break;
                case "RED":
                    playerColor[i] = RED;
                    break;
                case "GREEN":
                    playerColor[i] = GREEN;
                    break;
                case "YELLOW":
                    playerColor[i] = YELLOW;
                    break;
                case "BLUE":
                    playerColor[i] = BLUE;
                    break;
                case "PURPLE":
                    playerColor[i] = PURPLE;
                    break;
                case "CYAN":
                    playerColor[i] = CYAN;
                    break;
                case "WHITE":
                    playerColor[i] = WHITE;
                    break;
                case "HELP":
                    helpMe.printString("Color of player");
                default:
                    playerColor[i] = PURPLE;
                    int player = i+1; 
                    System.out.println(RED + "Invalid color given by Player " + player + RESET);
                    System.out.println(RED + "Setting color to PURPLE" + RESET);
                    break;
            }
        }
    }

    /**
     * Runs all the methods used to fetch all the needed params
     *
     * @throws IOException
     * @throws java.io.FileNotFoundException
     * @throws org.json.simple.parser.ParseException
     */
    // Init in-game parameters
    public void init() throws IOException, FileNotFoundException, ParseException {
        newHelp();
        AnsiConsole.systemInstall();
        welcome();
        askJsonImport();
        if (this.doJsonImport) {
            jsonRead();
            this.board = new String[boardSize][boardSize];
        } else {
            setSize();
            setUsers();
            setUserProp();
            playerColors();
        }

        // Game starts with first player
        this.turnChar = playerChar[0];
        this.turnColor = playerColor[0];
        jsonInit();
    }

    /**
     * Validates the <b>boardSize</b> param inputted by the player
     */
    public void validateStructure() {
        if (this.boardSize < 3 || this.boardSize > 9) {
            System.out.println(RED + "TicTacToe size cannot be less than 3 and more than 9" + RESET);
            System.out.println(RED + "Please try again" + RESET);
            setSize();
        }
    }

    /**
     * Renders/Creates the main game <b>Board</b>
     */
    public void renderStructure() {
        System.out.print(new String(new char[this.boardSize]).replace("\0", GREEN_BG + BLACK + "+---"));
        System.out.println("+" + RESET);
        
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if (board[i][j] == null) {
                    System.out.print(GREEN_BG + BLACK + "| " + "  ");
                } else {
                    System.out.print(GREEN_BG + BLACK + "| " + board[i][j] + " ");
                }
            }
            System.out.println("|" + RESET);
        }

        System.out.print(new String(new char[this.boardSize]).replace("\0", GREEN_BG + BLACK + "+---"));
        System.out.println("+" + RESET);

    }

    /**
     * Validates the index params inputted from the player
     *
     * @param i Integer index at X<sup>th</sup> position
     * @param j Integer index at Y<sup>th</sup> position
     */
    public void validateInput(int i, int j) {
        if (i > boardSize - 1 || j > boardSize - 1) {
            System.out.println(RED + "Please input valid index number" + RESET);
            this.restart = true;
        } // foreground fix :( to not start indexes from 0  
        /*else if(i == 0 && j == 0) {
            System.out.println(RED + "Invalid index, indexes starts from 1(inclusive)" + RESET);
            this.restart = true;
        }*/ else if (board[i][j] != null) {
            // foreground fix :( to not start indexes from 0
            int fix1 = i + 1;
            int fix2 = j + 1;
            System.out.println(RED + fix1 + " " + fix2 + " index already has " + board[i][j] + " value." + RESET);
            this.restart = true;
        } else {
            this.restart = false;
        }
    }

    /**
     * Adds values after the Last Element of Events Array
     *
     * @param x X<sup>th</sup> index of Board (Starting from 0)
     * @param y Y<sup>th</sup> index of Board (Starting from 0)
     * @param z Player Character
     * @param q Event type, 0 - General Value, 1 - Value from Undo, 2 - Value
     * from Redo
     */
    public void newEvent(int x, int y, String z, int q) {
        events.add(new ArrayList());
        ((ArrayList) events.get(events.size() - 1)).add(x);
        ((ArrayList) events.get(events.size() - 1)).add(y);
        ((ArrayList) events.get(events.size() - 1)).add(z);
        ((ArrayList) events.get(events.size() - 1)).add(q);
    }

    /**
     * Undo last move by removing last element from the <b>Board</b> and <b>Moves</b>
     * Array and adding a new event in events array
     */
    public void doUndo() {
        newEvent((Integer)((ArrayList) events.get(events.size() - 1)).get(0),(Integer) ((ArrayList) events.get(events.size() - 1)).get(1),(String)((ArrayList) events.get(events.size() - 1)).get(2), 1);
        this.board[this.input1.get(this.input1.size()-1)-1][this.input2.get(this.input2.size()-1)-1] = null;
        // foreground fix :( to not start indexes from 0 by adding -1 ^
        this.input1.remove(this.input1.size()-1);
        this.input2.remove(this.input2.size()-1);
        
        this.restart = true;
        prevTurn();
    }
    
    /**
     * Redo last move by getting last value from events and putting to <b>Board</b>
     * and <b>Moves</b> array
     */
    public void doRedo() {
        int x = (Integer) ((ArrayList) events.get(this.events.size()-1)).get(0);
        int y = (Integer) ((ArrayList) events.get(this.events.size()-1)).get(1);
        String getChar = (String) ((ArrayList) events.get(this.events.size()-1)).get(2);
        this.board[x][y] = getChar;
        input1.add(x+1);
        input2.add(y+1);
        // foreground fix :( to not start indexes from 0 by adding +1 ^
        
        newEvent(x, y, getChar, 2);
        this.restart = true;
        nextTurn();
    }

    /**
     * Simply prints the <i> make your move </i> to System.out console
     */
    public void askInput() {
        System.out.printf("%sPlayer %s, make your move: %s \n", turnColor, this.turnChar, RESET);
    }

    /**
     * Sets values inputted from the player to the main board
     *
     * @param i Integer index at i<sup>th</sup>row
     * @param j Integer index at j<sup>th</sup>column
     */
    public void setValue(int i, int j) {
        board[i][j] = this.turnChar;
    }

    /**
     * Updates <b>current</b> character and color to next player's turn
     * accordingly
     */
    public void nextTurn() {

        if (head == playerChar.length - 1) {
            head = 0;
        } else {
            head++;
        }
        this.turnChar = playerChar[head];
        this.turnColor = playerColor[head];
    }
    
    /**
     * Updates <b>current</b> character and color to previous player's turn
     * accordingly
     */
    public void prevTurn() {

        if (head == 0) {
            head = playerChar.length - 1;
        } else {
            head--;
        }
        this.turnChar = playerChar[head];
        this.turnColor = playerColor[head];
    }

    /**
     * Main game algorithm used to validate the goal pattern of the Player
     *
     * @param i1 Integer index at X<sup>th</sup> position
     * @param i2 Integer index at Y<sup>th</sup> position
     */
    public void logic(int i1, int i2) {
        // my bad :(
        int result1 = 0, result2 = 0, result3 = 0, result4 = 0, result5 = 0,
            result6 = 0;

        for (int i = 0; i < this.boardSize; i++) {
            // checks if elements along column are equal
            if (board[i][i2] == this.turnChar) {
                result1++;
                if (result1 == this.pattern) {
                    this.doNextMove = false;
                    win();
                    break;
                }
            } else {
                result1 = 0;
            }

            // checks if elements along row are equal
            if (board[i1][i] == this.turnChar) {
                result2++;
                if (result2 == this.pattern) {
                    this.doNextMove = false;
                    win();
                    break;
                }
            } else {
                result2 = 0;
            }

            // checks if elements along primary diagonal are equal
            if (board[i][i] == this.turnChar) {
                result3++;
                if (result3 == this.pattern) {
                    this.doNextMove = false;
                    win();
                    break;
                }
            } else {
                result3 = 0;
            }

            // checks if elements along secondary diagonal are equal
            for (int j = 0; j < this.boardSize; j++) {
                if (i + j == this.boardSize - 1) {
                    if (board[i][j] == this.turnChar) {
                        result4++;
                        if (result4 == this.pattern) {
                            this.doNextMove = false;
                            win();
                            break;
                        }
                    } else {
                        result4 = 0;
                    }
                }
            }

            if (!this.doNextMove) {
                break;
            }

            // checks if elements along the primary diagonal of the index are equal
            for (int j = 0; j < this.boardSize; j++) {
                if (i + j == i1 + i2) {
                    if (board[i][j] == this.turnChar) {
                        result5++;
                        if (result5 == this.pattern) {
                            this.doNextMove = false;
                            win();
                            break;
                        }
                    } else {
                        result5 = 0;
                    }
                }
            }

            if (!this.doNextMove) {
                break;
            }

            // checks if elements along the secondary diagonal of the index are equal
            for (int j = 0; j < this.boardSize; j++) {
                if (i + j == Math.abs(i1 - i2)) {
                    if (board[i][j] == this.turnChar) {
                        result6++;
                        if (result6 == this.pattern) {
                            this.doNextMove = false;
                            win();
                            break;
                        }
                    } else {
                        result6 = 0;
                    }
                }
            }
            if (!this.doNextMove) {
                break;
            }
        }
    }

    /**
     * Initializes Json objects and updates params on every input
     *
     * @param x Integer index at X<sup>th</sup> position
     * @param y Integer index at Y<sup>th</sup> position
     */
    public void jsonUpdate(int x, int y) {
        x++;
        y++;
        // foreground fix :( to not start indexes from 0 by adding +1 ^

        JSONObject m = new JSONObject();
        m.put("row", x);
        m.put("column", y);
        moves.add(m);

        PlayingData.put("Moves", moves);
        MainJSON.put("PlayingData", PlayingData);
    }

    /**
     * Checks if the board is full and its a draw
     * 
     * @return boolean if its draw or not
     */
    public boolean checkDraw() {
        return !Arrays.deepToString(this.board).contains("null");
    }
    
    /**
     * Prints Draw message
     */
    public void draw() {
        renderStructure();
        System.out.println(RED_BG + WHITE + "Oops! Its a draw!" + RESET);
        System.out.println(RED_BG + WHITE + "Better luck next time!" + RESET);
        if(!this.doJsonImport) {
            askJsonExport();
        }
    }
    
    /**
     * Prints the congratulations message on win
     */
    public void win() {
        renderStructure();
        System.out.println(RED_BG + WHITE + "Player " + this.turnChar + " won the Game!" + RESET);
        System.out.println(RED_BG + WHITE + "Thanks For Playing!" + RESET);
        if(!this.doJsonImport) {
            askJsonExport();
        }
    }

    /**
     * Pushes the variables to JSON object and return JSON format in a String
     * and Write to Json File
     *
     * @throws IOException
     */
    public void jsonWrite() throws IOException {
        System.out.println(GREEN + "Enter File Name" + RESET);
        String filename = scan.next();
        if(!filename.contains("json")) {
            filename += ".json";
        }
        FileWriter file = new FileWriter(filename);
        beautifyjson beautify = new beautifyjson();
        
        file.write(beautify.beautify(MainJSON.toJSONString()));
        file.flush();
    }

    /**
     * Iterates every move till winning
     *
     * @throws IOException
     * @throws java.io.FileNotFoundException
     * @throws org.json.simple.parser.ParseException
     */
    public void start() throws IOException, FileNotFoundException, ParseException {
        init();
        validateStructure();
        int move = 0;
        while (this.doNextMove) {
            renderStructure();
            askInput();
            
            if (!this.doJsonImport) {
                if(!scan.hasNextInt()) {
                    String t = scan.next();
                    switch (t) {
                        case "undo":
                            doUndo();
                            move--;
                            if (restart) {
                                continue;
                            }   
                            break;
                        case "redo":
                            doRedo();
                            move++;
                            if(restart) {
                                continue;
                            }   
                            break;
                        case "save":
                            jsonWrite();
                            this.doNextMove = false;
                            continue;
                        case "help":
                            helpMe.printString("Input Move");
                            continue;
                        default:
                            break;
                    }
                }
                
                input1.add(scan.nextInt());
                input2.add(scan.nextInt());
            }

            int x = input1.get(move) - 1;
            int y = input2.get(move) - 1;
            // foreground fix :( to not start indexes from 0 by adding -1 ^
            
            validateInput(x, y);
            if (restart) {
                move++;
                continue;
            }
            jsonUpdate(x, y);
            newEvent(x, y, this.turnChar, 0);
            setValue(x, y);
            logic(x, y);
            
            if(checkDraw() && this.doNextMove) {
                this.doNextMove = false;
                draw();
                break;
            }
            nextTurn();
            move++;
            if(this.doJsonImport) {
                if(move == input1.size()) {
                    this.doJsonImport = false;
                }
            }
        }
        if (this.doJsonExport) {
            jsonWrite();
        }
        
    }

    /**
     * Creates TicTacToe's object and runs <b>start</b> method
     *
     * @param args not required
     * @throws IOException
     * @throws java.io.FileNotFoundException
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        TicTacToe game = new TicTacToe();
        game.start();
    }

}
