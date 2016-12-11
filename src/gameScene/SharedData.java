package gameScene;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Дмитрий on 12/8/2016.
 */
public class SharedData {

    private final StringProperty stringStateProp = new SimpleStringProperty("");

    private final StringProperty stringLineProp = new SimpleStringProperty("");

    public StringProperty getStringStateProp() {
        return stringStateProp;
    }
    public StringProperty getStringLineProp() {
        return stringLineProp;
    }
}
