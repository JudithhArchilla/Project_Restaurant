package connections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import resources.ReadProperties;

public class ConnectionMongo implements Connection {

    MongoDatabase database;
    MongoClient mongoClient;

    /**
     * El método se conecta a la base de datos con MongoDB
     */
    @Override
    public void connect() {
        ReadProperties readProperties = new ReadProperties();
        String mongoDB = readProperties.getDatabase();

        try  {
            mongoClient = MongoClients.create();
            database = mongoClient.getDatabase(mongoDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        mongoClient.close();
    }

    /**
     * @return devuelve la base de datos, para poder utilizarla en los diferentes métodos
     */
    public MongoDatabase getDatabase() {
        return database;
    }
}
