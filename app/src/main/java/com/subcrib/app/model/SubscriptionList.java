package com.subcrib.app.model;

public class SubscriptionList {
    private String amount;
    private String subscription;
    private String description;
    private String payment;
    private String email;
    private String billingDate;
    private String billingPeriod;

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    private int imageResource;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public SubscriptionList(int id, String amount, String subscription, String description, String payment, String email, String billingDate, String billingPeriod) {
        this.id=id;
        this.amount = amount;
        this.subscription = subscription;
        this.description = description;
        this.payment = payment;
        this.email = email;
        this.billingDate = billingDate;
        this.billingPeriod = billingPeriod;

    }
    

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public String getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(String billingPeriod) {
        this.billingPeriod = billingPeriod;
    }
    
}
