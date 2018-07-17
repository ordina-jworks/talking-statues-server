package be.ordina.talkingstatues.util;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.io.IOException;

public class EncoderTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private GridFsTemplate gridFsTemplate;

    @Test
    public void encode() throws IOException {
        // TODO david
    }
}