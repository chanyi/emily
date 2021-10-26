package com.emily.emilyservice.harry;

import com.emily.emilyservice.utils.redis.RedisUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Set;

@Component
public class HarryPotterTest {

    @Autowired
    public RedisUtil redisUtil;

    private String setName = "Harry";


    public void deal() {
//        FactoryBean;
        readFile();
        getLastScoreValue();

    }
    private void getLastScoreValue(){

        System.out.println("=================================================");
        Set<String> set = redisUtil.zrangeByScore(setName,0,2);
        if(set == null){
            return;
        }
        set.stream().forEach(System.out::println);
    }

    private void dealString(String tempString) {
        if(tempString==null || "".equals(tempString)){
            return;
        }
        String[] strings = tempString.split(" ");
        for (int i = 0; i < strings.length; i++) {
            double count = redisUtil.zscore(setName,strings[i]);

            redisUtil.zadd(setName,count++,strings[i]);
        }
    }

    private void readFile(){
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
}