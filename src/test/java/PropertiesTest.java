import org.example.bot.Properties;

import java.nio.file.Paths;

public class PropertiesTest {

    public static void main(String[] args) {
        try {
            Properties properties = Properties.load(Paths.get("config.properties"));
            System.out.println(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
