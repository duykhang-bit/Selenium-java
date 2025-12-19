package utils;

import com.aventstack.extentreports.ExtentReports;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import com.frt.utils.EmailReporter;
import java.util.Date;

public class ReportSender {
    private static final String EMAIL_FROM = "leduykhang185@gmail.com";
    private static final String EMAIL_PASSWORD = "your-app-password";
    private static final String EMAIL_TO = "leduykhang185@gmail.com";

    public static void sendReport(ExtentReports extent) {
        try {
            sendEmailReport();
            System.out.println("Report sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void sendEmailReport() throws MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
        message.setSubject("Test Automation Report");

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Please find attached the test automation report.");

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(new File("test-output/ExtentReport.html"));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);
        Transport.send(message);
    }

    public void sendAutomationReport() {
        String reportPath = "path/to/your/report.html"; // Thay bằng đường dẫn thực tế đến file report
        String subject = "Automation Test Report - " + new Date();
        String body = "Hello Team,\n\nPlease find the latest automation test report attached.\n\nBest regards,\nYour Automation Script";

        EmailReporter.sendReport(reportPath, subject, body);
    }
} 