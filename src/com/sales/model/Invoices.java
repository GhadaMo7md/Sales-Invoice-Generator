
package com.sales.model;

import java.util.ArrayList;

public class Invoices {
    private int num;
    private String date;
    private String customer;
    private ArrayList<InvItems> items;
    
    public Invoices() {
    }

    public Invoices(int num, String date, String customer) {
        this.num = num;
        this.date = date;
        this.customer = customer;
    }

    public double getInvoiceTotal() {
        double total = 0.0;
        for (InvItems item : getItems()) {
            total += item.getLineTotal();
        }
        return total;
    }
    public ArrayList<InvItems> getItems() {
        
        if(items == null){
        items = new ArrayList<>();
        }
        return items;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Invoice{" + "num=" + num + ", date=" + date + ", customer=" + customer + '}';
    }
    
    public String getAsCSV() {
        return num + "," + date + "," + customer;
    }
    
}
