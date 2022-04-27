package dao;

import connections.ConnectionJDBC;
import models.Restaurant;
import resources.ReadProperties;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoRestaurantJDBC implements DaoRestaurant{

    private ConnectionJDBC connectionJDBC = new ConnectionJDBC();
    private ReadProperties readProperties = new ReadProperties();
    final String SQL_SELECT_R = "SELECT * FROM " + readProperties.getDatabaseTableR();
    final String SQL_INSERT_R = "INSERT INTO " + readProperties.getDatabaseTableR() + " (id, name, city, capacity) VALUES(?,?,?,?)";
    final String SQL_FINDBYID_R = "SELECT * FROM " + readProperties.getDatabaseTableR() + " where id=?";
    final String SQL_REMOVE_R = "DELETE FROM " + readProperties.getDatabaseTableR() + " where id=?";
    final String SQL_UPDATE_R= "UPDATE " + readProperties.getDatabaseTableR() + " set name=?, city=?, capacity=? WHERE id=? ";

    /**
     * Insereix l'objecte que li passen per parametre
     * a la base de dades utilitzant mongoDB
     *
     * @param restaurant l'objecte tipo Restaurant que vols inserir
     */
    @Override
    public void insert(Restaurant restaurant) {
        try {
            Connection connection = connectionJDBC.getMyConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_R);
            statement.setInt(1, restaurant.getIdRestaurant());
            statement.setString(2, restaurant.getName());
            statement.setString(3, restaurant.getCity());
            statement.setInt(4, restaurant.getCapacity());
            int ej = statement.executeUpdate();

            System.out.println(ej);
            connection.close();
            statement.close();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
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
        List<Restaurant> list = new ArrayList<>();

        try {
            Connection conexion = connectionJDBC.getMyConnection();
            PreparedStatement statement = conexion.prepareStatement(SQL_FINDBYID_R);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                //recuperamos los datos de la query
                int rId = resultSet.getInt("id");
                String rName = resultSet.getString("name");
                String rCity = resultSet.getString("city");
                int rCapacity = resultSet.getInt("capacity");


                Restaurant r = new Restaurant(rId, rName, rCity, rCapacity);
                list.add(r);
            }

            resultSet.close();
            statement.close();
            conexion.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.get(0);
    }

    /**
     * Busca a la base de dades tots els restaurants i els recupera
     */
    @Override
    public List<Restaurant> getAll() {
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        try {
            Connection conexion = connectionJDBC.getMyConnection();
            PreparedStatement statement = conexion.prepareStatement(SQL_SELECT_R);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                //recuperamos los datos de la query
                int idRestaurant = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String city = resultSet.getString("city");
                int capacity = resultSet.getInt("capacity");
                Restaurant r = new Restaurant(idRestaurant,name, city, capacity);

                restaurantList.add(r);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restaurantList;
    }

    /**
     * Elimina un restaurant de la base de dades
     *
     * @param id id del restaurant que es vol eliminar
     */
    @Override
    public void delete(Integer id) {
        try {
            Connection conexion = connectionJDBC.getMyConnection();
            PreparedStatement statement = conexion.prepareStatement(SQL_REMOVE_R);
            statement.setInt(1, id);
            statement.executeUpdate();
            conexion.close();
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Métode que actualitza l'objecte tipo Restaurant de la base de dades
     *
     * @param restaurant Object Restaurant
     */
    @Override
    public void update(Restaurant restaurant) {
        try {
            int registroUpdate =0;
            Connection conexion = connectionJDBC.getMyConnection();
            PreparedStatement statement = conexion.prepareStatement(SQL_UPDATE_R);
            statement.setString(1, restaurant.getName());
            statement.setString(2, restaurant.getCity());
            statement.setInt(3, restaurant.getCapacity());
            statement.setInt(4, restaurant.getIdRestaurant());
            registroUpdate = statement.executeUpdate();

            statement.close();
            conexion.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca a la base de dades els restaurants amb el nom del parametre
     * i els recupera
     *
     * @param cityName el nom dels restaurants que es volen recuperar
     */
    @Override
    public List<Restaurant> searchByCity(String cityName) {
        return null;
    }
}
