package com.word_trainer.application;

import javafx.fxml.FXMLLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Klasa odpowiada za ladowanie widoków fxml za pomoca springa.
 */
@Slf4j
public class SpringFxmlLoader {

    private static final String BUNDLES_PATH = "common//bundles//messages";

    private static final String DEFAULT_LOCALE = "pl";

    private static final ApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(SpringApplicationConfig.class);

    public Object load(final String url) {
        log.info("Load stage: {}", url);

        try (final InputStream fxmlStream = SpringFxmlLoader.class.getResourceAsStream(url)) {
            final FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle(BUNDLES_PATH, new Locale(DEFAULT_LOCALE)));
            loader.setControllerFactory(applicationContext::getBean);
            loader.setLocation(Class.class.getResource(url));
            return loader.load(fxmlStream);
        }
        catch (final IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
