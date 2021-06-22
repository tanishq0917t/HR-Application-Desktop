package com.tanishq.hr.pl.model;
import com.tanishq.hr.bl.interfaces.managers.*;
import com.tanishq.hr.bl.interfaces.pojo.*;
import com.tanishq.hr.bl.managers.*;
import com.tanishq.hr.bl.pojo.*;
import com.tanishq.hr.bl.exceptions.*;
import java.util.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.io.image.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.borders.*;
import javax.swing.table.*;
public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations;
private String[] columnTitle;
private DesignationManagerInterface designationManager;
public DesignationModel()
{
this.populateDataStructures();
}
private void populateDataStructures()
{
this.columnTitle=new String[2];
this.columnTitle[0]="S.No";
this.columnTitle[1]="Designation";
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException ble)
{
//???
}
Set<DesignationInterface> blDesignations=designationManager.getDesignations();
this.designations=new LinkedList<>();
for(DesignationInterface designation:blDesignations)
{
this.designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public int getRowCount()
{
return designations.size();
}
public int getColumnCount()
{
return this.columnTitle.length;
}
public String getColumnName(int columnIndex)
{
return columnTitle[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
return this.designations.get(rowIndex).getTitle();
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0)return Integer.class;
return String.class;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}

//****************************************************************************************

public void add(DesignationInterface designation) throws BLException
{
designationManager.addDesignation(designation);
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}//add ends

//****************************************************************************************

public int indexOfDesignation(DesignationInterface designation) throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException ble=new BLException();
ble.setGenericException("Invalid Designation: "+designation.getTitle());
throw ble;
}

//****************************************************************************************

public int indexOfTitle(String title,boolean partialLeftSearch) throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase()))
{
return index;
}
}
else
{
if(d.getTitle().equalsIgnoreCase(title))
{
return index;
}
}
index++;
}
BLException ble=new BLException();
ble.setGenericException("Invalid Title: "+title);
throw ble;
}

//****************************************************************************************

public void update(DesignationInterface designation) throws BLException
{
designationManager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}


//****************************************************************************************

public void remove(int code) throws BLException
{
designationManager.removeDesignation(code);
Iterator<DesignationInterface>iterator=this.designations.iterator();
int index=0;
while(iterator.hasNext())
{
if(iterator.next().getCode()==code)break;
index++;
}
if(index==this.designations.size())
{
BLException ble=new BLException();
ble.setGenericException("Invalid Designation Code: "+code);
throw ble;
}
this.designations.remove(index);
fireTableDataChanged();
}
public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=this.designations.size())
{
BLException ble=new BLException();
ble.setGenericException("Invalid Index: "+index);
throw ble;
}
return this.designations.get(index);
}

//******************************************************************************************
public void exportToPDF(File file) throws BLException
{
boolean newPage=true;
try
{
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
int pageSize=5;
int pageNumber=1;
Document document=new Document(pdfDocument);
PdfFont boldFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont font=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
int srNo=1;
String title="Designation";
float columnWidths[]={2,7};
Table table=new Table(UnitValue.createPercentArray(columnWidths));
table.setWidth(UnitValue.createPercentValue(100));
Paragraph cellPara;
Cell cell;
int r=0;
int cellCount=0;
int xx=0;
while(xx<this.designations.size())
{

if(newPage==true)
{
Paragraph p1=new Paragraph("");
Image img=new Image(ImageDataFactory.create("C://JavaProjects//hr//pl//src//main//resources//icons//logo.png"));
p1.setTextAlignment(TextAlignment.LEFT);
Paragraph p3=new Paragraph();
p1.add(img);
p1.add("                  TH Corporation").setFont(boldFont).setFontSize(18);
p3.add(String.valueOf(pageNumber)).setTextAlignment(TextAlignment.RIGHT);
Paragraph p2=new Paragraph("Designations");
p2.setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(16);
document.add(p3);
document.add(p1);
document.add(p2);
newPage=false;
table.addHeaderCell(new Cell().add(new Paragraph("Sr.No.")));
table.addHeaderCell(new Cell().add(new Paragraph("Designations")).setFont(font).setFontSize(12));
}
for(int c=0;c<2;c++)
{
if(c==0) cellPara=new Paragraph(String.valueOf(xx+1));
else cellPara=new Paragraph(this.designations.get(xx).getTitle()).setFont(font).setFontSize(10);
cell=new Cell();
cell.add(cellPara);
table.addCell(cell);
}
cellCount++;
if(cellCount%5==0 || this.designations.size()==(xx+1) )
{
cellCount=0;
document.add(table);
table=new Table(UnitValue.createPercentArray(columnWidths));
table.setWidth(UnitValue.createPercentValue(100));
Paragraph name=new Paragraph("Software made by: Tanishq Rawat");
name.setFont(font);
name.setFontSize(12);
document.add(name);
if(xx<this.designations.size()) 
{
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}

r++;
xx++;
System.out.println("Designation number: "+xx+" moved to Pdf");
}//while



document.close();
}catch(Exception e)
{
BLException blException=new BLException();
blException.setGenericException(e.getMessage());
throw blException;
}
}
}//class ends
