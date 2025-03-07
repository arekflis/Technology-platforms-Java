import java.util.*;

public class Mage implements Comparable<Mage>{
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    private MageComparator mageComparator;

    Mage(String name, int level, double power, int parameter, MageComparator mageComparator){
        this.name = name;
        this.level = level;
        this.power = power;
        this.mageComparator = mageComparator;
        if(parameter==0) this.apprentices = new HashSet<>();
        if(parameter == 1) this.apprentices = new TreeSet<>();
        if(parameter == 2) this.apprentices = new TreeSet<>(mageComparator);
    }

    public MageComparator getMageComparator() {
        return mageComparator;
    }

    public void setMageComparator(MageComparator mageComparator) {
        this.mageComparator = mageComparator;
    }

    public void AddToSet(Mage obj){
        this.apprentices.add(obj);
    }

    public Set<Mage> getApprentices() {
        return apprentices;
    }

    public void setApprentices(Set<Mage> apprentices) {
        this.apprentices = apprentices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void printSet(int wciecia, Mage obj){
        String text = "";
        for(int i=0; i<wciecia; i++){
            text = text + ("-");
        }
        System.out.println(text + obj.toString());
        wciecia++;
        for (Mage mag : obj.getApprentices()){
            printSet(wciecia, mag);
        }
    }

    public void addToMap(Map<Mage, Integer> map, Mage obj){
        map.put(obj, obj.hashCode());
        for(Mage o : obj.getApprentices()){
            obj.addToMap(map, o);
        }
    }


    public Map<Mage, Integer> createMap(int parameter){
        Map<Mage, Integer> map;
        if(parameter == 0) map = new HashMap<>();
        else if(parameter == 1) map = new TreeMap<>();
        else map = new TreeMap<>(this.getMageComparator());
        this.addToMap(map, this);
        return map;
    }


    @Override
    public String toString(){
        String text;
        text = ("Mage{name='") + this.getName() + ("', level=") + this.getLevel() + (", power=") + this.getPower() + ("}");
        return text;
    }


    @Override
    public int hashCode(){
        return this.apprenticesNumber();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        Mage o = (Mage) obj;
        if(this.hashCode() == o.hashCode() && this.compareTo(o) == 0) return true;
        return false;
    }

    @Override
    public int compareTo(Mage o) {
        int size = 0;
        if(this.getName().length() > o.getName().length()) size = o.getName().length();
        else size = this.getName().length();
        for(int i=0; i<size; i++){
            if(this.getName().charAt(i) > o.getName().charAt(i)) return 1;
            if(this.getName().charAt(i) < o.getName().charAt(i)) return -1;
        }
        if(this.getName().length() > o.getName().length()) return 1;
        if(this.getName().length() < o.getName().length()) return -1;

        if(this.getLevel() < o.getLevel()) return 1;
        if(this.getLevel() > o.getLevel()) return -1;

        if(this.getPower() < o.getPower()) return 1;
        if(this.getPower() > o.getPower()) return -1;
        return 0;
    }

    public int apprenticesNumber(){
        int count = this.getApprentices().size();

        for (Mage o : this.getApprentices()){
            count = count + o.apprenticesNumber();
        }

        return count;
    }
}
