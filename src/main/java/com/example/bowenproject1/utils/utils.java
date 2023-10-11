package com.example.bowenproject1.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class utils {


    public static String decimalToBinaryStr(int num) {
        return Integer.toBinaryString(num);
    }

    //split the string into several part with the given length
    public static String[] splitStrByGroup(String str, Integer groupSize) {
        int length = str.length()/groupSize;
        String[]result = new String[length];
        for(int i=0; i*groupSize<=str.length()-1;i++) {
            String temp = str.substring(i*groupSize, (i+1)*groupSize);
            result[i] = temp;
        }
        return result;
    }


    //hex to binary
    public static String convertToBinary16(String hex) {
        StringBuilder binary = new StringBuilder();

        // iterate the char
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            // change into the related int
            int decimal = Character.digit(c, 16);
            // change int into the 4bit binary form
            String binaryByte = String.format("%4s", Integer.toBinaryString(decimal))
                    .replace(' ', '0');
            //add into the result
            binary.append(binaryByte);
        }
        return binary.toString();
    }


    //convert string into binary form
    public static String convertToBinary(String input) {
        StringBuilder binaryString = new StringBuilder();
        byte[] bytes = input.getBytes();
        for (byte b : bytes) {
            for (int i = 7; i >= 0; i--) {
                binaryString.append((b >> i) & 1);
            }
        }

        return binaryString.toString();
    }

    //shift left according to certain steps
    public static String shiftLeft(String str, Integer num) {
        int realMove = num%str.length();
        return str.substring(realMove) + str.substring(0, realMove);
    }

    //this is used for the sBoxOperation where it requires the 4bit binary form
    //this function would add the result to 4bit form
    public static String fillPos(String str) {
        if (str.length() == 4) {
            return str;
        }
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length()!=4){
            strBuilder.insert(0, "0");
        }
        str = strBuilder.toString();
        return str;
    }


    //binary to hex
    public static String convertToHex(String binary) {
        StringBuilder hex = new StringBuilder();

        // split into several parts at the length of 4
        for (int i = 0; i < binary.length(); i += 4) {
            String binaryNibble = binary.substring(i, Math.min(i + 4, binary.length()));
            //cast into hex
            int decimal = Integer.parseInt(binaryNibble, 2);
            char hexDigit = Character.forDigit(decimal, 16);
            //add to the result
            hex.append(hexDigit);
        }

        return hex.toString().toUpperCase();
    }


    public static String convertToString2(String binary) {
        StringBuilder str = new StringBuilder();

        // iterate every 8 pos
        for (int i = 0; i < binary.length(); i += 8) {
            String binaryByte = binary.substring(i, i + 8);
            //change into the int
            int decimal = Integer.parseInt(binaryByte, 2);
            //change int into the ASCII form
            str.append((char) decimal);
        }
        return str.toString();
    }







}
