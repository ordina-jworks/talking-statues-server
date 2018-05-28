package be.ordina.talkingstatues;

import be.ordina.talkingstatues.Monument.Model.Information;
import be.ordina.talkingstatues.Monument.Model.Language;
import be.ordina.talkingstatues.Monument.Model.Monument;
import be.ordina.talkingstatues.Monument.MonumentRepository;
import com.sun.tools.javac.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TalkingStatuesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkingStatuesApplication.class, args);
	}

	@Bean
	CommandLineRunner onStartup(MonumentRepository monumentRepository) {
		return args -> {
			monumentRepository.deleteAll();
			Monument monument1 = new Monument(List.of(new Information(Language.NL,"Antoon van Dyck","...",null)),1.1,1.2,"Meir-Leysstraat-Jezusstraat");
			Monument monument2 = new Monument(List.of(new Information(Language.NL,"Hendrik Conscience","...",null)),1.1,1.2,"Hendrik Conscienceplein");

			monumentRepository.save(monument1);
			monumentRepository.save(monument2);
		};
	}
}

