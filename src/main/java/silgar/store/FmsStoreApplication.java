package silgar.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("silgar.store.minio")
public class FmsStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FmsStoreApplication.class, args);
	}

}
