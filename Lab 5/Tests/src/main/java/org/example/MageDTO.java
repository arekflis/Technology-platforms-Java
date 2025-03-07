package org.example;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MageDTO {

    @Getter @Setter
    private String name;

    public Mage toMage(int level){
        return new Mage(this.getName(), level);
    }
}
