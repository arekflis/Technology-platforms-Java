import java.util.Comparator;

public class MageComparator implements Comparator<Mage> {

    @Override
    public int compare(Mage mage1, Mage mage2){
        if(mage1.getLevel() < mage2.getLevel()) return 1;
        if(mage1.getLevel() > mage2.getLevel()) return -1;

        int size = 0;
        if(mage1.getName().length() > mage2.getName().length()) size = mage2.getName().length();
        else size = mage1.getName().length();
        for(int i=0; i<size; i++){
            if(mage1.getName().charAt(i) > mage2.getName().charAt(i)) return 1;
            if(mage1.getName().charAt(i) < mage2.getName().charAt(i)) return -1;
        }
        if(mage1.getName().length() > mage2.getName().length()) return 1;
        if(mage1.getName().length() < mage2.getName().length()) return -1;

        if(mage1.getPower() < mage2.getPower()) return 1;
        if(mage1.getPower() > mage2.getPower()) return -1;
        return 0;
    }
}
