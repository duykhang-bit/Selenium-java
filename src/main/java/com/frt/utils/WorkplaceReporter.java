package com.frt.utils;

import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WorkplaceReporter {
    private static final String ACCESS_TOKEN = "YOUR_WORKPLACE_ACCESS_TOKEN";
    private static final String GROUP_ID = "YOUR_WORKPLACE_GROUP_ID";
    private static final String WORKPLACE_API_URL = "https://graph.facebook.com/v18.0/" + GROUP_ID + "/feed";

    public static void sendReport(String reportPath, String message) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Read the report file
            File reportFile = new File(reportPath);
            if (!reportFile.exists()) {
                System.out.println("Report file not found: " + reportPath);
                return;
            }

            // Create multipart request
            HttpPost request = new HttpPost(WORKPLACE_API_URL);
            request.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);

            // Build multipart entity
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("message", message, ContentType.TEXT_PLAIN);
            builder.addBinaryBody("file", reportFile, ContentType.APPLICATION_OCTET_STREAM, reportFile.getName());
            request.setEntity(builder.build());

            // Send request
            org.apache.hc.core5.http.ClassicHttpResponse response = 
                (org.apache.hc.core5.http.ClassicHttpResponse) client.execute(request);

            if (response.getCode() == 200) {
                System.out.println("Report sent successfully to Workplace!");
            } else {
                System.out.println("Failed to send report. Status code: " + response.getCode());
                System.out.println("Response: " + IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8));
            }

        } catch (IOException e) {
            System.out.println("Error sending report to Workplace: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 