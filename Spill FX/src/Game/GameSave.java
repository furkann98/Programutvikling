package Game;

import java.io.IOException;
import java.io.PrintWriter;

public class GameSave {

    //Datafelt
    public  PrintWriter saveFile;
    private static final String LINE = "\r\n";

    public void makeFile(String fileName) throws IOException{
        saveFile = new PrintWriter(fileName, "UTF-8");
    }

    public void save(Player player, int waveNumber){
        String life = String.valueOf(player.getLives());
        String score = String.valueOf(player.getScore());
        String wave = String.valueOf(waveNumber);
        String power = String.valueOf(player.getPower());

        System.out.println(life);
        System.out.println(score);
        System.out.println(wave);

        saveFile.write(life);
        saveFile.write(LINE);
        saveFile.write(score);
        saveFile.write(LINE);
        saveFile.write(wave);
        saveFile.write(LINE);
        saveFile.write(power);
        saveFile.close();
    }



}
