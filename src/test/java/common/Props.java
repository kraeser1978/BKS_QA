package common;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Props {
    private static Logger logger = Logger.getLogger(Props.class.getSimpleName());
    private static HashMap<String,String> props = new HashMap();

    public Props(String contents) {
        readPropertiesFile(contents);
    }

    private void readPropertiesFile (String contents) {
        Properties properties =  new Properties();
//        FileInputStream fileInputStream = null;
        try {
//            fileInputStream = new FileInputStream(fileName);
            properties.load(new StringReader(contents));
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE,"ОШИБКА при чтении файла параметров");
        };
        for (Map.Entry<Object,Object> entry: properties.entrySet()){
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            props.put(key,value);
        }
    }

    public String getPropertyValue(String property){
        return this.props.get(property);
    }

}
