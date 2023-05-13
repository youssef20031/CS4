package engine;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.characters.*;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.characters.Hero;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Game {
    public static ArrayList<Hero> availableHeroes = new ArrayList<>();
    public static ArrayList<Hero> heroes = new ArrayList<>();
    public static ArrayList<Zombie> zombies = new ArrayList<>(10);
    public static Cell[][] map = new Cell[15][15];

    public static void loadHeroes(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String s = "";
        while ((s = br.readLine()) != null){
            String [] a = s.split(",");
            if (Objects.equals(a[1], "FIGH")){
                Fighter x = new Fighter(a[0],Integer.parseInt(a[2]),Integer.parseInt(a[4]),Integer.parseInt(a[3]));
                availableHeroes.add(x);
            } else if (Objects.equals(a[1], "EXP")){
                Explorer x = new Explorer(a[0],Integer.parseInt(a[2]),Integer.parseInt(a[4]),Integer.parseInt(a[3]));
                availableHeroes.add(x);
            }else{
                Medic x = new Medic(a[0],Integer.parseInt(a[2]),Integer.parseInt(a[4]),Integer.parseInt(a[3]));
                availableHeroes.add(x);
            }
        }
        br.close();
    }
    public static void cellAdder(int N, int J){
        Cell [] x = {new CollectibleCell(new Supply()), new CollectibleCell(new Vaccine()), new TrapCell()};
        int i = 0;
        while (i < N) {
            int a = (int) (Math.random() * map.length);
            int b = (int) (Math.random() * map.length);
            if(map[a][b] instanceof CharacterCell) {
                if (((CharacterCell) map[a][b]).getCharacter() == null) {
                    map[a][b] = x[J];
                    i++;
                }
            }
        }
    }
    public static void zombieAdder(int N){
        int i = 0;
        while (i < N) {
            int a = (int) (Math.random() * map.length);
            int b = (int) (Math.random() * map.length);
            if(map[a][b] instanceof CharacterCell) {
                if (((CharacterCell) map[a][b]).getCharacter() == null) {
                    Zombie z = new Zombie();
                    z.setLocation(new Point(a,b));
                    map[a][b] = new CharacterCell(z);
                    zombies.add(z);
                    i++;
                }
            }
        }
    }

    public static int vaccineCounter(){
        int count = 0;
        for (int i = 0; i< map.length; i++){
            for (int j = 0; j< map[i].length;j++){
                if(map[i][j] instanceof CollectibleCell) {
                    if(((CollectibleCell) map[i][j]).getCollectible() instanceof Vaccine){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static void startGame(Hero h){
        h.setLocation(new Point(0,0));
        for (int i = 0; i< map.length; i++){
            for (int j = 0; j< map[i].length;j++){
                map[i][j] = new CharacterCell(null);
            }
        }
        heroes.add(h);
        availableHeroes.remove(h);
        map[0][0] = new CharacterCell(h);
        /*cellAdder(5,0);
        cellAdder(5,1);
        cellAdder(5,2);*/
        int i = 0;
        while (i < 5) {
            int a = (int) (Math.random() * map.length);
            int b = (int) (Math.random() * map.length);
            if(map[a][b] instanceof CharacterCell) {
                if (((CharacterCell) map[a][b]).getCharacter() == null) {
                    map[a][b] = new CollectibleCell(new Supply());
                    i++;
                }
            }
        }
        int j = 0;
        while (j < 5) {
            int a = (int) (Math.random() * map.length);
            int b = (int) (Math.random() * map.length);
            if(map[a][b] instanceof CharacterCell) {
                if (((CharacterCell) map[a][b]).getCharacter() == null) {
                    map[a][b] = new CollectibleCell(new Vaccine());
                    j++;
                }
            }
        }
        int k = 0;
        while (k < 5) {
            int a = (int) (Math.random() * map.length);
            int b = (int) (Math.random() * map.length);
            if(map[a][b] instanceof CharacterCell) {
                if (((CharacterCell) map[a][b]).getCharacter() == null) {
                    map[a][b] = new TrapCell();
                    k++;
                }
            }
        }
        //zombieAdder(10);
        int f = 0;
        while (f < 10) {
            int a = (int) (Math.random() * map.length);
            int b = (int) (Math.random() * map.length);
            if(map[a][b] instanceof CharacterCell) {
                if (((CharacterCell) map[a][b]).getCharacter() == null) {
                    Zombie z = new Zombie();
                    z.setLocation(new Point(a,b));
                    ((CharacterCell) map[a][b]).setCharacter(z);
                    zombies.add(z);
                    f++;
                }
            }
        }
        /*map[0][0].setVisible(true);
        map[0][1].setVisible(true);
        map[1][0].setVisible(true);
        map[1][1].setVisible(true);*/
        illuminate(h.getLocation().x,h.getLocation().y);
    }
    public static boolean checkWin(){
        if (heroes.size() == 0){
            return false;
        }
        int count = vaccineCounter();
        int sum = 0;
        for (int i = 0; i < heroes.size(); i++){
            sum = sum + heroes.get(i).getVaccineInventory().size();
        }
        return count == 0 && sum == 0 && heroes.size() >= 5;

    }
    public static boolean checkGameOver(){
        int sum = 0;
        for (int i = 0; i < heroes.size(); i++){
            sum = sum + heroes.get(i).getVaccineInventory().size();
        }
        return checkWin() || availableHeroes.size() == 0 || heroes.size() == 0 || sum == 0 && vaccineCounter() == 0;
    }
    public static void lighter(){
        for (int i = 0; i < heroes.size(); i++){
            Hero h = heroes.get(i);
            int x = h.getLocation().x;
            int y = h.getLocation().y;
            illuminate(x,y);
        }
    }
    public static void endTurn() throws InvalidTargetException, NotEnoughActionsException {
        for (int i = 0; i< map.length; i++){
            for (int j = 0; j< map[i].length;j++){
                map[i][j].setVisible(false);
            }
        }
        zombieAdder(1);
        for (int i = 0; i < heroes.size();i++) {
            heroes.get(i).setActionsAvailable(heroes.get(i).getMaxActions());
            heroes.get(i).setTarget(null);
            heroes.get(i).setSpecialAction(false);
        }
        for (int i = 0; i < zombies.size(); i++){
            zombies.get(i).attack();
            zombies.get(i).setTarget(null);
        }


        lighter();

    }
    public static void illuminate(int X, int Y){
        map[X][Y].setVisible(true);
        if (X - 1 >= 0 && Y -1 >= 0){
            map[X-1][Y-1].setVisible(true);
        }
        if (X + 1 < 15 && Y -1 >= 0){
            map[X+1][Y-1].setVisible(true);
        }
        if (X - 1 >= 0 && Y + 1 < 15){
            map[X-1][Y+1].setVisible(true);
        }
        if (X + 1 < 15 && Y + 1 < 15){
            map[X+1][Y+1].setVisible(true);
        }
        if (X + 1 < 15){
            map[X+1][Y].setVisible(true);
        }
        if (X - 1 >= 0){
            map[X-1][Y].setVisible(true);
        }
        if (Y + 1 < 15){
            map[X][Y+1].setVisible(true);
        }
        if (Y -1 >= 0){
            map[X][Y-1].setVisible(true);
        }
    }

}
