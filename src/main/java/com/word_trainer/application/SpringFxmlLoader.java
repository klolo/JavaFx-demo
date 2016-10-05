package com.word_trainer.application;

import javafx.fxml.FXMLLoader;
import lombok.Getter;
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
@Getter
public class SpringFxmlLoader {

    private static final String BUNDLES_PATH = "common//bundles//messages";

    private static String defaultLocale = "pl";

    private static final ApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(SpringApplicationConfig.class);

    public static ResourceBundle resourceBundle;

    public Object load(final String url) {
        log.info("Load stage: {}", url);

        try (final InputStream fxmlStream = SpringFxmlLoader.class.getResourceAsStream(url)) {
            final FXMLLoader loader = new FXMLLoader();
            resourceBundle = ResourceBundle.getBundle(BUNDLES_PATH, new Locale(defaultLocale));
            loader.setResources(resourceBundle);
            loader.setControllerFactory(applicationContext::getBean);
            loader.setLocation(Class.class.getResource(url));
            return loader.load(fxmlStream);
        }
        catch (final IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public static void changeLocale(final String locale) {
        defaultLocale = locale;
    }
}
