package io.github.ivanbabura.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class DatabaseProperties extends Properties {
    final String propertiesFileName = "database.properties";

    /**
     * Использует название по умолчанию "database.properties".
     */
    public synchronized void loadFromResourceFile() {
        loadFromResourceFile(propertiesFileName);
    }

    /**
     * Можно создать несколько файлов с разными подключениями.
     */
    public synchronized void loadFromResourceFile(String propertiesFileName) {
        URL dbResource = Thread.currentThread()
                .getContextClassLoader()
                .getResource(propertiesFileName);
        if (dbResource != null)
            try (InputStream propertiesStream = dbResource.openStream()) {
                load(propertiesStream);
            } catch (IOException | NullPointerException e) {
                System.out.printf("Error read database properties from \"%s\": %s \n", propertiesFileName, e.getMessage());
            }
        else throw new RuntimeException("Error load resources");
    }

    /**
     * Возвращает копию Properties, которая конвертирует данные для Hibernate, тк у него свои параметры.
     */
    public DatabaseProperties getConvertedToHibernate() {
        DatabaseProperties clonedDbProp = (DatabaseProperties) this.clone();
        String[] props = {"url", "username", "password", "driver_class"};
        String str = "hibernate.connection.";
        for (String prop : props) {
            clonedDbProp.setProperty(str + prop, getProperty(prop));
            clonedDbProp.remove(prop);
        }
        clonedDbProp.setProperty("hibernate.dialect", getProperty("dialect"));
        clonedDbProp.remove("dialect");
        return clonedDbProp;
    }

}
