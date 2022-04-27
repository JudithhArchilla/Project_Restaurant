package dao;

import models.Book;
import models.Restaurant;

import java.util.List;

public interface GestorPersistencia {

    //Restaurant
    List<Restaurant> getAll_Restaurants();

    void insert_Restaurant(Restaurant restaurant);

    Restaurant findById_Restaurant(int id);

    void deleteOne_Restaurant(int id);

    void update_Restaurant(Restaurant restaurant);

    List<Restaurant> searchByCity(String cityName);

    //Books

    List<Book> getAll_Bookings();

    void insert_Book(Book book);

    Book findById_Book(int id);

    void deleteOne_Book(int id);

    void update_Book(Book book);



}
