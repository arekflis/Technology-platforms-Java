package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class MageController {

    @Getter @Setter
    private MageRepository repository;

    public String find(String name){
        Optional<Mage> mage = this.repository.find(name);
        if (mage.isEmpty()) return "not found";
        else{
            MageDTO mageDTO = mage.get().toMageDTO();
            return mageDTO.toString();
        }
    }

    public String delete(String name){
        try{
            MageDTO mageDTO = new MageDTO(name);
            this.repository.delete(mageDTO.getName());
            return "done";
        }
        catch(IllegalArgumentException exception){
            return "not found";
        }
    }

    public String save(String name, int level){
        try{
            MageDTO mageDTO = new MageDTO(name);
            this.repository.save(mageDTO.toMage(level));
            return "done";
        }
        catch(IllegalArgumentException exception){
            return "bad request";
        }
    }

}
