package net.example.plantsearchrest.service;

public interface MailSender {
    void send(String to, String subject, String message);
}
