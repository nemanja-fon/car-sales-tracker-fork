/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_controllers;

import carsalesclient.controller.ClientController;
import carsalesclient.form.InvoicesTableForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_coordinator.Coordinator;
import carsalesclient.form.modes.AddFormMode;
import carsalesclient.form.tableModels.InvoicesTableModel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import domain.Car;
import domain.Company;
import domain.Customer;
import domain.Individual;
import domain.Invoice;
import domain.InvoiceItem;
import domain.User;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author user
 */
public class SeeAllInvoicesController {
    private final InvoicesTableForm invoicesTableForm;

    public SeeAllInvoicesController(InvoicesTableForm invoicesTableForm) {
        this.invoicesTableForm = invoicesTableForm;
        addListeners();
    }
    
    public void openForm(){
        prepareForm();
        invoicesTableForm.setVisible(true);
    }
    
    private void addListeners(){
        invoicesTableForm.getDatePicker().addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dce) {
                try {
                    String d = invoicesTableForm.getDatePicker().getDateStringOrEmptyString();
                    invoicesTableForm.getTxtDate().setText(d);
                    Invoice invoice = new Invoice();
                    invoice.setSearchCondition("date_of_issue");
                    invoice.setSearchConditionValue(d);
                    List<Invoice> invoices = ClientController.getInstance().searchInvoices(invoice);
                    if (invoices.isEmpty()) {
                        JOptionPane.showMessageDialog(invoicesTableForm, "The system cannot find invoices");
                        invoicesTableForm.getTblInvoices().setModel(new InvoicesTableModel(new ArrayList<>()));
                    }
                    else{
                        JOptionPane.showMessageDialog(invoicesTableForm, "The system has found invoices");
                        setTableModel(new InvoicesTableModel(invoices));
                    }
                    
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
                
            }
        });
        
        invoicesTableForm.btnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowId = invoicesTableForm.getTblInvoices().getSelectedRow();
                if (rowId < 0) {
                    JOptionPane.showMessageDialog(invoicesTableForm, "Please select invoice!");
                    return;
                }
                Invoice invoice = ((InvoicesTableModel) invoicesTableForm.getTblInvoices().getModel()).getInvoiceAt(rowId);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.INVOICE_DETAILS, invoice);
                if (invoice != null) {
                    JOptionPane.showMessageDialog(invoicesTableForm, "The system has found the invoice");
                }
                else
                    JOptionPane.showMessageDialog(invoicesTableForm, "The system cannot find the invoice");
                Coordinator.getInstance().openAddInvoiceForm(AddFormMode.DETAILS_FORM);
            }
        });
        
        invoicesTableForm.btnGeneratePdfAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = invoicesTableForm.getTblInvoices().getSelectedRow();
                if(row < 0){
                    JOptionPane.showMessageDialog(invoicesTableForm, "Please select invoice.");
                    return;
                }
                Invoice invoice = ((InvoicesTableModel) invoicesTableForm.getTblInvoices().getModel()).getInvoiceAt(row);
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    generatePdf(invoice, selectedFile.getAbsolutePath());
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("No file selected.");
                }
            }
        });
        
        invoicesTableForm.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoicesTableForm.dispose();
            }
        });
    }

    private void prepareForm() {
        try {
            DatePickerSettings settings = invoicesTableForm.getDatePicker().getSettings();
            settings.setColor(DatePickerSettings.DateArea.DatePickerTextDisabled, Color.YELLOW);
            settings.setDateRangeLimits(LocalDate.MIN, LocalDate.now());
            settings.setFormatForDatesCommonEra("dd.MM.yyyy");
            settings.setVisibleDateTextField(false);
            
            List<Invoice> invoices = ClientController.getInstance().getAllInvoices();
            setTableModel(new InvoicesTableModel(invoices));
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void setTableModel(InvoicesTableModel model){
        invoicesTableForm.getTblInvoices().setModel(model);
        invoicesTableForm.getTblInvoices().getColumn("Items").setCellRenderer(new ButtonCellRenderer());
        invoicesTableForm.getTblInvoices().getColumn("Items").setCellEditor(new ButtonSeeItemsCellEditor("See Items"));
        invoicesTableForm.getTblInvoices().getColumn("Salesperson").setCellRenderer(new ButtonCellRenderer());
        invoicesTableForm.getTblInvoices().getColumn("Salesperson").setCellEditor(new ButtonSeeSalespersonCellEditor("See salesperson"));
        invoicesTableForm.getTblInvoices().getColumn("Customer").setCellRenderer(new ButtonCellRenderer());
        invoicesTableForm.getTblInvoices().getColumn("Customer").setCellEditor(new ButtonSeeCustomerCellEditor("See customer"));
    }

    private void generatePdf(Invoice invoice, String path) {
        String inputPdf = "invoice_template_good_one2.pdf";
        String outputPdf = path + "\\" + invoice.getInvoiceNum() + ".pdf";

        try {
            PdfReader pdfReader = new PdfReader(inputPdf);
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(outputPdf));

            AcroFields formFields = pdfStamper.getAcroFields();

            formFields.setField("InvNum", invoice.getInvoiceNum().toString());
            formFields.setField("invDate", invoice.getDateOfIssue().toString());
            if (invoice.getCustomer() instanceof Individual) {
                Individual i = (Individual) invoice.getCustomer();
                formFields.setField("customer", i.getFirstName() + " " + i.getLastName());
            }
            else
                formFields.setField("customer", ((Company) invoice.getCustomer()).getCompanyName());
            formFields.setField("salesmanName", invoice.getUser().getFirstName()+" "+invoice.getUser().getLastName());
            formFields.setField("username", invoice.getUser().getUsername());
            formFields.setField("job", "Manager");
            formFields.setField("total", invoice.getTotalAmount().toString());
            
            pdfStamper.setFormFlattening(true);
            
            
            PdfContentByte canvas = pdfStamper.getOverContent(pdfReader.getNumberOfPages());
            PdfPTable table = new PdfPTable(4);
            float[] widths = {52f, 306f, 72f, 72f};
            table.setTotalWidth(widths);
            
            BaseFont baseFont = BaseFont.createFont(BaseFont.COURIER, BaseFont.WINANSI, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 10, Font.NORMAL, BaseColor.BLACK);
            PdfPCell cell;
            List<InvoiceItem> items = invoice.getInvoiceItems();
            for (InvoiceItem i : items) {
                cell = new PdfPCell(new Phrase(Integer.toString(i.getNum()), font));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorderWidth(0);
                cell.setFixedHeight(20.5f);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(i.getCar().getBrand() + " " + i.getCar().getModel(), font));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorderWidth(0);
                cell.setFixedHeight(20.5f);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(Double.toString(i.getPrice()), font));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorderWidth(0);
                cell.setFixedHeight(20.5f);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(Double.toString(i.getPrice()), font));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorderWidth(0);
                cell.setFixedHeight(20.5f);
                table.addCell(cell);
            }
            
            table.writeSelectedRows(0, -1, 55, 449, canvas);
            
            pdfStamper.close();
            pdfReader.close();
            
            JOptionPane.showMessageDialog(invoicesTableForm, "PDF generated successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    
    
    private class ButtonCellRenderer extends JPanel implements TableCellRenderer {
        private final JButton button;

        public ButtonCellRenderer() {
            setLayout(new GridBagLayout());
            button = new JButton();
            button.setMargin(new Insets(1, 7, 1, 7));
            button.setFocusable(true);
            add(button);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            button.setText(value == null ? "" : value.toString());
            if (isSelected){
                setBackground(UIManager.getColor("Table.selectionBackground"));;
                setForeground(UIManager.getColor("Table.selectionForeground"));
            }
            else {
                setBackground(UIManager.getColor("Table.background"));
                setForeground(UIManager.getColor("Table.foreground"));
            }
            return this;
        }
    }
    
    private class ButtonSeeItemsCellEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton button;
        private String label;

        public ButtonSeeItemsCellEditor(String label) {
            panel = new JPanel(new GridBagLayout());
            button = new JButton(label);
            button.setMargin(new Insets(1,7,1,7));
            button.setFocusable(true);
            panel.add(button);

            button.addActionListener((ActionEvent e) -> {
                try {
                    int row = (int) button.getClientProperty("row");
                    Invoice invoice = ((InvoicesTableModel) invoicesTableForm.getTblInvoices().getModel()).getInvoiceAt(row);
                    
                    Coordinator.getInstance().openInvoiceItemsTableForm(invoice.getInvoiceItems());
                    fireEditingStopped();
                } catch (Exception ex) {
                    System.out.println("Error: "+ ex.getMessage());
                    ex.printStackTrace();
                }
                
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = value.toString();
            button.setText(label);
            button.putClientProperty("row", row);
            if (isSelected) {
                panel.setBackground(UIManager.getColor("Table.selectionBackground"));
            } else {
                panel.setBackground(UIManager.getColor("Table.background"));
            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }
    
    private class ButtonSeeSalespersonCellEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton button;
        private String label;

        public ButtonSeeSalespersonCellEditor(String label) {
            panel = new JPanel(new GridBagLayout());
            button = new JButton(label);
            button.setMargin(new Insets(1,7,1,7));
            panel.add(button);

            button.addActionListener((ActionEvent e) -> {
                try {
                    int row = (int) button.getClientProperty("row");
                    User user = ((InvoicesTableModel) invoicesTableForm.getTblInvoices().getModel()).getInvoiceAt(row).getUser();
                    user.setSearchCondition("id");
                    user.setSearchConditionValue(user.getIdUser().toString());
                    user = ClientController.getInstance().searchUsers(user).getFirst();
                    Coordinator.getInstance().addParam(CoordinatorParamConsts.USER_DETAILS, user);
                    Coordinator.getInstance().openAddUserForm(AddFormMode.DETAILS_FORM);
                    fireEditingStopped();
                } catch (Exception ex) {
                    System.out.println("Error: " +ex.getMessage());
                    ex.printStackTrace();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = value.toString();
            button.setText(label);
            button.putClientProperty("row", row);
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }
    
    private class ButtonSeeCustomerCellEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton button;
        private String label;

        public ButtonSeeCustomerCellEditor(String label) {
            panel = new JPanel(new GridBagLayout());
            button = new JButton(label);
            button.setMargin(new Insets(1,7,1,7));
            panel.add(button);

            button.addActionListener((ActionEvent e) -> {
                try {
                    int row = (int) button.getClientProperty("row");
                    Customer customer = ((InvoicesTableModel) invoicesTableForm.getTblInvoices().getModel()).getInvoiceAt(row).getCustomer();
                    customer.setSearchCondition("id");
                    customer.setSearchConditionValue(customer.getIdCustomer().toString());
                    customer = ClientController.getInstance().searchCustomers(customer).getFirst();
                    Coordinator.getInstance().addParam(CoordinatorParamConsts.CUSTOMER_DETAILS, customer);
                    Coordinator.getInstance().openAddCustomerForm(AddFormMode.DETAILS_FORM);
                    fireEditingStopped();
                } catch (Exception ex) {
                    System.out.println("Error: " +ex.getMessage());
                    ex.printStackTrace();
                }
                
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = value.toString();
            button.setText(label);
            button.putClientProperty("row", row);
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }
}
