package lvsg.tools.YahooNfe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App {

	public static Properties props = new Properties();

	public static void main(String[] args) throws IOException {

		InputStream input = App.class.getClassLoader().getResourceAsStream("config.properties");
		props.load(input);

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new VerifyEmail(), 0L, Long.valueOf(props.getProperty("tempo_atualizacao")), TimeUnit.HOURS);
	}
}
