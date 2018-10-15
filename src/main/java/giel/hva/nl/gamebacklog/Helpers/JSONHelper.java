package giel.hva.nl.gamebacklog.Helpers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import giel.hva.nl.gamebacklog.Models.GameObject;

public class JSONHelper {
    private static final String FILE_NAME = "GameLibrary.json";
    private Context context;
    private File file;

    public JSONHelper(Context context){
        this.context = context;
        file = new File(context.getFilesDir(), FILE_NAME);
    }

    public void WriteToJson(List<GameObject> gameObjects) throws IOException {
        Gson gson = new Gson();
        FileWriter fw = new FileWriter(file);
        gson.toJson(gameObjects, fw);
        fw.close();
    }

    public List<GameObject> ReadJson() throws IOException {
        List<GameObject> gameObjects = new ArrayList<>();
        if(file.exists()){
            FileReader fr = new FileReader(file);
            Type type = new TypeToken<ArrayList<GameObject>>(){}.getType();
            Gson gson = new Gson();
            gameObjects = gson.fromJson(fr, type);
            fr.close();
        }
        else{
            file.createNewFile();
        }

        if(gameObjects == null){
            gameObjects = new ArrayList<>();
        }
        return gameObjects;
    }

}
