import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox
{
    static Stage confirm;
    static Scene scene;

    public static Boolean display(String title)
    {
        Main obj = new Main();

        confirm = new Stage();
        confirm.setTitle("Waiting...");

        confirm.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label(title);

        Button no = new Button("Nope");
        no.setOnAction(e -> {

            confirm.close();
            obj.result = false;

        });

        Button yes = new Button("Yeahhh!");
        yes.setOnAction(e -> {

            confirm.close();
            obj.result = true;

        });

        HBox buttonStack = new HBox();
        buttonStack.getChildren().addAll(no,yes);
        buttonStack.setAlignment(Pos.CENTER_RIGHT);
        buttonStack.setSpacing(10);

        VBox fullStack = new VBox();
        fullStack.getChildren().addAll(label,buttonStack);
        fullStack.setAlignment(Pos.CENTER);
        fullStack.setSpacing(20);

        BorderPane mainBP = new BorderPane();
        mainBP.setCenter(fullStack);
        mainBP.setPadding(new Insets(20));

        scene = new Scene(mainBP,300,100, Color.RED);

        scene.getStylesheets().add("changeForAlert.css");

        confirm.setScene(scene);
        confirm.setResizable(false);
        confirm.setAlwaysOnTop(true);
        confirm.toFront();
        confirm.showAndWait();

        return obj.result;
    }
}