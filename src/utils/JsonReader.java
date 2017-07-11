package utils;

import com.google.gson.Gson;
import core.TSPInstance;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class JsonReader {

    private static JsonReader instance = null;
    private Gson gson;

    private JsonReader(){
        gson = new Gson();
    }

    public static JsonReader getInstance(){
        if(instance == null){
            instance = new JsonReader();
            return instance;
        }
        return instance;
    }

    public TSPInstance getSpecifications(String fileName){
        TSPInstance tspInstance = new TSPInstance();
        try {
            Reader fileReader = new FileReader(new File(fileName));
            tspInstance = gson.fromJson(fileReader, TSPInstance.class);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            return tspInstance;
        }
    }
}
