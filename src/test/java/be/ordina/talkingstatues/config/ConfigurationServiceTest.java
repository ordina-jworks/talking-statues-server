package be.ordina.talkingstatues.config;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

import static be.ordina.talkingstatues.config.Configuration.GOOGLE_API_KEY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ConfigurationServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ConfigurationRepository configurationRepository;
    @InjectMocks
    private ConfigurationService configurationService;

    @Test
    public void getConfigurationById() {
        Configuration expected = new Configuration(GOOGLE_API_KEY, "value");
        when(configurationRepository.findById(GOOGLE_API_KEY)).thenReturn(Optional.of(expected));

        Configuration actual = configurationService.getConfigurationById(GOOGLE_API_KEY);

        assertEquals(expected, actual);
    }

    @Test
    public void getConfigurationById_notFound() {
        when(configurationRepository.findById(GOOGLE_API_KEY)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> configurationService.getConfigurationById(GOOGLE_API_KEY))
                .withMessage("Configuration with id: " + GOOGLE_API_KEY + " does not exist");
    }
}