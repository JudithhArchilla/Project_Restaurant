package dao;

import connections.ConnectionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import models.Book;
import models.Restaurant;
import org.bson.Document;
import resources.ReadProperties;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DaoRestaurantMongo implements DaoRestaurant{

    /**
     * Insereix l'objecte que li passen per parametre
     * a la base de dades utilitzant mongoDB
     *
     * @param restaurant l'objecte tipo Restaurant que vols inserir
     */
    @Override
    public void insert(Restaurant restaurant) {
        ConnectionMongo mongodb = new ConnectionMongo();
        ReadProperties readProperties = new ReadProperties();
        String table = readProperties.getDatabaseTableR();

        try {
            mongodb.connect();
            MongoDatabase dataBase = mongodb.getDatabase();
            MongoCollection<Document> collection = dataBase.getCollection(table);

            Document doc = new Document("idRestaurant", restaurant.getIdRestaurant())
                    .append("name", restaurant.getName())
                    .append("city", restaurant.getCity())
                    .append("capacity", restaurant.getCapacity())
                    .append("bookingList", restaurant.getBookingList());

            collection.insertOne(doc);
        } finally {
            mongodb.close();
        }
    }

    /**
     * Busca a la base de dades el restaurant amb l'id del parametre
     * i el recupera
     *
     * @param id l'id del restaurant que es vol recuperar
     */
    @Override
    public Restaurant getById(Integer id) {
        ConnectionMongo mongodb = new ConnectionMongo();
        ReadProperties readProperties = new ReadProperties();
        String table = readProperties.getDatabaseTableR();

        String name = null;
        String city = null;
        int capacity = 0;
        List<Book> bookingList = new ArrayList<Book>();

        int idBook;
        String bookName;
        Date bookDateBeafore;
        int dinersBook;
        int tableN;
        boolean vegetarianMenu;
        LocalDate bookDate;
        Book book;

        try {
            mongodb.connect();
            MongoDatabase dataBase = mongodb.getDatabase();
            MongoCollection<Document> collection = dataBase.getCollection(table);

            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                Document d = cursor.next();
                if (d.getInteger("idRestaurant") == id) {

                    name = d.getString("name");
                    city = d.getString("city");
                    capacity = d.getInteger("capacity");

                    List<Document> listBooks = d.getList("bookingList", Document.class);
                    for (Document subdoc : listBooks) {
                        idBook = subdoc.getInteger("idBook");
                        bookName = subdoc.getString("bookName");
                        bookDateBeafore = subdoc.getDate("bookDate");
                        dinersBook = subdoc.getInteger("dinersBook");
                        tableN = subdoc.getInteger("tableN");
                        vegetarianMenu = subdoc.getBoolean("vegetarianMenu");

                        bookDate = bookDateBeafore.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        book = new Book(idBook, id, bookName, bookDate, dinersBook, tableN, vegetarianMenu);
                        bookingList.add(book);
                    }
                }
            }
            Restaurant res = new Restaurant(id, name, city, capacity, bookingList);
            return res;
        } finally {
            mongodb.close();
        }
    }

    /**
     * Busca a la base de dades tots els restaurants i els recupera
     */
    @Override
    public List<Restaurant> getAll() {
        ConnectionMongo mongodb = new ConnectionMongo();
        ReadProperties readProperties = new ReadProperties();
        String table = readProperties.getDatabaseTableR();

        int idRestaurant;
        String name = null;
        String city = null;
        int capacity = 0;
        List<Restaurant> restaurantsList = new ArrayList<Restaurant>();

        int idBook;
        String bookName;
        Date bookDateBeafore;
        int dinersBook;
        int tableN;
        boolean vegetarianMenu;
        LocalDate bookDate;
        Book book;

        try {
            mongodb.connect();
            MongoDatabase dataBase = mongodb.getDatabase();
            MongoCollection<Document> collection = dataBase.getCollection(table);

            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                Document d = cursor.next();

                idRestaurant = d.getInteger("idRestaurant");
                name = d.getString("name");
                city = d.getString("city");
                capacity = d.getInteger("capacity");
                List<Book> bookingList = new ArrayList<Book>();

                List<Document> listBooks = d.getList("bookingList", Document.class);
                for (Document subdoc : listBooks) {
                    idBook = subdoc.getInteger("idBook");
                    bookName = subdoc.getString("bookName");
                    bookDateBeafore = subdoc.getDate("bookDate");
                    dinersBook = subdoc.getInteger("dinersBook");
                    tableN = subdoc.getInteger("tableN");
                    vegetarianMenu = subdoc.getBoolean("vegetarianMenu");

                    bookDate = bookDateBeafore.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    book = new Book(idBook, idRestaurant, bookName, bookDate, dinersBook, tableN, vegetarianMenu);
                    bookingList.add(book);
                }
                Restaurant restaurant = new Restaurant(idRestaurant, name, city, capacity, bookingList);
                restaurantsList.add(restaurant);
            }
            return restaurantsList;
        } finally {
            mongodb.close();
        }
    }

    /**
     * Elimina un restaurant de la base de dades
     *
     * @param id id del restaurant que es vol eliminar
     */
    @Override
    public void delete(Integer id) {
        ConnectionMongo mongodb = new ConnectionMongo();
        ReadProperties readProperties = new ReadProperties();
        String table = readProperties.getDatabaseTableR();

        try {
            mongodb.connect();
            MongoDatabase dataBase = mongodb.getDatabase();
            MongoCollection<Document> collection = dataBase.getCollection(table);

            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                Document d = cursor.next();
                if (d.getInteger("idRestaurant") == id) {
                    collection.deleteOne(d);
                }
            }
        } finally {
            mongodb.close();
            System.out.println("Close");
        }
    }

    /**
     * Métode que actualitza l'objecte tipo Restaurant de la base de dades
     *
     * @param restaurant Object Restaurant
     */
    @Override
    public void update(Restaurant restaurant) {
        delete(restaurant.getIdRestaurant());
        insert(restaurant);
    }

    /**
     * Busca a la base de dades els restaurants amb el nom del parametre
     * i els recupera
     *
     * @param cityName el nom dels restaurants que es volen recuperar
     */
    @Override
    public List<Restaurant> searchByCity(String cityName) {
        ConnectionMongo mongodb = new ConnectionMongo();
        ReadProperties readProperties = new ReadProperties();
        String table = readProperties.getDatabaseTableR();

        int idRestaurant;
        String name = null;
        String city = null;
        int capacity = 0;
        List<Restaurant> restaurantsList = new ArrayList<Restaurant>();

        int idBook;
        String bookName;
        Date bookDateBeafore;
        int dinersBook;
        int tableN;
        boolean vegetarianMenu;
        LocalDate bookDate;
        Book book;

        try {
            mongodb.connect();
            MongoDatabase dataBase = mongodb.getDatabase();
            MongoCollection<Document> collection = dataBase.getCollection(table);

            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                Document d = cursor.next();
                if (d.getString("city").equals(cityName)) {

                    idRestaurant = d.getInteger("idRestaurant");
                    name = d.getString("name");
                    city = d.getString("city");
                    capacity = d.getInteger("capacity");
                    List<Book> bookingList = new ArrayList<Book>();

                    List<Document> listBooks = d.getList("bookingList", Document.class);
                    for (Document subdoc : listBooks) {
                        idBook = subdoc.getInteger("idBook");
                        bookName = subdoc.getString("bookName");
                        bookDateBeafore = subdoc.getDate("bookDate");
                        dinersBook = subdoc.getInteger("dinersBook");
                        tableN = subdoc.getInteger("tableN");
                        vegetarianMenu = subdoc.getBoolean("vegetarianMenu");

                        bookDate = bookDateBeafore.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        book = new Book(idBook, idRestaurant, bookName, bookDate, dinersBook, tableN, vegetarianMenu);
                        bookingList.add(book);
                    }
                    Restaurant restaurant = new Restaurant(idRestaurant, name, city, capacity, bookingList);
                    restaurantsList.add(restaurant);
                }
            }
            return restaurantsList;
        } finally {
            mongodb.close();
        }
    }
}
