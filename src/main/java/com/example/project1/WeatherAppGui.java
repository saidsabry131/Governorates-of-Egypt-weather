package com.example.project1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import  javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import javafx.geometry.Insets;
import org.json.simple.JSONObject;

import static java.lang.Thread.sleep;

public class WeatherAppGui extends Application {
    private static final int IMAGES_PER_ROW = 4;
    private static final int NUMBER_OF_ROWS = 7;
    private static final String[] GOVERNORATES = {
            "Alexandria", "Aswan", "Asyut", "Beheira", "Beni Suef", "Cairo",
            "Dakahlia", "Damietta", "Faiyum", "Gharbia", "Giza", "Ismailia",
            "Kafr El Sheikh", "Luxor", "Matruh", "Minya", "Monufia", "New Valley",
            "Sinai", "Port Said", "Qalyubia", "Qena", "Red Sea", "Sharqia", "Sohag", "South Sinai"
    };
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Set up the root node (GridPane)
        ScrollPane SP = new ScrollPane();
        GridPane root = new GridPane();
        SP.setContent(root);
        root.setHgap(13);
        root.setVgap(10);
        root.setPadding(new Insets(10));
        primaryStage.setResizable(false);

        // Directory containing your images
        File imagesDirectory = new File("src\\images 2");

        // Load all images from the directory
        File[] imageFiles = imagesDirectory.listFiles();
        if (imageFiles != null) {
            int row = 0;
            int col = 0;

            for (int i = 0; i< GOVERNORATES.length; i++) {
                File file = imageFiles[i];
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(209); // Set the width as needed
                imageView.setPreserveRatio(true);

                Button button = new Button(GOVERNORATES[i]);

                // Add the image and button to the GridPane
                root.add(imageView, col, row);
                root.add(button, col, row + 1);
                GridPane.setHalignment(button, javafx.geometry.HPos.CENTER);
                int finalI = i;
                final String currentGovernorate = GOVERNORATES[i];
                button.setOnAction(event ->{
                    // Create a new scene for results
                    GridPane resultsPane = new GridPane();
                    resultsPane.setAlignment(Pos.CENTER);
                    resultsPane.setHgap(10);
                    resultsPane.setVgap(10);
                    resultsPane.setPadding(new Insets(10));

                    resultsPane.setStyle("-fx-background-color: linear-gradient(to right bottom,#dd5e89,#f7bb97)");
                    Label temperatureLabel = new Label("Temperature:");
                    temperatureLabel.setFont(Font.font("Times New Roman",  30));;
                    temperatureLabel.setStyle("-fx-text-fill: black;");

                    Label humidityLabel = new Label("Humidity:");
                    humidityLabel.setFont(Font.font("Times New Roman", 30));;
                    humidityLabel.setStyle("-fx-text-fill: black;");

                    Label windLabel = new Label("Wind:");
                    windLabel.setFont(Font.font("Times New Roman", 30));;
                    windLabel.setStyle("-fx-text-fill: black;");


                    TextField temperatureField = new TextField();
                    TextField humidityField = new TextField();
                    TextField windField = new TextField();

                    Button b=new Button("Back");



                    String currentGovernoratee = ((Button) event.getSource()).getText();

                    // Fetch weather data based on the button's label
                    JSONObject weatherData ;


                    weatherData = WeatherApp.getWeatherData(currentGovernoratee);







//                    JSONObject weatherData;
//                  weatherData = WeatherApp.getWeatherData("Sharqia");


                  /*
                  aswan
                  cairo
                  alex
                   */
//                    double temperature = (double) weatherData.get("temperature");
//                    temperatureField.setText(temperature + " C");
//
//
//                    double h = (double) weatherData.get("humidity");
//                   humidityField.setText(h + " C");
////
////                    double wind = (double) weatherData.get("windspeed");
////                    windField.setText(wind + " C");


                        double temperature = (double) weatherData.get("temperature");
                        temperatureField.setText(temperature + " C");

                         



                            long humidity = (long) weatherData.get("humidity");
                            humidityField.setText(humidity + " %");

                    double windspeed = (double) weatherData.get("windspeed");
                    windField.setText(windspeed+" ");



                    resultsPane.add(temperatureLabel, 0, 0);
                    resultsPane.add(humidityLabel, 0, 1);
                    resultsPane.add(windLabel, 0, 2);
                    resultsPane.add(temperatureField, 1, 0);
                    resultsPane.add(humidityField, 1, 1);
                    resultsPane.add(windField, 1, 2);
                    resultsPane.add(b,0,3,2,1);
//one stage may contain multiple scenes , but only one scene may be shown at a time , one scene can contain multiple panes simultaneously .


                    HBox iconBox = new HBox(20);
                    iconBox.setAlignment(Pos.CENTER);
                    iconBox.setPadding(new Insets(30));

                    //ImageView icon5 = createIconView("C:\\Users\\ascom\\IdeaProjects\\Project1\\src\\icons\\temprature.png");

                  // ImageView icon1 = createIconView(String.valueOf(getClass().getResource("/src/icons/temprature.png")));
                   ImageView icon1= createIconView("src\\icons\\temprature.png");

                    ImageView icon2 = createIconView("src\\icons\\humidity.png");
                    ImageView icon3 = createIconView("src\\icons\\wind.png");

                    iconBox.getChildren().addAll(icon1, icon2, icon3);
                    resultsPane.add(iconBox, 0, 4, 2, 1);

                    Scene resultsScene = new Scene(resultsPane, 800, 600);
                    GridPane.setHalignment(b, javafx.geometry.HPos.CENTER);

                    Stage resultsStage = new Stage();
                    resultsStage.setTitle("Results ");
                    resultsStage.setScene(resultsScene);



                    // Show the results stage
                    resultsStage.show();

                    // Close the main stage
                    primaryStage.close();



                    //event for the back to main scene button
                    b.setOnAction(e -> {
                        // close the results stage
                        resultsStage.close();

                        // show the main stage
                        primaryStage.show();
                    });
                });

                col++;
                if (col == IMAGES_PER_ROW) {
                    col = 0;
                    row += 2;

                    // Limit the number of rows
                    if (row >= NUMBER_OF_ROWS * 2) {
                        break;
                    }
                }
            }
        }

        Scene scene = new Scene(SP,910,500);
//        Scene scene = new Scene(GP,1600,900);
        primaryStage.setTitle("Welcome to our weather app!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    private ImageView createIconView(String iconFileDirectory) {
        Image icon = new Image(new File(iconFileDirectory).toURI().toString()); // In JavaFX, the Image class's constructor accepts either a direct URL string, an InputStream, or a file path.
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(50); // Set the width as needed
        iconView.setFitHeight(50); // Set the height as needed
        return iconView;
    }

    public static void main(String[] args) {
        launch();
    }
}
