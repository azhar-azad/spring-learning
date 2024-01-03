package com.azad.prospring5.ch3.setter_injection;

public interface NewsletterSender {
    void setSmtpServer(String smtpServer);
    String getSmtpServer();
    void setFromAddress(String formAddress);
    String getFromAddress();
    void send();
}
