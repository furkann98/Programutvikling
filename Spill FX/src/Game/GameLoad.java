package Game;


import Controller.GamePanelController;
import com.sun.xml.internal.fastinfoset.util.StringArray;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


//import javax.swing.filechooser.FileNameExtensionFilter;


public class GameLoad {



/*

    public List<String> stringArray;
    public Path p;
    public List<String> list;


    public List<String> liveValues;
    public List<String> scoreValues;
    public List<String> waveValues;

    public void readFile(String filepath) throws IOException {

        p = Paths.get(filepath);
        String minPath = "src/test.txt";
        p = Paths.get(minPath);

        stringArray = (Files.readAllLines(p));

        int delimiterForLivesIndex = 0;
        int delimiterForScoresIndex = 0;
        int delimiterForWavesIndex = 0;
        int delimiterEnd = 0;


        liveValues = new ArrayList<>();
        scoreValues = new ArrayList<>();
        waveValues = new ArrayList<>();

        for(int i=0; i < stringArray.size(); i++){
            if (stringArray.get(i).contains("Lives Values")){
                delimiterForLivesIndex = i;
                System.out.println("lives val index " + i);
            }
            if (stringArray.get(i).contains("Score Values")){
                delimiterForScoresIndex = i;
                System.out.println("lives val index " + i);
            }
            if (stringArray.get(i).contains("Wave Values")){
                delimiterForWavesIndex = i;
                System.out.println("lives val index " + i);
            }

        }

        for(int i=++delimiterForLivesIndex; i < delimiterForScoresIndex;i++){
            liveValues.add(stringArray.get(i));
        }

        for(int i=++delimiterForScoresIndex; i < delimiterForWavesIndex;i++){
            scoreValues.add(stringArray.get(i));
        }
        for(int i=++delimiterForWavesIndex; i < delimiterEnd;i++){
            waveValues.add(stringArray.get(i));
        }

       for (String s: scoreValues){
           System.out.println(s);
       }




    }

*/


}
