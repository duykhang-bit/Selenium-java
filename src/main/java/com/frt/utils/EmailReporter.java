package com.frt.utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public class EmailReporter {

    private static final String SMTP_HOST = "YOUR_SMTP_HOST"; // e.g., smtp.gmail.com
    private static final String SMTP_PORT = "YOUR_SMTP_PORT"; // e.g., 587 or 465
    private static final String SENDER_EMAIL = "YOUR_EMAIL_ADDRESS";
    private static final String SENDER_PASSWORD = "YOUR_EMAIL_PASSWORD"; // Or app-specific password
    private static final String RECIPIENT_EMAIL = "RECIPIENT_EMAIL_ADDRESS";

    public static void sendReport(String reportPath, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Use TLS
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT_EMAIL));
            message.setSubject(subject);
            message.setSentDate(new Date());

            // Create the email body and attachment
            Multipart multipart = new MimeMultipart();

            // Text body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);

            // Attachment body part
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            File reportFile = new File(reportPath);
            DataSource source = new FileDataSource(reportFile);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(reportFile.getName());
            multipart.addBodyPart(attachmentBodyPart);

            // Set the multipart as email content
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Report email sent successfully to " + RECIPIENT_EMAIL);

        } catch (MessagingException e) {
            System.out.println("Error sending report email: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 