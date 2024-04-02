package it.epicode.ProgettoCapstone.config;

import it.epicode.ProgettoCapstone.payloads.SendEmailModel;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    private String mailgunAPIKey;

    private String domainName;

    public EmailSender(@Value("${mailgun.apikey}") String mailgunAPIKey,
                       @Value("${mailgun.domainname}") String domainName) {
        this.mailgunAPIKey = mailgunAPIKey;
        this.domainName = domainName;
    }

    public void sendEmail(SendEmailModel model) {
        Unirest.post("https://api.mailgun.net/v3/" + domainName + "/messages")
                .basicAuth("api", mailgunAPIKey)
                .queryString("from", "AntonioVetrate <antoniovetrate@fastwebnet.it>")
                .queryString("to", "antoniosimonetti_005@fastwebnet.it")
                .queryString("subject", "Form Message")
                .queryString("text", "Hai ricevuto una mail da " + model.contactName() + " : " + model.message() + "Il numero dell'utente é: " + model.phone() + "e la sua email di contatto é" + model.email())
                .asJsonAsync(response -> {
                    if (response.getStatus() == 200) {
                        System.out.println("Sent!");
                    } else {
                        System.out.println("Error sending email: " + response.getStatus() + " " + response.getStatusText());
                        System.out.println("Error details: " + response.getBody().getObject().getString("message"));
                    }
                });
    }
}