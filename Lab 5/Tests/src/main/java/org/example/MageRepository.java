package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;


public class MageRepository {

    @Getter @Setter
    private Collection<Mage> collection;

    MageRepository(){
        this.collection = new ArrayList<>();
    }
    public Optional<Mage> find (String name){
        for (Mage m: this.collection){
            if(m.getName().equals(name)){
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }

    public void delete(String name){
        for (Mage m: this.collection){
            if(m.getName().equals(name)){
                this.collection.remove(m);
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public void save(Mage mage){
        for (Mage m: this.collection){
            if(m.getName().equals(mage.getName())){
                throw new IllegalArgumentException();
            }
        }
        this.collection.add(mage);
    }
}
