package be.ordina.talkingstatues.config;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static be.ordina.talkingstatues.config.Configuration.GOOGLE_API_KEY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ConfigurationControllerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ConfigurationService configurationService;
    @InjectMocks
    private ConfigurationController configurationController;

    @Test
    public void getGoogleApiKey() {
        Configuration expected = new Configuration(GOOGLE_API_KEY, "value");
        when(configurationService.getConfigurationById(GOOGLE_API_KEY)).thenReturn(expected);

        Configuration actual = configurationController.getGoogleApiKey();

        assertEquals(expected, actual);
    }
}