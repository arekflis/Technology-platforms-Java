import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int parametr = 1;
        String napis = args[0];
        if(napis.compareTo("brak_sortowania") == 0) parametr = 0;
        else if(napis.compareTo("naturalny_porzadek") == 0) parametr = 1;
        else if(napis.compareTo("alternatywny_porzadek") == 0) parametr = 2;
        else System.exit(0);
        MageComparator mageC = new MageComparator();

        Mage m1 = new Mage("Gandalf", 100, 99.9, parametr, mageC);
        Mage m2 = new Mage("Merlin", 50, 44.9, parametr, mageC);
        Mage m3 = new Mage("Morgana", 25, 19.9, parametr, mageC);
        Mage m4 = new Mage("Dumbledore", 4, 4.9, parametr, mageC);
        Mage m5 = new Mage("Vivi", 22, 19.9, parametr, mageC);
        Mage m6 = new Mage("Aurora", 11, 9.9, parametr, mageC);
        Mage m7 = new Mage("Sauron", 7, 5.9, parametr, mageC);
        Mage m8 = new Mage("Medivh", 46, 39.45, parametr, mageC);
        Mage m9 = new Mage("Elminster", 31, 12.872, parametr, mageC);
        Mage m10 = new Mage("Jaina", 69, 69.69696969, parametr, mageC);

        m1.AddToSet(m2);
        m1.AddToSet(m8);
        m1.AddToSet(m10);
        m2.AddToSet(m3);
        m2.AddToSet(m5);
        m3.AddToSet(m4);
        m5.AddToSet(m6);
        m5.AddToSet(m7);
        m8.AddToSet(m9);

        int wciecia = 1;
        m1.printSet(wciecia, m1);

        Map<Mage, Integer> mapa = m1.createMap(parametr);
        System.out.println("MAPA:");
        for (Map.Entry<Mage,Integer> m : mapa.entrySet()){
            Mage m0 = m.getKey();
            int value = m.getValue();
            System.out.println(m0 + " -> " + value);
        }
    }
}