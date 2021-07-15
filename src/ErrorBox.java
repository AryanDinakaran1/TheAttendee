
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorBox
{
    static Stage confirm;

    public static Boolean display(String title)
    {
        Main obj = new Main();

        confirm = new Stage();
        confirm.setTitle("Oh..This is Embarassing");

        confirm.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label(title);

        Button okButton = new Button("Okk");
        okButton.setOnAction(e -> {

            confirm.close();
            obj.result = false;

        });

        HBox buttonStack = new HBox();
        buttonStack.getChildren().addAll(okButton);
        buttonStack.setAlignment(Pos.CENTER_RIGHT);
        buttonStack.setSpacing(10);

        VBox fullStack = new VBox();
        fullStack.getChildren().addAll(label,buttonStack);
        fullStack.setAlignment(Pos.CENTER);
        fullStack.setSpacing(20);

        BorderPane mainBP = new BorderPane();
        mainBP.setCenter(fullStack);
        mainBP.setPadding(new Insets(20));

        Scene scene = new Scene(mainBP,300,100);

        confirm.setScene(scene);
        confirm.setResizable(false);
        confirm.setAlwaysOnTop(true);
        confirm.toFront();
        confirm.showAndWait();

        return obj.result;
    }
}