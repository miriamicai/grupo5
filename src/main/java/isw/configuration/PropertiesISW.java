package isw.configuration;

//import java.io.FileInputStream; //unused import
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

//import src.main.resources.*;

public class PropertiesISW extends Properties{ //Properties implements Hashtable

    /**
     * To successfully store and retrieve objects from a hashtable, the objects
     * used as keys must implement the hashCode method and the equals method.
     */



    /**
     *
     */


    //private static final long serialVersionUID = 1L;
    private static PropertiesISW prop;
    private static final String path = "properties.xml"; //se puede conectar directamente


    private PropertiesISW() {
        try {
            System.out.println("Path: " + path);
            this.loadFromXML(getClass().getClassLoader().getResourceAsStream(path));
        } catch (InvalidPropertiesFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
    }

    public static PropertiesISW getInstance() {
        if (prop==null) {
            prop=new PropertiesISW();
        }
        return prop;

    }

}