package lvsg.tools.YahooNfe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerifyEmail implements Runnable {
	private static final String IMAP = "imap.mail.yahoo.com";
	private static final Logger LOG = LoggerFactory.getLogger(VerifyEmail.class);
	
	public void run() {
		try {
			Session session = Session.getDefaultInstance(new Properties(), null);

			Store store = session.getStore("imaps");
			store.connect(IMAP, App.user, App.pass);

			Folder inbox = store.getFolder("inbox");
			inbox.open(Folder.READ_WRITE);
			LOG.info("Conectado");
			
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message[] messages = inbox.search(unseenFlagTerm);
			int messageCount = messages.length;
			LOG.info("Mensagens a serem procesadas: " + messageCount);
			
			for (Message message : messages) {
				
				Multipart multipart = (Multipart) message.getContent();
				for (int i = 0; i < multipart.getCount(); i++) {
					BodyPart bodyPart = multipart.getBodyPart(i);
					if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) && (bodyPart.getFileName() == null)) {
						continue; // dealing with attachments only
					}

					if (!bodyPart.getFileName().endsWith(".xml"))
						continue;
					System.out.println(bodyPart.getFileName());
					
					//message.setFlag(Flags.Flag.SEEN, true);
					InputStream is = bodyPart.getInputStream();
					File f = new File(App.dir, bodyPart.getFileName());
					System.out.println("Salvando Arquivo" + f.getAbsolutePath());
					FileOutputStream fos = new FileOutputStream(f);
					byte[] buf = new byte[4096];
					int bytesRead;
					while ((bytesRead = is.read(buf)) != -1) {
						fos.write(buf, 0, bytesRead);
					}
					fos.close();
				}
			}
			inbox.close(true);
			store.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
