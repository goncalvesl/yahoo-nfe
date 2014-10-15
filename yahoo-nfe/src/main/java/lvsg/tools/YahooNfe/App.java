package lvsg.tools.YahooNfe;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {
	private static final Logger	LOG	= LoggerFactory.getLogger(App.class);
	public static String		dir;
	public static String		user;
	public static String		pass;

	public static void main(String[] args) throws IOException {
		dir = args[0];
		user = args[1];
		pass = args[2];
		LOG.info("Diretório: " + dir);
		LOG.info("user: " + user);
		LOG.info("pass: " + pass);

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new VerifyEmail(), 0L, 1, TimeUnit.HOURS);
	}
}
