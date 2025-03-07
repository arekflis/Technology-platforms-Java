package org.example;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Tower {

    @Id
    private String name;

    private int height;

    @OneToMany(mappedBy = "tower")
    private List<Mage> mages;

    public Tower(){

    }

    public Tower(String name, int height) {
        this.name = name;
        this.height = height;
        this.mages = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Mage> getMages() {
        return mages;
    }

    public void setMages(List<Mage> mages) {
        this.mages = mages;
    }

    public void addToList(Mage m){
        this.mages.add(m);
    }

    public void removeFromList(Mage m) { this.mages.remove(m); }

    @Override
    public String toString(){
        return ("Tower: {name - " + this.name + ", height - " + this.height + "}");
    }
}
