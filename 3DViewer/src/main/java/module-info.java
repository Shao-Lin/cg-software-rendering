module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;


    opens com.graphics.rendering to javafx.fxml, org.junit.jupiter.api;
    exports com.graphics.rendering;
    exports com.graphics.rendering.math.matrix;
    exports com.graphics.rendering.math.vector;
    exports com.graphics.rendering.models_operations;
    exports com.graphics.rendering.model;

}