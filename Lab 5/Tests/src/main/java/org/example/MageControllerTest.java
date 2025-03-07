package org.example;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class MageControllerTest extends BaseMageControllerTest{

    @Mock
    private MageRepository mageRepository;
    private MageController mageController;

    @BeforeEach
    public void init(){
        this.mageController = new MageController(this.mageRepository);
    }

    @Test
    public void testFindNonExistingMage(){
        MageDTO mDTO = new MageDTO("Gandalf");
        Mockito.when(this.mageRepository.find(mDTO.getName())).thenReturn(Optional.empty());

        String result = this.mageController.find(mDTO.getName());

        Assert.assertEquals("not found", result);
    }

    @Test
    public void testFindExistingMage(){
        MageDTO mDTO = new MageDTO("Gandalf");
        Mockito.when(this.mageRepository.find(mDTO.getName())).thenReturn(Optional.of(mDTO.toMage(0)));

        String result = this.mageController.find(mDTO.getName());

        Assert.assertEquals(mDTO.toString(), result);
    }

    @Test
    public void testDeleteNonExistingMage(){
        MageDTO mDTO = new MageDTO("Gandalf");
        Mockito.doThrow(IllegalArgumentException.class).when(this.mageRepository).delete(Mockito.any());

        String result = this.mageController.delete(mDTO.getName());

        Assert.assertEquals("not found", result);
    }

    @Test
    public void testDeleteExistingMage(){
        MageDTO mDTO = new MageDTO("Gandalf");
        Mockito.doNothing().when(this.mageRepository).delete(mDTO.getName());

        String result = this.mageController.delete(mDTO.getName());

        Assert.assertEquals("done", result);

    }

    @Test
    public void testSaveNewMage(){
        MageDTO mDTO = new MageDTO("Gandalf");
        Mockito.doNothing().when(this.mageRepository).save(mDTO.toMage(0));

        String result = this.mageController.save(mDTO.getName(), 0);

        Assert.assertEquals("done", result);
    }

    @Test
    public void testSaveExistingMage(){
        MageDTO mDTO = new MageDTO("Gandalf");
        Mockito.doThrow(IllegalArgumentException.class).when(this.mageRepository).save(Mockito.any());

        String result = this.mageController.save(mDTO.getName(), 0);

        Assert.assertEquals("bad request", result);
    }

}
