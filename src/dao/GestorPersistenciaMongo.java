package dao;

import models.Book;
import models.Restaurant;

import java.util.List;

/**
 * Implemento los metodos de GestorPersistencia
 */
public class GestorPersistenciaMongo implements GestorPersistencia {

    private DaoRestaurantMongo daoRestaurantMongo = new DaoRestaurantMongo();
    private DaoBookingMongo daoBookingMongo = new DaoBookingMongo();

    /**
     * Implemento los metodos de GestorPersistencia
     */
    @Override
    public List<Restaurant> getAll_Restaurants() {
        return daoRestaurantMongo.getAll();
    }

    /**
     * @param restaurant
     * agrega un restaurant a la db
     */
    @Override
    public void insert_Restaurant(Restaurant restaurant) {
        daoRestaurantMongo.insert(restaurant);
    }

    @Override
    public Restaurant findById_Restaurant(int id) {
        return daoRestaurantMongo.getById(id);
    }

    @Override
    public void deleteOne_Restaurant(int id) {
        daoRestaurantMongo.delete(id);
    }

    @Override
    public void update_Restaurant(Restaurant restaurant) {
        daoRestaurantMongo.update(restaurant);
    }

    @Override
    public List<Restaurant> searchByCity(String cityName) {
        return daoRestaurantMongo.searchByCity(cityName);
    }

    @Override
    public List<Book> getAll_Bookings() {
        return daoBookingMongo.getAll();
    }

    @Override
    public void insert_Book(Book book) {
        daoBookingMongo.insert(book);
    }

    @Override
    public Book findById_Book(int id) {
        return daoBookingMongo.getById(id);
    }

    @Override
    public void deleteOne_Book(int id) {
        daoBookingMongo.delete(id);
    }

    @Override
    public void update_Book(Book book) {
        daoBookingMongo.update(book);
    }
}
