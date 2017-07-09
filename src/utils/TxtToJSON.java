package utils;
/**
 * Created by Ferruvich on 30/05/2017.
 * Txt to JSON parser
 * This parses is used to construct JSON
 * for better performance and usability in project
 */

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TxtToJSON{

    public static void main(String[] args){
        String dir = new String("Instances/");
        File directory = new File(dir);
        ArrayList<File> files = new ArrayList<>(Arrays.asList(directory.listFiles()));
        for(File f : files) {
            String jsonFileName = f.getAbsolutePath().replace(".txt", ".json").replace("Instances","InstancesJSON");
            System.out.println("Creating " + jsonFileName + "...");
            File JSONFile = new File(jsonFileName);
            try {
                Scanner reader = new Scanner(f);
                Writer writer = new FileWriter(JSONFile);
                int i = 0, j = 0;
                while (reader.hasNext()) {
                    if (i == 0) {
                        writer.write("{ \n\t\"totNodes\": " + reader.nextLine() + ",\n");
                        i++;
                    } else if (i == 1) {
                        reader.nextLine();
                        i++;
                    } else if (i == 2) {
                        writer.write("\t\"totRoutes\": " + reader.nextLine() + ",\n");
                        i++;
                    } else if (i == 3){
                        String completeTspIndexes = reader.nextLine();
                        writer.write("\t\"completeTsp\": [");
                        completeTspIndexes = completeTspIndexes.replace(" ", ",");
                        if(completeTspIndexes.lastIndexOf(",") == completeTspIndexes.length()-1){
                            completeTspIndexes = completeTspIndexes.substring(0, completeTspIndexes.length()-1);
                        }
                        writer.write(completeTspIndexes);
                        writer.write("],\n");
                        i++;
                    } else if(i == 4){
                        String lineHaulTspIndexes = reader.nextLine();
                        writer.write("\t\"lineHaulTsp\": [");
                        lineHaulTspIndexes = lineHaulTspIndexes.replace(" ", ",");
                        if(lineHaulTspIndexes.lastIndexOf(",") == lineHaulTspIndexes.length()-1){
                            lineHaulTspIndexes = lineHaulTspIndexes.substring(0, lineHaulTspIndexes.length()-1);
                        }
                        writer.write(lineHaulTspIndexes);
                        writer.write("],\n");
                        i++;
                    } else if (i == 5){
                        String backHaulTspIndexes = reader.nextLine();
                        writer.write("\t\"backHaulTsp\": [");
                        backHaulTspIndexes = backHaulTspIndexes.replace(" ", ",");
                        if(backHaulTspIndexes.lastIndexOf(",") == backHaulTspIndexes.length()-1){
                            backHaulTspIndexes = backHaulTspIndexes.substring(0, backHaulTspIndexes.length()-1);
                        }
                        writer.write(backHaulTspIndexes);
                        writer.write("],\n");
                        i++;
                    } else {
                        String line = reader.nextLine();
                        String[] splitted = line.split("   ");
                        if (j == 0) {
                            writer.write("\t\"maxCapacity\": " + splitted[3] + ",\n");
                            writer.write("\t\"nodes\": [\n");
                        }
                        writer.write("\t{\n\t\t\"index\": " + j + ",\n");
                        writer.write("\t\t\"xcoord\": " + splitted[0] + ",\n");
                        writer.write("\t\t\"ycoord\": " + splitted[1] + ",\n");
                        if (splitted.length == 4) {
                            writer.write("\t\t\"type\": \"Warehouse\"" + ",\n");
                            writer.write("\t\t\"capacity\": " + 0 + " \n\t},\n");
                        } else {
                            if (Integer.parseInt(splitted[2]) == 0) {
                                writer.write("\t\t\"type\": \"Backhaul\"" + ",\n");
                                writer.write("\t\t\"capacity\": " + splitted[3] + " \n\t},\n");
                            } else if (Integer.parseInt(splitted[3]) == 0) {
                                writer.write("\t\t\"type\": \"Linehaul\"" + ",\n");
                                writer.write("\t\t\"capacity\": " + splitted[2] + " \n\t}");
                                if (reader.hasNext())
                                    writer.write(",\n");
                            }
                        }
                        j++;
                    }
                }
                writer.write("\n\t]\n}");
                writer.flush();
                reader.close();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
