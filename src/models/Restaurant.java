package models;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    int idRestaurant;
    String name;
    String city;
    int capacity;
    List<Book> bookingList = new ArrayList<>();

    public Restaurant(int idRestaurant, String name, String city, int capacity) {
        this.idRestaurant = idRestaurant;
        this.name = name;
        this.city = city;
        this.capacity = capacity;
    }

    public Restaurant(int idRestaurant, String name, String city, int capacity, List<Book> bookingList) {
        this.idRestaurant = idRestaurant;
        this.name = name;
        this.city = city;
        this.capacity = capacity;
        this.bookingList = bookingList;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Book> getBookingList() {
        return bookingList;
    }

    public void addBooking(Book book){
        bookingList.add(book);
    }

    public void setBookingList(List<Book> bookingList) {
        this.bookingList = bookingList;
    }

    @Override
    public String toString() {
        return "Restaurant { " +
                "idRestaurant=" + idRestaurant +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", capacity=" + capacity +
                ", bookingList=" + bookingList +
                '}';
    }
}
