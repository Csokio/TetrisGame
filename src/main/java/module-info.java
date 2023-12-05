module com.example.tetrisgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.tetrisgame to javafx.fxml;
    exports com.example.tetrisgame;
    exports com.example.tetrisgame.blocks;
    opens com.example.tetrisgame.blocks to javafx.fxml;
}