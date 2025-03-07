package org.example;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class MageRepositoryTest {

    @Test
    public void testFindNonExistingMage(){
        MageRepository mageRepository = new MageRepository();

        Optional<Mage> mage = mageRepository.find("Rudolph");

        Assert.assertEquals(Optional.empty(), mage);
    }

    @Test
    public void testFindExistingMage(){
        MageRepository mageRepository = new MageRepository();
        mageRepository.save(new Mage("Gandalf", 10));
        mageRepository.save(new Mage("Dumbledore" , 100));

        Optional<Mage> mage = mageRepository.find("Gandalf");

        Assert.assertTrue(mage.isPresent());
        Assert.assertEquals("Gandalf", mage.get().getName());
    }

    @Test
    public void testSaveError(){
        MageRepository mageRepository = new MageRepository();
        mageRepository.save(new Mage("Gandalf", 10));
        mageRepository.save(new Mage("Dumbledore" , 100));

        Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> mageRepository.save(new Mage("Gandalf", 15)));

        Assert.assertEquals(IllegalArgumentException.class, exception.getClass());
    }

    @Test
    public void testDeleteError(){
        MageRepository mageRepository = new MageRepository();

        Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> mageRepository.delete("Rudolph"));

        Assert.assertEquals(IllegalArgumentException.class, exception.getClass());
    }
}
