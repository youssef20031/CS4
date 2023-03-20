package engine;

import model.characters.Hero;
import model.characters.Zombie;
import model.world.Cell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Game {
    public static ArrayList<Hero> availableHeroes;
    public static ArrayList<Hero> heroes;
    public static ArrayList<Zombie> zombies;
    public static Cell[][] map;

    public static void loadHeroes(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String s = "";
        while ((s = br.readLine()) != null){
            String [] a = s.split(",");

        }
    }

}
