package config;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Notification {

	public Notification() {
		// TODO Auto-generated constructor stub
	}
	public static void sendNotificationEmail(String to, String nomDestinataire, String nomAutre, double montant, boolean isEnvoyeur) {
	    final String username = "hnlouison@gmail.com";
	    final String password = "nhjqetbkcqrejpwr"; // Le mot de passe généré pour l'application

	    Properties prop = new Properties();
	    prop.put("mail.smtp.host", "smtp.gmail.com");
	    prop.put("mail.smtp.port", "587");
	    prop.put("mail.smtp.auth", "true");
	    prop.put("mail.smtp.starttls.enable", "true");

	    Session session = Session.getInstance(prop,
	        new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });

	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(username));
	        message.setRecipients(
	            Message.RecipientType.TO,
	            InternetAddress.parse(to)
	        );
	        
	        String subject = isEnvoyeur ? "Confirmation d'envoi" : "Notification de réception";
	        String messageContent = isEnvoyeur
	            ? "Cher " + nomDestinataire + ", vous avez envoyé la somme de " + montant + " à " + nomAutre + "."
	            : "Cher " + nomDestinataire + ", vous avez reçu la somme de " + montant + " de la part de " + nomAutre + ".";

	        message.setSubject(subject);
	        message.setText(messageContent);

	        Transport.send(message);

	        System.out.println("Email envoyé avec succès à " + to);

	    } catch (MessagingException e) {
	        System.out.println("Erreur lors de l'envoi de l'email à " + to);
	        e.printStackTrace();
	    }
	}
}
