package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {

    public static final int mag = 0;
    public static final int tow = 1;
    public static final int mixed = 2;

    public static final int printingDatabase = 1;
    public static final int newTower = 2;
    public static final int newMage = 3;
    public static final int query1 = 4;
    public static final int query2 = 5;
    public static final int query3 = 6;
    public static final int deleteMage = 7;
    public static final int deleteTower = 8;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public void addingToDatabesEntities(ArrayList<Mage> mages, ArrayList<Tower> towers){
        for (Tower t: towers){
            this.entityManager.persist(t);
        }
        for(Mage m: mages){
            this.entityManager.persist(m);
        }
    }

    Database(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        this.entityManager = this.entityManagerFactory.createEntityManager();

        ArrayList<Tower> towers = new ArrayList<>();
        ArrayList<Mage> mages = new ArrayList<>();

        createNewEntities(towers, mages);
        try{
            this.entityManager.getTransaction().begin();
            addingToDatabesEntities(mages, towers);
            this.entityManager.getTransaction().commit();
        }
        catch(Exception e){
            this.entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void finish(){
        this.entityManager.close();
        this.entityManagerFactory.close();
    }

    public void createNewTowers(ArrayList<Tower> towers){
        towers.add(new Tower("Dragon Tower", 100));
        towers.add(new Tower("Tower of Enchanted Dreams", 200));
        towers.add(new Tower("Tower of Frosty Mist", 300));
        towers.add(new Tower("Wizards Tower", 400));
        towers.add(new Tower("Tower of the White Wanderer", 500));
    }

    public void createNewMages(ArrayList<Mage> mages, ArrayList<Tower> towers){
        mages.add(new Mage("Merlin", 1, towers.get(0)));
        mages.add(new Mage("Gandalf", 7, towers.get(0)));
        mages.add(new Mage("Dumbledore", 12, towers.get(0)));
        mages.add(new Mage("Saruman", 15, towers.get(1)));
        mages.add(new Mage("Elminster", 18, towers.get(1)));
        mages.add(new Mage("Medivh", 20, towers.get(1)));
        mages.add(new Mage("Raistlin Majere", 59, towers.get(2)));
        mages.add(new Mage("Zatanna", 69, towers.get(2)));
        mages.add(new Mage("Yennefer ", 120, towers.get(2)));
        mages.add(new Mage("Aaravos", 100, towers.get(3)));
        mages.add(new Mage("Szarlatan", 122, towers.get(3)));
        mages.add(new Mage("Willow Rosenberg", 101, towers.get(3)));
        mages.add(new Mage("Circe", 98, towers.get(4)));
        mages.add(new Mage("Morgana le Fay", 88, towers.get(4)));
        mages.add(new Mage("Doctor Strange", 77, towers.get(4)));
    }

    public void magesToTowers(ArrayList<Mage> mages, ArrayList<Tower> towers){
        int j = 0;
        for (int i=0; i< towers.size(); i++){
            towers.get(i).addToList(mages.get(j));
            towers.get(i).addToList(mages.get(j+1));
            towers.get(i).addToList(mages.get(j+2));
            j += 3;
        }
    }

    public void createNewEntities(ArrayList<Tower> towers, ArrayList<Mage> mages){
        createNewTowers(towers);
        createNewMages(mages, towers);
        magesToTowers(mages, towers);
    }

    public void optionals(int command){
        if(command == printingDatabase) printAll();
        if(command == newTower) addNewTower();
        if(command == newMage) addNewMage();
        if(command == query1) printMagesWitheLevelMoreThan();
        if (command == query2) printTowersWithHeightLessThan();
        if(command == query3) printMagesWithLevelFromTower();
        if(command == deleteMage) deleteMage();
        if(command == deleteTower) deleteTower();
    }

    public void functionals(int command){
        try{
            this.entityManager.getTransaction().begin();
            optionals(command);
            this.entityManager.getTransaction().commit();
        }
        catch(Exception e){
            this.entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void printing(List<Tower> towers, List<Mage> mages, int parameter){
        if(parameter == mixed || parameter == tow){
            System.out.println("Towers:");
            for (Tower t : towers) {
                System.out.println(t);
            }
        }
        if(parameter == mixed || parameter == mag){
            System.out.println("Mages:");
            for (Mage m : mages) {
                System.out.println(m);
            }
        }
    }


    public void printAll(){
        List<Tower> towers = this.entityManager.createQuery("SELECT t FROM Tower t", Tower.class).getResultList();
        List<Mage> mages = this.entityManager.createQuery("SELECT m FROM Mage m", Mage.class).getResultList();
        printing(towers, mages, mixed);
    }

    public void addNewTower(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give Tower's name:");
        String name = scanner.nextLine();
        System.out.println("Give Tower's height:");
        int height = scanner.nextInt();
        Tower tower = this.entityManager.find(Tower.class, name);
        if(tower == null){
            tower = new Tower(name, height);
            this.entityManager.persist(tower);
            System.out.println(tower + " added!");
        }
        else{
            System.out.println("Tower with this name exist!");
        }
    }

    public void addNewMage(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give Mage's name:");
        String name = scanner.nextLine();
        System.out.println("Give Tower's name:");
        String name_Tower = scanner.nextLine();
        System.out.println("Give Mage's level:");
        int height = scanner.nextInt();
        Mage mage = this.entityManager.find(Mage.class, name);
        if(mage == null){
            Tower tower = this.entityManager.find(Tower.class, name_Tower);
            if(tower == null){
                System.out.println("Tower with this name not exist!");
            }
            else{
                mage = new Mage(name, height, tower);
                tower.addToList(mage);
                this.entityManager.persist(mage);
                System.out.println(mage + " added!");
            }
        }
        else{
            System.out.println("Mage with this name exist!");
        }
    }

    public void printMagesWitheLevelMoreThan(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give minimum level:");
        int level = scanner.nextInt();
        List<Mage> mages = this.entityManager.createQuery("SELECT m FROM Mage m WHERE m.level > " + level, Mage.class).getResultList();
        if(mages.isEmpty()){
            System.out.println("No mage has a level more than " + level + "!");
        }
        else{
            System.out.println("Mages with level more than " + level + ":");
            printing(null, mages, mag);
        }
    }

    public void printTowersWithHeightLessThan(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give maximum higher:");
        int height = scanner.nextInt();
        List<Tower> towers = this.entityManager.createQuery("SELECT t FROM Tower t WHERE t.height < " + height, Tower.class).getResultList();
        if(towers.isEmpty()){
            System.out.println("No tower has a height less than " + height + "!");
        }
        else{
            System.out.println("Towers with height less than " + height + ":");
            printing(towers, null, tow);
        }
    }

    public void printMagesWithLevelFromTower(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give tower's name:");
        String tower_name = scanner.nextLine();
        System.out.println("Give minimum level:");
        int level = scanner.nextInt();
        Tower tower = this.entityManager.find(Tower.class, tower_name);
        if(tower != null){
            List<Mage> mages = this.entityManager.createQuery("SELECT m FROM Mage m JOIN Tower t ON t.name = m.tower.name WHERE m.level > " + level + " AND t.name = '" + tower.getName() + "'", Mage.class).getResultList();
            if(mages.isEmpty()){
                System.out.println("No mage from " + tower_name + " has a level more than " + level + "!");
            }
            else{
                System.out.println("Mages from " + tower_name + " with level more than " + level + ":");
                printing(null, mages, mag);
            }
        }
        else{
            System.out.println("This tower not exist!");
        }
    }

    public void deleteMage(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give Mage's name:");
        String name = scanner.nextLine();
        Mage mage = this.entityManager.find(Mage.class, name);
        if(mage != null){
            this.entityManager.remove(mage);
            mage.getTower().removeFromList(mage);
            System.out.println(mage + " removed!");
        }
        else{
            System.out.println("Mage with this name not exist!");
        }
    }

    public void deleteTower(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give Tower's name:");
        String name = scanner.nextLine();
        Tower tower = this.entityManager.find(Tower.class, name);
        if(tower != null){
            List<Mage> mages = this.entityManager.createQuery("SELECT m FROM Mage m WHERE m.tower.name = '" + name + "'", Mage.class).getResultList();
            if(!mages.isEmpty()){
                for(Mage m: mages){
                    this.entityManager.remove(m);
                }
            }
            this.entityManager.remove(tower);
            System.out.println(tower + " removed [with mages]!");
        }
        else{
            System.out.println("Tower with this name not exist!");
        }
    }
}
