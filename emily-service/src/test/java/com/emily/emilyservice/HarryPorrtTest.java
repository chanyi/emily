package com.emily.emilyservice;

import java.io.*;

public class HarryPorrtTest {
    public static void main(String[] args) {
        File file = new File("D:/code/emily/emily-service/src/test/java/com/emily/emilyservice/1.Harry Potter and the Sorcerer's Stone.txt");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(fileReader);
                String tempString = null;
                int line = 1;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = bufferedReader.readLine()) != null) {
                    // 显示行号
                    System.out.println("line " + line + ": " + tempString);
                    dealString(tempString);
                    line++;
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void dealString(String tempString) {
        if(tempString==null || "".equals(tempString)){
            return;
        }
        String[] strings = tempString.split(" ");
        
    }
}
