package isw.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class PropertiesISW extends Properties{ //Properties implements Hashtable

    //el objeto usado como key en la hashtable debe implementar el metodo equals


    //private static final long serialVersionUID = 1L;
    private static PropertiesISW prop;
    private static final String path = "properties.xml";


    private PropertiesISW() {
        try {
            System.out.println("Path: " + path); //archivo properties.xml
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

    public static PropertiesISW getInstance() { //Socket, cliente y customerDAO
        if (prop==null) {
            prop=new PropertiesISW();
        }
        return prop;

    }

}