package org.example;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class BaseMageControllerTest {

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setup(){
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception{
        autoCloseable.close();
    }

}
