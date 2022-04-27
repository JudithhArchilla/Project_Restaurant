package dao;

import connections.ConnectionJDBC;
import models.Book;
import resources.ReadProperties;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoBookingJDBC implements DaoBooking{

    private ConnectionJDBC connectionJDBC = new ConnectionJDBC();
    private ReadProperties readProperties = new ReadProperties();

    final String SQL_INSERT = "INSERT INTO " + readProperties.getDataBaseTableB() + " (id_book, id_restaurant, book_name, book_date, diners_book, table_nro, vegetarian_menu) VALUES(?,?,?,?,?,?,?)";
    final String SQL_FINDBYID = "SELECT * FROM " + readProperties.getDataBaseTableB() + " where id=?";
    final String SQL_SELECT = "SELECT * FROM " + readProperties.getDataBaseTableB();
    final String SQL_REMOVE_BOOK = "DELETE FROM " + readProperties.getDataBaseTableB() + " where id_book=?";
    final String SQL_UPDATE_BOOK = "UPDATE " + readProperties.getDataBaseTableB() + " set book_name=?, book_date=?, diners_book=?, table_nro=?, vegetarian_menu=? WHERE id_book=? ";

    /**
     * este método se encarga de insertar este libro en la db
     *
     * @param book el objeto tipo Book que quieres insertar
     */
    @Override
    public void insert(Book book) {
        try {
            Connection connection = connectionJDBC.getMyConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setInt(1, book.getIdBook());
            statement.setInt(2, book.getIdRestaurant());
            statement.setString(3, book.getBookName());
            statement.setDate(4, java.sql.Date.valueOf(book.getBookDate()));
            statement.setInt(5, book.getDinersBook());
            statement.setInt(6, book.getTableN());
            statement.setBoolean(7, book.isVegetarianMenu());
            int ej = statement.executeUpdate();

            System.out.println(ej);
            connection.close();
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pasándole un id de un Book, busca el book dentro de la bd
     *
     * @param id id del book que se quiere buscar
     * @return devuelve el objeto book con ese id
     */
    @Override
    public Book getById(Integer id) {
        List<Book> list = new ArrayList<>();

        try {
            Connection conexion = connectionJDBC.getMyConnection();
            PreparedStatement statement = conexion.prepareStatement(SQL_FINDBYID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                //recuperamos los datos
                int idBook = resultSet.getInt("id_book");
                int idRestaurant = resultSet.getInt("id_restaurant");
                String bookName = resultSet.getString("book_name");
                LocalDate bookDate = resultSet.getDate("book_date").toLocalDate();
                int dinersBook = resultSet.getInt("diners_book");
                int tableN = resultSet.getInt("table_nro");
                boolean vegetarianMenu = resultSet.getBoolean("vegetarian_menu");

                Book book = new Book(idBook, idRestaurant, bookName, bookDate, dinersBook, tableN, vegetarianMenu);
                list.add(book);
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
     * Recupera todos los Books de todos los restaurantes
     *
     * @return Listado de todos los books
     */
    @Override
    public List<Book> getAll() {

        List<Book> listBooks = new ArrayList<Book>();

        try {
            ConnectionJDBC connectionJDBC = new ConnectionJDBC();

            Connection conexion = connectionJDBC.getMyConnection();

            PreparedStatement statement = conexion.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                //recuperamos los datos
                int idBook = resultSet.getInt("id_book");
                int idRestaurant = resultSet.getInt("id_restaurant");
                String bookName = resultSet.getString("book_name");
                LocalDate bookDate = resultSet.getDate("book_date").toLocalDate();
                int dinersBook = resultSet.getInt("diners_book");
                int tableN = resultSet.getInt("table_nro");
                boolean vegetarianMenu = resultSet.getBoolean("vegetarian_menu");

                Book book = new Book(idBook, idRestaurant, bookName, bookDate, dinersBook, tableN, vegetarianMenu);

                listBooks.add(book);
            }

            resultSet.close();
            statement.close();
            conexion.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listBooks;
    }

    /**
     * El metodo se encarga de borrar el objeto con ese id de la db
     *
     * @param id idBook del Book que quieres eliminar
     */
    @Override
    public void delete(Integer id) {
        try {
            Connection conexion = connectionJDBC.getMyConnection();
            PreparedStatement statement = conexion.prepareStatement(SQL_REMOVE_BOOK);
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
     * Método que actualiza el objeto tipo Book de la base de datos
     *
     * @param book recibe un book y hace el update
     */
    @Override
    public void update(Book book) {
        try {
            int registroUpdate = 0;
            Connection conexion = connectionJDBC.getMyConnection();
            PreparedStatement statement = conexion.prepareStatement(SQL_UPDATE_BOOK);
            statement.setString(1, book.getBookName());
            statement.setDate(2, java.sql.Date.valueOf(book.getBookDate()));
            statement.setInt(3, book.getDinersBook());
            statement.setInt(4, book.getTableN());
            statement.setBoolean(5, book.isVegetarianMenu());
            statement.setInt(6, book.getIdBook());
            registroUpdate = statement.executeUpdate();

            statement.close();
            conexion.close();

            System.out.println(registroUpdate);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
