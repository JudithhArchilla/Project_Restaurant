package dao;

import models.Book;
import models.Restaurant;

import java.util.List;

/**
 * Implemento los metodos de GestorPersistencia
 */
public class GestorPersistenciaJDBC implements GestorPersistencia {
    private DaoBookingJDBC daoBookingJDBC = new DaoBookingJDBC();
    private DaoRestaurantJDBC daoRestaurantJDBC = new DaoRestaurantJDBC();

    /**
     * @return  devuelve todos los restaurants
     */
    @Override
    public List<Restaurant> getAll_Restaurants() {
        return daoRestaurantJDBC.getAll();
    }

    /**
     * @param restaurant
     * agrega un restaurant a la db
     */
    @Override
    public void insert_Restaurant(Restaurant restaurant) {
        daoRestaurantJDBC.insert(restaurant);
    }

    @Override
    public Restaurant findById_Restaurant(int id) {
        return daoRestaurantJDBC.getById(id);
    }

    @Override
    public void deleteOne_Restaurant(int id) {
        daoRestaurantJDBC.delete(id);
    }

    @Override
    public void update_Restaurant(Restaurant restaurant) {
        daoRestaurantJDBC.update(restaurant);
    }

    @Override
    public List<Restaurant> searchByCity(String cityName) {
        return daoRestaurantJDBC.searchByCity(cityName);
    }

    @Override
    public List<Book> getAll_Bookings() {
        return daoBookingJDBC.getAll();
    }

    @Override
    public void insert_Book(Book book) {
        daoBookingJDBC.insert(book);
    }

    @Override
    public Book findById_Book(int id) {
        return daoBookingJDBC.getById(id);
    }

    @Override
    public void deleteOne_Book(int id) {
        daoBookingJDBC.delete(id);
    }

    @Override
    public void update_Book(Book book) {
        daoBookingJDBC.update(book);
    }
}
