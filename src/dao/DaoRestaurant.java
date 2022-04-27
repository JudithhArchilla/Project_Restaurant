package dao;

import models.Restaurant;

import java.io.IOException;
import java.util.List;

public interface DaoRestaurant extends Dao<Restaurant, Integer> {

    List<Restaurant> searchByCity(String cityName) throws IOException;

}