package nl.nfi.aardwolf;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.vaadin.flow.component.dependency.CssImport;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.domain.FileDelta;
import nl.nfi.aardwolf.domain.ModificationType;
import nl.nfi.aardwolf.repositories.AppRepository;

@SpringBootApplication
//The CSS imports below are purely to make sure they are available to shared-styles.css
@CssImport("./styles/shared/variables/colors.css")
@CssImport("./styles/shared/variables/sizing.css")
@CssImport("./styles/shared/layout.css")
public class AardwolfProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AardwolfProjectApplication.class, args);
	}


    @Bean
    public CommandLineRunner loadData(@Autowired final AppRepository repository) {
        // Set recurring file
        return (args) -> {
            // save a couple of apps
            repository.save(createWhatsdownApp());
            repository.save(createWhatsdownAppV2());
            repository.save(createSplatify());
        };
    }

    private FileDelta getRecurringModifiedFile() {
        return new FileDelta(ModificationType.MODIFIED, "type_1/subtype_2", "/opt/test/wd-1.0/modifiedfile");
    }

    private App createWhatsdownApp() {
        final App app = new App("WhatsDown", "Fakebook", "1.0", "A messaging application");
        final ArrayList<Experiment> experiments = new ArrayList<>();
        final Experiment experiment = new Experiment();
        experiment.setUserName("testuser");
        experiment.setDeviceDetails("Nokia 3310");
        experiment.setOperatingSystem("Android 9");
        experiment.setDateTimePerformed(LocalDateTime.now().minusDays(1));
        experiments.add(experiment);

        final List<FileDelta> changes = new ArrayList<>();
        changes.add(new FileDelta(ModificationType.CREATED, "type_1/subtype_1", "/opt/test/wd-1.0/createdfile"));
        changes.add(new FileDelta(ModificationType.DELETED, "type_1/subtype_1", "/opt/test/wd-1.0/deletedfile"));
        changes.add(getRecurringModifiedFile());

        experiment.setChanges(changes);

        app.setExperiments(experiments);
        return app;
    }

    private App createSplatify() {
        final App app = new App("Splatify", "Boomshakalak software", "1.2.3", "The worst music in the world");
        final ArrayList<Experiment> experiments = new ArrayList<>();
        final Experiment experiment = new Experiment();
        experiment.setUserName("someone");
        experiment.setDeviceDetails("CoalpoweredPhone");
        experiment.setOperatingSystem("OtherOS");
        experiment.setDateTimePerformed(LocalDateTime.now().minusDays(2));
        experiments.add(experiment);

        final List<FileDelta> changes = new ArrayList<>();
        changes.add(getRecurringModifiedFile());

        experiment.setChanges(changes);

        app.setExperiments(experiments);
        return app;
    }

    private App createWhatsdownAppV2() {
        final App app = new App("WhatsDown", "Fakebook", "2.0", "A messaging application");
        final ArrayList<Experiment> experiments = new ArrayList<>();
        final Experiment experiment = new Experiment();
        experiment.setUserName("testuser");
        experiment.setDeviceDetails("Smashung S20 Ultra 5G");
        experiment.setOperatingSystem("OtherOS");
        experiment.setDateTimePerformed(LocalDateTime.now().minusDays(3));
        experiments.add(experiment);

        final List<FileDelta> changes = new ArrayList<>();
        changes.add(new FileDelta(ModificationType.DELETED, "type_3/subtype_1", "/opt/test/wd-2.0/deletedfile"));
        changes.add(new FileDelta(ModificationType.DELETED, "type_3/subtype_2", "/opt/test/wd-2.0/anotherdeletedfile"));
        changes.add(getRecurringModifiedFile());

        experiment.setChanges(changes);

        app.setExperiments(experiments);
        return app;
    }
}
