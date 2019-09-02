package hu.tamasruszka.spo.optimizer.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Empty service configuration class. The service package given to the component scan annotation
 */
@Configuration
@ComponentScan("hu.tamasruszka.spo.optimizer.service")
public class ServiceConfiguration {

}
