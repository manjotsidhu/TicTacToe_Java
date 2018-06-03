/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Arrays;

/**
 *
 * @author ManjotSidhu
 */
public class logic {
    
    
    public static void main(String[] args) {
        int[][] ticArray = new int[3][3];
        ticArray[0][2] = 1;
        ticArray[1][1] = 1;
        ticArray[2][0] = 1;
        System.out.println("+---+---+---+");
        
        System.out.printf("| %s | %s | %s |\n",
                ticArray[0][0], ticArray[0][1], ticArray[0][2]);
        System.out.printf("| %s | %s | %s |\n",
                ticArray[1][0], ticArray[1][1], ticArray[1][2]);
        System.out.printf("| %s | %s | %s |\n",
                ticArray[2][0], ticArray[2][1], ticArray[2][2]);
        
        System.out.println("+---+---+---+");
        
        // Logic Starts Here
        int result1 = 0;
        int result2 = 0;
        int result3 = 0;
        int result4 = 0;
        int turn = 1;
        
        // Lucky value = last and latest array element index which completes the pattern
        int i1 = 0;
        int i2 = 1;
        
        for(int i = 0; i < 3; i++) {
            if(ticArray[i][i2] == turn) {
                result1++;
                    if(result1 == 3) {
                    System.out.println("You got the pattern");
                    break; }
            } else {
                result1 = 0;
            }
            
            if(ticArray[i1][i] == turn) {
                result2++;
                    if(result2 == 3) {
                    System.out.println("You got the pattern");
                    break; }
            } else {
                result2 = 0;
            }
            
            if(ticArray[i][i] == turn) {
                result3++;
                if(result3 == 3) {
                    System.out.println("You got the pattern");
                    break; }
            } else {
                result3 = 0;
            }
            
            for(int j = 0; j < 3; j++) {
                if(i+j == 2) {
                    if(ticArray[i][j] == turn) {
                        result4++;
                        if(result4 == 3) {
                            System.out.println("You got the pattern");
                            break; }
                    } else {
                        result4 = 0;
                    }
                }
            }
        }
        
    }
}
