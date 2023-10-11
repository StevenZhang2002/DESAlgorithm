package com.example.bowenproject1.Service.impl;

import com.example.bowenproject1.Service.EncodeService;
import com.example.bowenproject1.utils.utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;


@Service
public class encoder implements EncodeService {
//    private int groups;
//    private String originalText;
//    private String encodeText;
//    private String key;

    private static int[]IpExchange = new int[] {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7};

    private static int[]permuteChange1 = new int[] {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4};

    private static int[]permuteChange2 = new int[] {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32};

    private static int[]IP_Reverse = new int[] {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25};

    private static int[]leftShiftTable = new int[]{
            1, 1, 2, 2, 2, 2, 2, 2,
            1, 2, 2, 2, 2, 2, 2, 1};

    private static int[]expandTable = new int[] {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1};

    private static final int[][] SBoxes = {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
                    0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
                    4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
                    15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13},
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
                    3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
                    0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
                    13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9},
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
                    13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
                    13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
                    1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12},
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
                    13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
                    10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
                    3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14},
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
                    14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
                    4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
                    11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3},
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
                    10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
                    9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
                    4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13},
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
                    13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
                    1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
                    6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12},
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
                    1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
                    7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
                    2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};

    private static int[] P_substituteTable = new int[] {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25};


    public static String IPSubstitute(String str) {
        //substitute using IP table
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < IpExchange.length; i++) {
            result.append(str.charAt(IpExchange[i]-1));
        }
        return result.toString();
    }

    public static String PSubstitute(String str) {
        //substitute using P table
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < P_substituteTable.length; i++) {
            result.append(str.charAt(P_substituteTable[i]-1));
        }
        return result.toString();
    }

    public static String IP_ReverseSubstitute(String str) {
        // substitute using IP reverse table
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < IP_Reverse.length; i++) {
            result.append(str.charAt(IP_Reverse[i]-1));
        }
        return result.toString();
    }

    public static String Extend_Substitute(String str) {
        //32->48
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <expandTable.length; i++) {
            result.append(str.charAt(expandTable[i]-1));
        }
        return result.toString();
    }

    public static String XorOperation(String str1, String str2) {
        StringBuilder result = new StringBuilder();
        //if it is the same, add '0', else add '1'
        for (int i = 0; i<str1.length(); i++) {
            //compare two string by characters
            if (Objects.equals(str1.charAt(i),str2.charAt(i))) {
                result.append("0");
            }else {
                result.append("1");
            }
        }
        return result.toString();
    }

    public static String[] splitParts(String str) {
        //this function split a single string into two even part
        String[]strList = new String[2];
        String str1 = str.substring(0,str.length()/2);
        String str2 = str.substring(str.length()/2);
        strList[0] = str1;
        strList[1] = str2;
        return strList;
    }

    public static String[] subKeyGenerate(String key) {
        // result
        String[] subKeys = new String[16];
        StringBuilder temp = new StringBuilder();
        //using PC1 to apply the exchange
        for (int index : permuteChange1) {
            temp.append(key.charAt(index - 1));
        }
        String key_permute = temp.toString();
        // split into two part
        // left part
        String C0 = key_permute.substring(0, key_permute.length() / 2);
        // right part
        String D0 = key_permute.substring(key_permute.length() / 2);
        // Generate the 16 keys
        for(int i = 0; i< leftShiftTable.length; i++) {
            C0 = utils.shiftLeft(C0, leftShiftTable[i]);
            D0 = utils.shiftLeft(D0, leftShiftTable[i]);
            String subKey_temp = C0 + D0;
            StringBuilder subKey = new StringBuilder();
            for (int idx : permuteChange2) {
                subKey.append(subKey_temp.charAt(idx-1));
            }
            subKeys[i] = subKey.toString();
        }
        return subKeys;
    }



    public static String plainTextValidation(String str) {
        //convert the str into 64 bit
        //fill to 64 bit (the multiple result of the 64 bit)
        StringBuilder binaryForm = new StringBuilder(utils.convertToBinary(str));
        while (binaryForm.length() % 64 != 0) {
            binaryForm.append("0");
        }
        return binaryForm.toString();
    }

    public static String sBoxOperation(String str) {
        //48->32
        StringBuilder result = new StringBuilder();
        //split the text into 8 groups with 6 bit
        String[]strGroup = utils.splitStrByGroup(str, 6);
        for (int i = 0 ; i < strGroup.length; i++) {
            //binary to decimal
            //take the 1th and the last place of each group and cast into binary
            int row = Integer.parseInt(strGroup[i].charAt(0) + "" + strGroup[i].charAt(5), 2);
            //take the second place to the fourth place of each group and cast into binary
            int col = Integer.parseInt(strGroup[i].substring(1, 5), 2);
            //using the index to locate the number and cast into binary form
            //(fillPos is used to fill the binary form into 4 places)
            String temp = utils.fillPos(utils.decimalToBinaryStr(SBoxes[i][row*16 + col]));
            result.append(temp);
        }
        return result.toString();
    }

    public String keyValidation(String key){
        //convert into binary form
        key = utils.convertToBinary(key);
        //check if it meets the 64 bit requirements, filling to 64 bit or substract to 64 bit
        if(key.length() < 64) {
            StringBuilder keyBuilder = new StringBuilder(key);
            while (keyBuilder.length() != 64) {
                keyBuilder.append("0");
            }
            key = keyBuilder.toString();
        } else if (key.length() > 64) {
            key = key.substring(0,64);
        }
        return key.toString();
    }


    public String DesDecrypt(String msg, String key) {
        //convert str to binary form and fill Pos/Sub length
        key = keyValidation(key);
        //convert the encoded message into binary form
        msg = utils.convertToBinary16(msg);
        //split into several parts evenly with 64 bit
        String[] msgSplit = utils.splitStrByGroup(msg, 64);
        //generate subkeys and reverse the order
        String[] tempKeys = subKeyGenerate(key);
        String[] subKeys = new String[16];
        for(int i=15;i>=0;i--){
            subKeys[15-i]=tempKeys[i];
        }
        StringBuilder result = new StringBuilder();
        //for each partition, undertaken the subEncrypt function and append to the result
        for (String msgPart : msgSplit) {
            String temp = SubEncrypt(msgPart, subKeys);
            result.append(temp);
        }
        String tempResult = result.toString();
        //since the original text may involve position filling. substract the filling position(should be the product of 8)
        int i = tempResult.length()/8;
        String finalResult = "";
        while (tempResult.substring((i-1) * 8, i * 8 ).equals("00000000")){
            i--;
        }
        //return the result
        finalResult = tempResult.substring(0,i * 8);
        //convert the result into String
        return utils.convertToString2(finalResult);
    }

    public String DesEncrypt(String msg, String key) {
        //convert str to binary form and fill Pos/Sub length
        key = keyValidation(key);
        //convert str to binary form and fill Pos
        msg = plainTextValidation(msg);
        //split into several parts evenly with 64 bit
        String[] msgSplit = utils.splitStrByGroup(msg, 64);
        //generate subkeys
        String[] subKeys = subKeyGenerate(key);
        StringBuilder result = new StringBuilder();
        //for each partition, undertaken the subEncrypt function and append to the result
        for (String msgPart : msgSplit) {
            String temp = SubEncrypt(msgPart, subKeys);
            temp = utils.convertToHex(temp);
            result.append(temp);
        }
        return result.toString();
    }



    public static String SubEncrypt(String str, String[]subKeys){
        // msg-IP substitute
        String msg_Ip = IPSubstitute(str);
        // msg split into left and right
        // 64 -> 32
        String[] msgList = splitParts(msg_Ip);
        // value initialization
        String left = msgList[0];
        String right = msgList[1];
        String leftPre = msgList[0];
        String rightPre = msgList[1];
        for (int i = 0; i < 16; i++) {
            // update the left part
            //Li = Ri-1
            left = rightPre;
            //  first extend (32->48) then take the XOR Operation
            String extendedTemp = Extend_Substitute(rightPre);
            String XorOperationTemp = XorOperation(subKeys[i], extendedTemp);
            // apply the sBoxOperation (48->32)
            String sBoxOut = sBoxOperation(XorOperationTemp);
            // P Substitute 32->32
            String pOutPut = PSubstitute(sBoxOut);
            // XOR with the Li-1 part (32)
            right = XorOperation(leftPre, pOutPut);
            rightPre = right;
            leftPre = left;
        }
        // integrate (32->64)
        String strIntegrate = right + left;
        // IP reverse substitute
        String strReverseIp = IP_ReverseSubstitute(strIntegrate);
        return strReverseIp;
    }




}
