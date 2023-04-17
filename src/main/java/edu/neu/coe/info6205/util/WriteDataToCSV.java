package edu.neu.coe.info6205.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteDataToCSV {



    public static BufferedWriter createBufferedWriter(String csvFile){
        try {
        FileWriter fileWriter = new FileWriter(csvFile);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Sequence, Start Point, Lat , Long , End Point, Lat, Long, Distance(mtr)");
            bufferedWriter.newLine();
            return bufferedWriter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeData(String data,BufferedWriter bw){
        try {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void closeStream(BufferedWriter bw){
        try {

            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
