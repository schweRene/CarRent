package auto.carrent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.*;
//import java.sql.Date;
import java.util.*;
import java.util.Date;

public class DashboardController implements Initializable {


    @FXML
    private TextField availableCars_brand;

    @FXML
    private Button availableCars_btn;

    @FXML
    private TextField availableCars_carId;

    @FXML
    private Button availableCars_clearBtn;

    @FXML
    private TableColumn<CarData, String> availableCars_col_brand;

    @FXML
    private TableColumn<CarData, String> availableCars_col_carId;

    @FXML
    private TableColumn<CarData, String> availableCars_col_model;

    @FXML
    private TableColumn<CarData, String> availableCars_col_price;

    @FXML
    private TableColumn<CarData, String> availableCars_col_status;

    @FXML
    private Button availableCars_deleteBtn;

    @FXML
    private AnchorPane availableCars_form;

    @FXML
    private ImageView availableCars_imageView;

    @FXML
    private Button availableCars_importBtn;

    @FXML
    private Button availableCars_insertBtn;

    @FXML
    private TextField availableCars_model;

    @FXML
    private TextField availableCars_price;

    @FXML
    private TextField availableCars_search;

    @FXML
    private ComboBox<?> availableCars_status;

    @FXML
    private TableView<CarData> availableCars_tableView;

    @FXML
    private Button availableCars_updateBtn;

    @FXML
    private Button close;

    @FXML
    private BarChart<?, ?> home_IncomeChart;

    @FXML
    private Label home_availableCars;

    @FXML
    private Button home_btn;

    @FXML
    private Label home_totalCustomers;

    @FXML
    private Label home_totalIncome;

    @FXML
    private LineChart<?, ?> homer_customerChart;

    @FXML
    private Button logoutBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Button minimize;

    @FXML
    private Button rentBtn;

    @FXML
    private Button rentCar_btn;

    @FXML
    private TextField rent_amount;

    @FXML
    private Label rent_balance;

    @FXML
    private ComboBox<?> rent_brand;

    @FXML
    private ComboBox<?> rent_carid;

    @FXML
    private TableColumn<CarData, String> rent_col_brand;

    @FXML
    private TableColumn<CarData, String> rent_col_carid;

    @FXML
    private TableColumn<CarData, String> rent_col_model;

    @FXML
    private TableColumn<CarData, String> rent_col_price;

    @FXML
    private TableColumn<CarData, String> rent_col_status;

    @FXML
    private DatePicker rent_dateRented;

    @FXML
    private DatePicker rent_dateReturn;

    @FXML
    private TextField rent_firstName;

    @FXML
    private AnchorPane rent_form;

    @FXML
    private ComboBox<?> rent_gender;

    @FXML
    private TextField rent_lastName;

    @FXML
    private ComboBox<?> rent_model;

    @FXML
    private TableView<CarData> rent_tableView;

    @FXML
    private Label rent_total;

    @FXML
    private Label username;

    @FXML
    private Label welcome;

    @FXML
    private ImageView searchImage;

    //Database Tools

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private Image image;

    public void homeAvailableCars() {

        String sql = "SELECT COUNT(id) FROM car WHERE status = 'Available'";

        connect = Database.connectDB();

        int countAC = 0;

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {

                countAC = result.getInt("COUNT(id)");

            }

            home_availableCars.setText(String.valueOf(countAC));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeTotalIncome() {
        String sql = "SELECT SUM(total) FROM customer";

        double sumIncome = 0;

        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                sumIncome = result.getDouble("SUM(total)");
            }
            home_totalIncome.setText("€" + String.valueOf(sumIncome));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void homeTotalCustomers() {

        String sql = "SELECT COUNT(id) FROM customer";


        int countTC = 0;

        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {

                countTC = result.getInt("COUNT(id)");

            }
            home_totalCustomers.setText(String.valueOf(countTC));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeIcomeChart() {

        home_IncomeChart.getData().clear();

        String sql = "SELECT date_rented, SUM(total) FROM customer GROUP BY date_rented ORDER BY TIMESTAMP(date_rented) ASC LIMIT 6";

        connect = Database.connectDB();

        try {

            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {

                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));

            }

            home_IncomeChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeCustomerChart(){
            homer_customerChart.getData().clear();

            String sql = "SELECT date_rented, COUNT(id) FROM customer GROUP BY date_rented ORDER BY TIMESTAMP(date_rented) ASC LIMIT 4";

            connect = Database.connectDB();

            try {

                XYChart.Series chart = new XYChart.Series();

                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();

                while (result.next()){

                    chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
                    }

                homer_customerChart.getData().add(chart);

            }catch (Exception e){e.printStackTrace();}

    }

    public void availableCarAdd() {

        String sql = "INSERT INTO car (car_id, brand, model, price, status, image, date)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";

        connect = Database.connectDB();

        try {
            Alert alert;

            if (availableCars_carId.getText().isEmpty()
                    || availableCars_brand.getText().isEmpty()
                    || availableCars_model.getText().isEmpty()
                    || availableCars_status.getSelectionModel().getSelectedItem() == null
                    || availableCars_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Fülle bitte alle Felder aus.");
                alert.showAndWait();

            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, availableCars_carId.getText());
                prepare.setString(2, availableCars_brand.getText());
                prepare.setString(3, availableCars_model.getText());
                prepare.setString(4, availableCars_price.getText());
                prepare.setString(5, (String) availableCars_status.getSelectionModel().getSelectedItem());

                String uri = getData.path;
                uri = uri.replace("\\", "\\\\");

                prepare.setString(6, uri);


                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(7, String.valueOf(sqlDate));

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Hinzufügen war erfolgreich.");
                alert.showAndWait();

                availableCarShowListData();
                availableCarClear();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableCarUpdate() {
        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");


        String sql = "UPDATE car SET brand = '" + availableCars_brand.getText() + "', model ='"
                + availableCars_model.getText() + "', status = '"
                + availableCars_status.getSelectionModel().getSelectedItem() + "', price = '"
                + availableCars_price.getText() + "', image ='" + uri
                + "' WHERE car_id = '" + availableCars_carId.getText() + "'";

        connect = Database.connectDB();

        try {
            Alert alert;

            if (availableCars_carId.getText().isEmpty()
                    || availableCars_brand.getText().isEmpty()
                    || availableCars_model.getText().isEmpty()
                    || availableCars_status.getSelectionModel().getSelectedItem() == null
                    || availableCars_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Fülle bitte alle Felder aus.");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Sind Sie sicher das sie dieses Fahrzeug aktualisieren möchten: " + availableCars_carId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Aktualisieren war erfolgreich.");
                    alert.showAndWait();

                    availableCarShowListData();
                    availableCarClear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableCarDelete() {

        String sql = "DELETE FROM car WHERE car_id = '" + availableCars_carId.getText() + "'";

        connect = Database.connectDB();

        try {

            Alert alert;

            if (availableCars_carId.getText().isEmpty()
                    || availableCars_brand.getText().isEmpty()
                    || availableCars_model.getText().isEmpty()
                    || availableCars_status.getSelectionModel().getSelectedItem() == null
                    || availableCars_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Fülle bitte alle Felder aus.");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Sind Sie sicher das sie dieses Fahrzeug löschen möchten: " + availableCars_carId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Löschen war erfolgreich.");
                    alert.showAndWait();

                    availableCarShowListData();
                    availableCarClear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableCarClear() {

        availableCars_carId.setText("");
        availableCars_brand.setText("");
        availableCars_model.setText("");
        availableCars_status.getSelectionModel().clearSelection();
        availableCars_price.setText("");

        getData.path = "";

        availableCars_imageView.setImage(null);
    }

    private String[] listStatus = {"Verfügbar", "Nicht verfügbar"};


    public void availableCarStatusList() {


        List<String> listS = new ArrayList<>();

        for (String data : listStatus) {
            listS.add(data);

        }

        ObservableList listData = FXCollections.observableArrayList(listS);

        availableCars_status.setItems(listData);

    }

    public void availableCarImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Öffne eine Bilddatei");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter(" Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 116, 150, false, true);

            availableCars_imageView.setImage(image);
        }

    }

    public ObservableList<CarData> availableCarListData() {

        ObservableList<CarData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM car";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            CarData carID;

            while (result.next()) {
                carID = new CarData(result.getInt("car_id")
                        , result.getString("brand")
                        , result.getString("model")
                        , result.getDouble("price")
                        , result.getString("status")
                        , result.getString("image")
                        , result.getDate("date"));

                listData.add(carID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;

    }

    private ObservableList<CarData> availableCarList;

    public void availableCarShowListData() {
        availableCarList = availableCarListData();

        availableCars_col_carId.setCellValueFactory(new PropertyValueFactory<>("car_id"));
        availableCars_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        availableCars_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        availableCars_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableCars_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        availableCars_tableView.setItems(availableCarList);

    }

    public void availableCarSearch() {
        FilteredList<CarData> filter = new FilteredList<>(availableCarList, e -> true);

        availableCars_search.textProperty().addListener((Observable, oldValue, newValue) -> {


            filter.setPredicate(predicateCarData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateCarData.getCarID().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCarData.getBrand().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCarData.getModel().toLowerCase().contains(searchKey)) {
                    return true;
                }else if(predicateCarData.getPrice().toString().contains(searchKey)){
                    return true;
                }
                else if (predicateCarData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else{ return false;}
            });
        });
        SortedList<CarData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(availableCars_tableView.comparatorProperty());
        availableCars_tableView.setItems(sortList);
    }

    public void availableCarSelect() {

        CarData carD = availableCars_tableView.getSelectionModel().getSelectedItem();
        int num = availableCars_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        availableCars_carId.setText(String.valueOf(carD.getCarID()));
        availableCars_brand.setText(carD.getBrand());
        availableCars_model.setText(carD.getModel());
        availableCars_price.setText(String.valueOf(carD.getPrice()));

        getData.path = carD.getImage();


        String uri = "file:" + carD.getImage();


        image = new Image(uri, 116, 150, false, true);
        availableCars_imageView.setImage(image);
    }

    public void rentPay() {
        rentCustomerID();

        String sql = "INSERT INTO customer"
                + "(customer_id, firstname, lastname, gender, car_id, brand"
                + ", model, total, date_rented, date_return) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";

        connect = Database.connectDB();

        try {
            Alert alert;

            if (rent_firstName.getText().isEmpty()
                    || rent_lastName.getText().isEmpty()
                    || rent_gender.getSelectionModel().getSelectedItem() == null
                    || rent_carid.getSelectionModel().getSelectedItem() == null
                    || rent_brand.getSelectionModel().getSelectedItem() == null
                    || rent_model.getSelectionModel().getSelectedItem() == null
                    || totalPrice == 0 || rent_amount.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Irgendetwas ist falsch :3");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Sind Sie sicher?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, rent_firstName.getText());
                    prepare.setString(3, rent_lastName.getText());
                    prepare.setString(4, (String) rent_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, (String) rent_carid.getSelectionModel().getSelectedItem());
                    prepare.setString(6, (String) rent_brand.getSelectionModel().getSelectedItem());
                    prepare.setString(7, (String) rent_model.getSelectionModel().getSelectedItem());
                    prepare.setString(8, String.valueOf(totalPrice));
                    prepare.setString(9, String.valueOf(rent_dateRented));
                    prepare.setString(10, String.valueOf(rent_dateReturn));

                    prepare.executeUpdate();

                    //SEt the Status of car to not available
                    String updateCar = "UPDATE car SET status = 'Not Available' WHERE car_id = '"
                            + rent_carid.getSelectionModel().getSelectedItem() + "'";

                    statement = connect.createStatement();
                    statement.executeUpdate(updateCar);


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Erfolgreich hinzugefügt");
                    alert.showAndWait();

                    rentCarShowListData();
                    rentClear();
                } //Now lets proceed to dashboard Form
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rentClear() {
        totalPrice = 0;
        rent_firstName.setText("");
        rent_lastName.setText("");
        rent_gender.getSelectionModel().getSelectedItem();
        amount = 0;
        balance = 0;
        rent_balance.setText("€0.0");
        rent_total.setText("€0.0");
        rent_amount.setText("");
        rent_carid.getSelectionModel().clearSelection();
        rent_brand.getSelectionModel().clearSelection();
        rent_model.getSelectionModel().clearSelection();
    }

    private int customerId;

    public void rentCustomerID() {
        String sql = "SELECT id FROM customer";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                //Get the last id and add + 1
                customerId = result.getInt("id") + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private double amount;
    private double balance;

    public void rentAmount() {
        Alert alert;

        if (totalPrice == 0 || rent_amount.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Fehlgeschlagen :3");
            alert.showAndWait();

            rent_amount.setText("");
        } else {
            amount = Double.parseDouble(rent_amount.getText());

            if (amount >= totalPrice) {
                balance = (amount - totalPrice);
                rent_balance.setText("€" + String.valueOf(balance));

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Fehlgeschlagen :3");
                alert.showAndWait();

                rent_amount.setText("");
            }
        }

    }

    public ObservableList<CarData> rentCarListData() {

        ObservableList<CarData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM car ";

        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            CarData carID;

            while (result.next()) {
                carID = new CarData(result.getInt("car_id")
                        , result.getString("brand")
                        , result.getString("model")
                        , result.getDouble("price")
                        , result.getString("status")
                        , result.getString("image")
                        , result.getDate("date"));

                listData.add(carID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private int countDate;

    public void rentDate() {
        Alert alert;

        if (rent_carid.getSelectionModel().getSelectedItem() == null
                || rent_brand.getSelectionModel().getSelectedItem() == null
                || rent_model.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Irgendetwas stimmt nicht.");
            alert.showAndWait();

            rent_dateRented.setValue(null);
            rent_dateReturn.setValue(null);


        } else {
            if (rent_dateReturn.getValue().isAfter(rent_dateRented.getValue())) {
                //Count the Day
                countDate = rent_dateReturn.getValue().compareTo(rent_dateRented.getValue());
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Irgendetwas stimmt nicht.");
                alert.showAndWait();

                //Increase of 1 day once the user clicked the same day
                rent_dateReturn.setValue(rent_dateRented.getValue().plusDays(1));
            }
        }
    }

    private double totalPrice;

    public void rentDisplayTotal() {
        rentDate();
        double price = 0;

        String sql = "SELECT price, model FROM car WHERE model = '"
                + rent_model.getSelectionModel().getSelectedItem() + "'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                price = result.getDouble("price");
            }
            //price * the count day you want to use the car
            totalPrice = (price * countDate);
            //Display total
            rent_total.setText("€" + String.valueOf(totalPrice));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] genderList = {"Männlich", "Weiblich", "Andere"};

    public void rentCarGender() {
        List<String> listG = new ArrayList<>();

        for (String data : genderList) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        rent_gender.setItems(listData);
    }

    public void rentCarCarID() {

        String sql = "SELECET * FROM car WHERE status = 'Available'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("car_id"));
            }

            rent_carid.setItems(listData);

            rentCarBrand();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rentCarBrand() {

        String sql = "SELECT * FROM car WHERE car_id = '"
                + rent_carid.getSelectionModel().getSelectedItem() + "'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("brand"));
            }

            rent_brand.setItems(listData);

            rentCarModel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rentCarModel() {

        String sql = "SELECT * FROM car WHERE brand = '"
                + rent_brand.getSelectionModel().getSelectedItem() + "'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("model"));
            }

            rent_model.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<CarData> rentCarList;

    public void rentCarShowListData() {
        rentCarList = rentCarListData();

        rent_col_carid.setCellValueFactory(new PropertyValueFactory<>("car_id"));
        rent_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        rent_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        rent_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        rent_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        rent_tableView.setItems(rentCarList);

    }

    public void displayUsername() {
        String user = getData.username;

        //To set the first Letter to big Letter
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
    }

    private double x = 0;
    private double y = 0;

    public void logout() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Bist du sicher, dass du dich ausloggen willst?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            //Link your Login Form
            if (option.get().equals(ButtonType.OK)) {
                //Hide your Dashboard Form
                logoutBtn.getScene().getWindow().hide();


                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });


                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            }
        } catch (Exception e) {

        }
    }


    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            availableCars_form.setVisible(false);
            rent_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #796c6f, #3a404a);");
            availableCars_btn.setStyle("-fx-background-color:transparent");
            rentCar_btn.setStyle("-fx-background-color:transparent");

            homeAvailableCars();
            homeTotalIncome();
            homeTotalCustomers();
            homeIcomeChart();
            homeCustomerChart();

        } else if (event.getSource() == availableCars_btn) {
            home_form.setVisible(false);
            availableCars_form.setVisible(true);
            rent_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:transparent");
            availableCars_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #796c6f, #3a404a);");
            rentCar_btn.setStyle("-fx-background-color:transparent");

            //To update your Tableview once you click the available Car Nav Button
            availableCarShowListData();
            availableCarStatusList();
            availableCarSearch();

        } else if (event.getSource() == rentCar_btn) {
            home_form.setVisible(false);
            availableCars_form.setVisible(false);
            rent_form.setVisible(true);

            home_btn.setStyle("-fx-background-color:transparent");
            availableCars_btn.setStyle("-fx-background-color:transparent");
            rentCar_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #796c6f, #3a404a);");

            rentCarShowListData();
            rentCarCarID();
            rentCarBrand();
            rentCarModel();
            rentCarGender();
        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();

        homeAvailableCars();
        homeTotalIncome();
        homeTotalCustomers();
        homeIcomeChart();
        homeCustomerChart();

        //to Display the Data on the Tableview
        availableCarShowListData();
        availableCarStatusList();
        availableCarSearch();

        rentCarShowListData();
        rentCarCarID();
        rentCarBrand();
        rentCarModel();
        rentCarGender();
    }
}
