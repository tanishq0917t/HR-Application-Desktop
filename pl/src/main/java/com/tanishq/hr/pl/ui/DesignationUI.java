package com.tanishq.hr.pl.ui;
import com.tanishq.hr.pl.model.*;
import com.tanishq.hr.bl.exceptions.*;
import com.tanishq.hr.bl.interfaces.pojo.*;
import com.tanishq.hr.bl.pojo.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JLabel titleLabel;
private JLabel searchErrorLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JTable designationTable;
private DesignationModel designationModel;
private Container container;
private JScrollPane scrollPane;
private DesignationPanel designationPanel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon logoIcon;
//*****************************************************************************************
public DesignationUI()
{
super("Designation Manager by Tanishq Rawat");
initComponents();
addListeners();
setAppearance();
setViewMode();
designationPanel.setViewMode();
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
//*****************************************************************************************
private void initComponents()
{
logoIcon=new ImageIcon("C://JavaProjects//hr//pl//src//main//resources//icons//logo.png");
setIconImage(logoIcon.getImage());
designationModel=new DesignationModel();
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton(); //done
clearSearchTextFieldButton.setIcon(new ImageIcon("C://JavaProjects//hr//pl//src//main//resources//icons//cross.png"));
searchErrorLabel=new JLabel("");
designationTable=new JTable(designationModel);
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
}
//*****************************************************************************************
public void setAppearance()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
designationTable.setFont(dataFont);
designationTable.setRowHeight(35);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(20);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(400);
JTableHeader header=designationTable.getTableHeader();
header.setFont(columnHeaderFont);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
designationPanel=new DesignationPanel();
container.setLayout(null);
int lm=0,tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);
searchLabel.setBounds(lm+10,tm+60,100,30);
searchErrorLabel.setBounds(lm+420,tm+30,100,20);
searchTextField.setBounds(lm+100,tm+60,400,30);
clearSearchTextFieldButton.setBounds(lm+505,tm+60,30,30);
scrollPane.setBounds(lm+10,tm+100,565,340);
designationPanel.setBounds(lm+10,tm+420,565,200);
container.add(titleLabel);
container.add(searchLabel);
container.add(searchErrorLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);
container.add(designationPanel);
int w=600,h=680;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
}
//***************************************************************************************
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
designationTable.getSelectionModel().addListSelectionListener(this);
}
//***************************************************************************************
private void searchDesignation()
{
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0)return;
int rowIndex;
try
{
rowIndex=designationModel.indexOfTitle(title,true);
}catch(BLException ble)
{
searchErrorLabel.setText("Not Found");
return;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}
//***************************************************************************************
public void changedUpdate(DocumentEvent ev)
{
searchDesignation();
}
//***************************************************************************************
public void removeUpdate(DocumentEvent ev)
{
searchDesignation();
}
//***************************************************************************************
public void insertUpdate(DocumentEvent ev)
{
searchDesignation();
}
//*****************************************************************************************
public void valueChanged(ListSelectionEvent ev)
{
//System.out.println("Value changed got called");
int selectedRowIndex=designationTable.getSelectedRow();
try
{
DesignationInterface designation=designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}catch(BLException ble)
{
designationPanel.clearDesignation();
}
}
//***************************************************************************************
private void setViewMode()
{
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
designationTable.setEnabled(true);
}
}
//***************************************************************************************
private void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
//***************************************************************************************
private void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
//***************************************************************************************
private void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
//***************************************************************************************
private void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
//***************************************************************************************
//inner class
public class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTextFieldButton;
private JButton addButton;
private JButton deleteButton;
private JButton editButton;
private JButton cancelButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;
private DesignationInterface designation;
//***************************************************************************************
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(170,170,170)));
initComponents();
setAppearance();
addListeners();
}
//***************************************************************************************
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleLabel.setText(designation.getTitle());
}
//***************************************************************************************
public void clearDesignation()
{
this.designation=null;
titleLabel.setText("");
}
//***************************************************************************************
private void initComponents()
{
designation=null;
titleCaptionLabel=new JLabel("Designation");
titleLabel=new JLabel("");
titleTextField=new JTextField();
clearTextFieldButton=new JButton("x");
buttonsPanel=new JPanel();
addButton=new JButton();
addButton.setIcon(new ImageIcon("C://JavaProjects//hr//pl//src//main//resources//icons//add.jpg"));
editButton=new JButton();
editButton.setIcon(new ImageIcon("C://JavaProjects//hr//pl//src//main//resources//icons//edit.jpg"));
deleteButton=new JButton();
deleteButton.setIcon(new ImageIcon("C://JavaProjects//hr//pl//src//main//resources//icons//delete.jpg"));
cancelButton=new JButton();
cancelButton.setIcon(new ImageIcon("C://JavaProjects//hr//pl//src//main//resources//icons//cancel.jpg"));
exportToPDFButton=new JButton();
exportToPDFButton.setIcon(new ImageIcon("C://JavaProjects//hr//pl//src//main//resources//icons//pdf.jpg"));
}
//***************************************************************************************
private void setAppearance()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm=0,tm=0;
titleCaptionLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+125,tm+20,400,30);
titleTextField.setBounds(lm+125,tm+20,350,30);
clearTextFieldButton.setBounds(lm+480,tm+20,30,30);
buttonsPanel.setBounds(50,tm+80,465,75);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
addButton.setBounds(70,12,50,50);
editButton.setBounds(140,12,50,50);
cancelButton.setBounds(210,12,50,50);
deleteButton.setBounds(280,12,50,50);
exportToPDFButton.setBounds(350,12,50,50);
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(titleTextField);
add(titleLabel);
add(clearTextFieldButton);
add(buttonsPanel);
}
//***************************************************************************************
private boolean addDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation Required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d;
d=new Designation();
d.setTitle(title);
try
{
designationModel.add(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException ble)
{
//nothing to do
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException ble)
{
if(ble.hasGenericException()) JOptionPane.showMessageDialog(this,ble.getGenericException());
else
{
if(ble.hasException("title"))JOptionPane.showMessageDialog(this,ble.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
//***************************************************************************************
private boolean updateDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation Required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d;
d=new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
try
{
designationModel.update(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException ble)
{
//nothing to do
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException ble)
{
if(ble.hasGenericException()) JOptionPane.showMessageDialog(this,ble.getGenericException());
else
{
if(ble.hasException("title"))JOptionPane.showMessageDialog(this,ble.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
//***************************************************************************************
private void removeDesignation()
{
try
{
String title=this.designation.getTitle();
int option=JOptionPane.showConfirmDialog(this,"Delete "+title+"?","Confirmation of Deletion",JOptionPane.YES_NO_OPTION);
if(option==JOptionPane.NO_OPTION) return;
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" deleted");
}catch(BLException ble)
{
if(ble.hasGenericException()) JOptionPane.showMessageDialog(this,ble.getGenericException());
else
{
if(ble.hasException("title")) JOptionPane.showMessageDialog(this,ble.getException("title"));
}
}
}
//***************************************************************************************
private void addListeners()
{
this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{




JFileChooser jfc=new JFileChooser();
jfc.setAcceptAllFileFilterUsed(false);
jfc.setCurrentDirectory(new File("."));
jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){
public boolean accept(File file)
{
if(file.isDirectory())  return true;
if(file.getName().endsWith(".pdf")) return true;
return false;
}
public String getDescription()
{
return "pdf files";
}
});



int selectedOption=jfc.showSaveDialog(DesignationUI.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{

try
{
File selectedFile=jfc.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith("."))pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false)pdfFile+=".pdf";
File file= new File(pdfFile);
File parent=new File(file.getParent());

if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect Path: "+file.getAbsolutePath());
return;
}

designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data exported to PDF: "+file.getAbsolutePath());
}catch(BLException ble)
{
if(ble.hasGenericException())
{
JOptionPane.showMessageDialog(DesignationUI.this,ble.getGenericException());
}
}
catch(Exception e)
{
System.out.println(e);
}
}//if ends






}
});

this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)setAddMode();
else
{
if(addDesignation())setViewMode();
}
}
});
this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)setEditMode();
else
{
if(updateDesignation()) setViewMode();
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});
this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setDeleteMode();
}
});
this.clearTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
titleTextField.setText("");
}
});
}
//***************************************************************************************
private void setViewMode()
{
DesignationUI.this.setViewMode();
this.addButton.setIcon(new ImageIcon("C://JavaProjects//hr//pl//src//main//resources//icons//add.jpg"));
this.editButton.setIcon(new ImageIcon("C://JavaProjects//hr//pl//src//main//resources//icons//edit.jpg"));
this.titleTextField.setVisible(false);
this.titleLabel.setVisible(true);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
this.clearTextFieldButton.setVisible(false);
if(designationModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}
//***************************************************************************************
private void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.clearTextFieldButton.setVisible(true);
addButton.setIcon(new ImageIcon("C://JavaProjects//hr//pl//src//main//resources//icons//save.png"));
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
this.titleTextField.requestFocus();
}
//***************************************************************************************
private void setEditMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select Designation to Edit");
return;
}
DesignationUI.this.setEditMode();
this.titleTextField.setText(this.designation.getTitle());
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.titleTextField.requestFocus();
this.clearTextFieldButton.setVisible(true);
editButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/update.jpg")));
addButton.setEnabled(false);
editButton.setEnabled(true);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
//***************************************************************************************
private void setDeleteMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select Designation to Delete");
return;
}
DesignationUI.this.setDeleteMode();
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
removeDesignation();
DesignationUI.this.setViewMode();
this.setViewMode();
}
//***************************************************************************************
private void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
//***************************************************************************************
}//inner class ends
}//class ends
