package edu.neu.coe.info6205.util;

//import com.sun.javafx.tk.FontMetrics;
//import com.sun.javafx.tk.Toolkit;
import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.graph.Graph;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.io.*;
public class ReadDataFromCSV {


    public static Graph readData(String fileName, Graph graph, double width, double height) {
        try {
            InputStream inputStream = ReadDataFromCSV.class.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            reader.readLine();
            int i = 0;
            while ((line = reader.readLine()) != null && i <  graph.getSize()) {
                String[] fields = line.split(",");
                i++;
                double longitude = Double.parseDouble(fields[1]);
                double latitude = Double.parseDouble(fields[2]);
                double x = longitudeToX(longitude, width);
                double y = latitudeToY(latitude, height);
                graph.addNode(new Node(fields[0], latitude, longitude,x,y));

            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return graph;
    }

    public static double longitudeToX(double longitude, double canvasWidth) {
        double xFactor = canvasWidth / (0.224117 +0.477183);
        return xFactor * (longitude +0.477183);
    }

    public static double latitudeToY(double latitude, double canvasHeight) {
        double yFactor = canvasHeight / (51.670564 - 51.338396);
        return canvasHeight - yFactor * (latitude - 51.338396);
    }

}
