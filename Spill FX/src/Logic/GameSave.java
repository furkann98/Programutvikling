package Logic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Denne klassen blir kalt på når spillet skal lagre verdier.
 *
 *
 */
public class GameSave {

    //Datafelt
    public  PrintWriter saveFile;
    private static final String LINE = "\r\n";

    /**
     *  lager en fil.
     *
     * @param fileName navnet på filen
     * @throws IOException throws IOException
     */
    public void makeFile(File fileName) throws IOException{
        saveFile = new PrintWriter(fileName, "UTF-8");
    }

    /**
     *Save Metode
     *
     * Her lagres verdiene til Spilleren.
     * Hvor mange liv den har,
     * Poeng score,
     * Hvilken Wave man er på,
     * level på PowerLevel.
     *
     *
     * @param player verdier for spiller
     * @param waveNumber
     */
    public void save(Player player, int waveNumber){
        String life = String.valueOf(player.getLives());
        String score = String.valueOf(player.getScore());
        String wave = String.valueOf(waveNumber);
        String power = String.valueOf(player.getPowerLevel());

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
