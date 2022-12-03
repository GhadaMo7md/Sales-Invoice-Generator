package com.sales.controller;

import com.sales.model.Invoices;
import com.sales.model.InvTable;
import com.sales.model.InvItems;
import com.sales.model.ItemsTable;
import com.sales.view.InvDialog;
import com.sales.view.InvGUI;
import com.sales.view.ItemsDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Guide implements ActionListener, ListSelectionListener {

    private InvGUI frame;
    private InvDialog invoiceDialog;
    private ItemsDialog lineDialog;

    public Guide(InvGUI frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
        switch (actionCommand) {
            case "Load Invoice":
                loadF();
                break;
            case "Save Invoice":
                saveF();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createInvoiceOK":
                createInvoiceOK();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            Invoices currentInvoice = frame.getInvoices().get(selectedIndex);
            frame.getInvoiceNumLabel().setText("" + currentInvoice.getNum());
            frame.getInvoiceDateLabel().setText(currentInvoice.getDate());
            frame.getCustomerNameLabel().setText(currentInvoice.getCustomer());
            frame.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            ItemsTable linesTableModel = new ItemsTable(currentInvoice.getItems());
            frame.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    private void loadF() {
        JFileChooser fileChooser = new JFileChooser();
        try {
            int connect = fileChooser.showOpenDialog(frame);
            if (connect == JFileChooser.APPROVE_OPTION) {
                File invFile = fileChooser.getSelectedFile();
                Path invoice = Paths.get(invFile.getAbsolutePath());
                List<String> InvItems = Files.readAllLines(invoice);
                ArrayList<Invoices> invArray = new ArrayList<>();
                for (String invItem : InvItems) {
                    try {
                        String[] headerParts = invItem.split(",");
                        int invNum = Integer.parseInt(headerParts[0]);
                        String invDate = headerParts[1];
                        String custName = headerParts[2];

                        Invoices inv = new Invoices(invNum, invDate, custName);
                        invArray.add(inv);
                    } catch (Exception y) {
                        JOptionPane.showMessageDialog(frame,
                                "Error in Item format", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        
                    }
                }
                 
          connect = fileChooser.showOpenDialog(frame);
                if(connect == JFileChooser.APPROVE_OPTION){
                   File itemFile = fileChooser.getSelectedFile();
                   Path items = Paths.get(itemFile.getAbsolutePath());
                   List<String> itemItems = Files.readAllLines(items);
                  for (String itemItem : itemItems) {
                     
                     try{String[] itemComponents = itemItem.split(",");
                    int invNumber = Integer.parseInt( itemComponents[0]);
                    String itN = itemComponents[1];
                    double itP = Double.parseDouble(itemComponents[2]);
                    int c =Integer.parseInt(itemComponents[3]);
                    Invoices i = null;
                    for(Invoices inv :invArray){
                    
                      if(inv.getInvnum() == invNumber){
                       
                          i = inv;
                        break;
                      }  
                    }
                  InvItems item =new InvItems(itN,itP,c,i);
                  i.getItems().add(item);
                     }catch(Exception k){
            JOptionPane.showMessageDialog(frame,
                    "Error in Item Format",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                     }    
                  }
                    
                }
             frame.setInv(invArray);
             InvTable invTable = new InvTable(invArray);
             frame.setInvTable(invTable);
             frame.getIT().setModel(invTCon);
             frame.getInvTCon().fireTableDataChanged();
      
      
      }      
        
      }catch (Exception exeption){exeption.printStackTrace();
                       JOptionPane.showMessageDialog(frame, 
                               "Error in File Reading", 
                               "Error", 
                               JOptionPane.ERROR_MESSAGE);
                    } 
    }
//                System.out.println("Check point");
//                connect = fileChooser.showOpenDialog(frame);
//                if (connect == JFileChooser.APPROVE_OPTION) {
//                    File lineFile = fileChooser.getSelectedFile();
//                    Path linePath = Paths.get(lineFile.getAbsolutePath());
//                    List<String> lineLines = Files.readAllLines(linePath);
//                    System.out.println("Lines have been read");
//                    for (String lineLine : lineLines) {
//                        try {
//                            String lineParts[] = lineLine.split(",");
//                            int invoiceNum = Integer.parseInt(lineParts[0]);
//                            String itemName = lineParts[1];
//                            double itemPrice = Double.parseDouble(lineParts[2]);
//                            int count = Integer.parseInt(lineParts[3]);
//                            Invoices inv = null;
//                            for (Invoices invoice : invArray) {
//                                if (invoice.getNum() == invoiceNum) {
//                                    inv = invoice;
//                                    break;
//                                }
//                            }
//
//                            InvItems line = new InvItems(itemName, itemPrice, count, inv);
//                            inv.getInvItems().add(line);
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                            JOptionPane.showMessageDialog(frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
//                        }
//                    }
//                    System.out.println("Check point");
//                }
//                frame.setInvoices(invArray);
//                InvTable invoicesTableModel = new InvTable(invArray);
//                frame.setInvoicesTableModel(invoicesTableModel);
//                frame.getInvoiceTable().setModel(invoicesTableModel);
//                frame.getInvoicesTableModel().fireTableDataChanged();
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(frame, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
//        }
    }

    private void saveF() {
        ArrayList<Invoices> invoices = frame.getInvoices();
        String headers = "";
        String lines = "";
        for (Invoices invoice : invoices) {
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (InvItems line : invoice.getInvItems()) {
                String lineCSV = line.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvoice() {
        invoiceDialog = new InvDialog(frame);
        invoiceDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            frame.getInvoices().remove(selectedRow);
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItem() {
        lineDialog = new ItemsDialog(frame);
        lineDialog.setVisible(true);
    }

    private void deleteItem() {
        int selectedRow = frame.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            ItemsTable linesTableModel = (ItemsTable) frame.getLineTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createInvoiceOK() {
        String date = invoiceDialog.getInvDateField().getText();
        String customer = invoiceDialog.getCustNameField().getText();
        int num = frame.getNextInvoiceNum();
        try {
            String[] dateParts = date.split("-");  // "22-05-2013" -> {"22", "05", "2013"}  xy-qw-20ij
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Invoices invoice = new Invoices(num, date, customer);
                    frame.getInvoices().add(invoice);
                    frame.getInvoicesTableModel().fireTableDataChanged();
                    invoiceDialog.setVisible(false);
                    invoiceDialog.dispose();
                    invoiceDialog = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void createLineOK() {
        String item = lineDialog.getItemNameField().getText();
        String countStr = lineDialog.getItemCountField().getText();
        String priceStr = lineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            Invoices invoice = frame.getInvoices().get(selectedInvoice);
            InvItems line = new InvItems(item, price, count, invoice);
            invoice.getInvItems().add(line);
            ItemsTable linesTableModel = (ItemsTable) frame.getLineTable().getModel();
            //linesTableModel.getInvItems().add(line);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

}
