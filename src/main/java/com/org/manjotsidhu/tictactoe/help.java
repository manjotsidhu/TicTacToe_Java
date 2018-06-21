/*
 * Copyright (C) 2018, Manjot Sidhu <manjot.techie@gmail.com>
 */

package com.org.manjotsidhu.tictactoe;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class help {

    // Parameters
    Map<String, String> helpMap = new TreeMap<>();
    
    // Add new elements/help commands to help
    public void addToHelp(String key,String value) {
        helpMap.put(key, value);
    }
    
    // Get elements by their keys
    public String getHelp(String key) {
        return helpMap.get(key);
    }
    
    // Pads spaces
    public void padSpace(int x) {
        System.out.print(new String(new char[x]).replace("\0", " "));
    }
    
    // Structure
    public void printString() {
        Set<String> setCodes = helpMap.keySet();
        Iterator<String> iterator1 = setCodes.iterator();
        int keyLength = 0, valLength = 0;
        
        while (iterator1.hasNext()) {
            String key = iterator1.next();
            String val = helpMap.get(key);
            
            if(key.length() > keyLength) {
                keyLength = key.length();
            }
            if(val.length() > valLength) {
                    valLength = val.length();
            }
            
        }
        
        /*if(valLength > 30) {
            valLength = 30;
        }*/
            
        Iterator<String> iterator = setCodes.iterator();

        System.out.print("+-");
        System.out.print(new String(new char[keyLength]).replace("\0", "-"));
        System.out.print("-+-");
        System.out.print(new String(new char[valLength]).replace("\0", "-"));
        System.out.println("-+");
        
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = helpMap.get(key);
            
            System.out.print("| " + key);
            
            padSpace(keyLength-key.length());
            /*if(valLength-value.length() < 0) {
                System.out.println(" : " + value.substring(0, 30) + "-|");
                System.out.print("| ");
                padSpace(keyLength);
                System.out.print(" : " + value.substring(30, value.length()));
                padSpace(valLength - value.substring(30, value.length()).length());
            } else { */
                System.out.print(" : " + value);
                padSpace(valLength-value.length());
            //}
                
                System.out.println(" |");
        }
        
        System.out.print("+-");
        System.out.print(new String(new char[keyLength]).replace("\0", "-"));
        System.out.print("-+-");
        System.out.print(new String(new char[valLength]).replace("\0", "-"));
        System.out.println("-+");
    }
    
    /**
     *  Get help from key
     */
    public void printString(String key) {
        String val = helpMap.get(key);
        
        System.out.print("+-");
        System.out.print(new String(new char[key.length()]).replace("\0", "-"));
        System.out.print("-+-");
        System.out.print(new String(new char[val.length()]).replace("\0", "-"));
        System.out.println("-+");
        
        System.out.println("| " + key + " : " + val + " |");
        
        System.out.print("+-");
        System.out.print(new String(new char[key.length()]).replace("\0", "-"));
        System.out.print("-+-");
        System.out.print(new String(new char[val.length()]).replace("\0", "-"));
        System.out.println("-+");
    }
    
    /* Testing Stuff
    public static void main(String[] args) {
        help halp = new help();
        halp.addToHelp("Manjot", "monkey");
        halp.addToHelp("Manjots", "monkeys");
        halp.addToHelp("Manjotsada", "asdamonkeys");
        halp.addToHelp("Manjotsadadaddadsa", "manjotsinghsidhu12345678901234567890");
        halp.printString("Manjot");
    } */
}
