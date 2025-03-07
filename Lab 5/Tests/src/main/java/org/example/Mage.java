package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Mage {

    @Id
    @Getter @Setter
    private String name;

    @Getter @Setter
    private int level;

    public MageDTO toMageDTO(){
        return new MageDTO(this.getName());
    }

}
