package engine;

import model.characters.*;
import model.world.Cell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Game {
    public static ArrayList<Hero> availableHeroes;
    public static ArrayList<Hero> heroes;
    public static ArrayList<Zombie> zombies;
    public static Cell[][] map;

    public static void loadHeroes(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String s = "";
        heroes = new ArrayList<>();
        while ((s = br.readLine()) != null){
            String [] a = s.split(",");
            if (Objects.equals(a[1], "FIGH")){
                Fighter x = new Fighter(a[0],Integer.parseInt(a[2]),Integer.parseInt(a[4]),Integer.parseInt(a[3]));
                heroes.add(x);
            } else if (Objects.equals(a[1], "EXP")){
                Explorer x = new Explorer(a[0],Integer.parseInt(a[2]),Integer.parseInt(a[4]),Integer.parseInt(a[3]));
                heroes.add(x);
            }else{
                Medic x = new Medic(a[0],Integer.parseInt(a[2]),Integer.parseInt(a[4]),Integer.parseInt(a[3]));
                heroes.add(x);
            }
            availableHeroes = heroes;

        }
    }

}
