package resources;

import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class ReadProperties {
    private static String database;
    private static String databaseTableR;
    private static String dataBaseTableB;
    private static String jdbc;
    private static String user;
    private static String password;
    private static String rView;
    private static String rImages;


    /**
     * Read property se encarga de leer lo relacionado con la db en config.properties
     */
    public ReadProperties() {
        Properties propiedades = new Properties();
        try {
            //  /home/users/inf/wiam2/iam48131844/IdeaProjects/uf4-m06/Project_Restaurant/src/resources/config.properties
            propiedades.load(new FileReader("../Project_Restaurant/src/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Enumeration<Object> claves = propiedades.keys();


        while (claves.hasMoreElements()) {
            Object clave = claves.nextElement();


            switch (clave.toString()) {

                case "dataBase":
                    database = propiedades.get(clave).toString();
                    break;
                case "tableR":
                    databaseTableR = propiedades.get(clave).toString();
                    break;
                case "tableB":
                    dataBaseTableB = propiedades.get(clave).toString();
                    break;
                case "jdbc":
                    jdbc = propiedades.get(clave).toString();
                    break;
                case "user":
                    user = propiedades.get(clave).toString();
                    break;
                case "password":
                    password = propiedades.get(clave).toString();
                    break;
                case "ruta_view":
                    rView = propiedades.get(clave).toString();
                    break;
                case "ruta_img":
                    rImages = propiedades.get(clave).toString();
                    break;

            }

        }

    }

    public String getrView() {
        return rView;
    }

    public String getrImages() {
        return rImages;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        ReadProperties.database = database;
    }

    public String getDatabaseTableR() {
        return databaseTableR;
    }

    public void setDatabaseTableR(String databaseTableR) {
        ReadProperties.databaseTableR = databaseTableR;
    }

    public String getDataBaseTableB() {
        return dataBaseTableB;
    }

    public void setDataBaseTableB(String dataBaseTableB) {
        ReadProperties.dataBaseTableB = dataBaseTableB;
    }

    public String getJdbc() {
        return jdbc;
    }

    public void setJdbc(String jdbc) {
        ReadProperties.jdbc = jdbc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        ReadProperties.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        ReadProperties.password = password;
    }


}



