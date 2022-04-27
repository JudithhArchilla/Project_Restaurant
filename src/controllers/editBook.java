package controllers;

import Utils.Comprobaciones;
import dao.GestorPersistencia;
import dao.GestorPersistenciaJDBC;
import dao.GestorPersistenciaMongo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Book;
import resources.ReadProperties;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class editBook implements Initializable {
    ReadProperties readP = new ReadProperties();

    @FXML
    private Button btnGoBack;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtDinners;
    @FXML
    private TextField txtTable;
    @FXML
    private TextField txtVgMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"consultBooks.fxml"));
            Stage stage = (Stage) btnGoBack.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editBook(ActionEvent actionEvent) {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorPersistenciaMongo = new GestorPersistenciaMongo();

        int idSelectedRestaurant = home.restaurantSelected.getIdRestaurant();
        int idbooksele = consultBook.idBookSelectioned;
        String name = this.txtName.getText();
        String date = this.txtDate.getText();
        int dinnersBook = Integer.parseInt(this.txtDinners.getText());
        int tableNro = Integer.parseInt(this.txtTable.getText());
        String vgMenu = this.txtVgMenu.getText();

        boolean vgMyMenu = Comprobaciones.comprobacionVgMenu(vgMenu);
        LocalDate localDate = Comprobaciones.comprobacionFecha(date);
        Book book = new Book(idbooksele, idSelectedRestaurant, name, localDate, dinnersBook, tableNro, vgMyMenu);

        if (home.selected.equals("JDBC")) {
            gestorPersistenciaJdbc.update_Book(book);

        } else if (home.selected.equals("MONGODB")) {
            gestorPersistenciaMongo.update_Book(book);
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Se ha actualizado el book");
        alert.showAndWait();

        goBack();
    }

    public void deleteBook(ActionEvent actionEvent) {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorPersistenciaMongo = new GestorPersistenciaMongo();

        if (home.selected.equals("JDBC")) {
            gestorPersistenciaJdbc.deleteOne_Book(consultBook.idBookSelectioned);

        } else if (home.selected.equals("MONGODB")) {
            gestorPersistenciaMongo.deleteOne_Book(consultBook.idBookSelectioned);
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Se ha eliminado el book");
        alert.showAndWait();
        goBack();
    }
}
    
