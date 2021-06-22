package com.tanishq.hr.dl.dao;
import com.tanishq.hr.dl.dto.*;
import com.tanishq.hr.dl.interfaces.dto.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.exceptions.*;
import java.io.*;
import java.util.*;
public class DesignationDAO implements DesignationDAOInterface
{
private final static String FILE_NAME="designation.data";
//******************************************************************************************

public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation in null");
String title=designationDTO.getTitle();
if(title==null) throw new DAOException("Designation in null");
title=title.trim();
if(title.length()==0) throw new DAOException("Length of title cannot be zero");
try
{
File file=new File(FILE_NAME);
RandomAccessFile random=new RandomAccessFile(file,"rw");
int lastGeneratedCode=0;
int recordCount=0;
String lastGeneratedCodeString="";
String recordCountString="";
if(random.length()==0)
{
lastGeneratedCodeString="0";
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
recordCountString="0";
while(recordCountString.length()<10) recordCountString+=" ";
random.writeBytes(lastGeneratedCodeString);
random.writeBytes("\n");
random.writeBytes(recordCountString);
random.writeBytes("\n");
}
else
{
lastGeneratedCodeString=random.readLine().trim();
recordCountString=random.readLine().trim();
lastGeneratedCode=Integer.parseInt(lastGeneratedCodeString);
recordCount=Integer.parseInt(recordCountString);
}
int fCode;
String fTitle;
while(random.getFilePointer()<random.length())
{
fCode=Integer.parseInt(random.readLine());
fTitle=random.readLine();
if(fTitle.equalsIgnoreCase(title))
{
random.close();
throw new DAOException("Designation :"+title+" exists");
}
}
int code=lastGeneratedCode+1;
random.writeBytes(String.valueOf(code));
random.writeBytes("\n");
random.writeBytes(title);
random.writeBytes("\n");
designationDTO.setCode(code);
lastGeneratedCode++;
recordCount++;
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
recordCountString=String.valueOf(recordCount);
while(recordCountString.length()<10) recordCountString+=" ";
random.seek(0);
random.writeBytes(lastGeneratedCodeString);
random.writeBytes("\n");
random.writeBytes(recordCountString);
random.writeBytes("\n");
random.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************************

public void update(DesignationDTOInterface designationDAO) throws DAOException
{
if(designationDAO==null) throw new DAOException("Designation is null");
int code=designationDAO.getCode();
if(code<=0) throw new DAOException("Invalid code: "+code);
String title=designationDAO.getTitle();
if(title==null) throw new DAOException("Designation in null");
title=title.trim();
if(title.length()==0) throw new DAOException("Length of title cannot be zero");
try
{
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("Invalid Code: "+code);
RandomAccessFile random=new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
throw new DAOException("Invalid Code: "+code);
}
int fCode;
String fTitle;
random.readLine();
random.readLine();
boolean found=false;
while(random.getFilePointer()<random.length())
{
fCode=Integer.parseInt(random.readLine());
if(fCode==code)
{
found=true;
break;
}
random.readLine();
}
if(found==false)
{
random.close();
throw new DAOException("Invalid Code: "+code);
}
random.seek(0);
random.readLine();
random.readLine();
while(random.getFilePointer()<random.length())
{
fCode=Integer.parseInt(random.readLine());
fTitle=random.readLine();
if(fCode!=code && title.equalsIgnoreCase(fTitle))
{
random.close();
throw new DAOException("Title: "+title+" Exists.");
}
}
File tmpFile=new File("tmp.data");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmprandom=new RandomAccessFile(tmpFile,"rw");
random.seek(0);
tmprandom.writeBytes(random.readLine());
tmprandom.writeBytes("\n");
tmprandom.writeBytes(random.readLine());
tmprandom.writeBytes("\n");
while(random.getFilePointer()<random.length())
{
fCode=Integer.parseInt(random.readLine());
fTitle=random.readLine();
if(code!=fCode)
{
tmprandom.writeBytes(String.valueOf(fCode));
tmprandom.writeBytes("\n");
tmprandom.writeBytes(fTitle);
tmprandom.writeBytes("\n");
}
else
{
tmprandom.writeBytes(String.valueOf(code));
tmprandom.writeBytes("\n");
tmprandom.writeBytes(title);
tmprandom.writeBytes("\n");
}
}
random.seek(0);
tmprandom.seek(0);
while(tmprandom.getFilePointer()<tmprandom.length())
{
random.writeBytes(tmprandom.readLine());
random.writeBytes("\n");
}
random.setLength(tmprandom.length());
tmprandom.setLength(0);
random.close();
tmprandom.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}


}

//******************************************************************************************

public void delete(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
String fTitle="";
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("Invalid Code: "+code);
RandomAccessFile random=new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
throw new DAOException("Invalid Code: "+code);
}
int fCode;
random.readLine();
int recordCount=Integer.parseInt(random.readLine().trim());
boolean found=false;
while(random.getFilePointer()<random.length())
{
fCode=Integer.parseInt(random.readLine());
fTitle=random.readLine();
if(fCode==code)
{
found=true;
break;
}
}
if(found==false)
{
random.close();
throw new DAOException("Invalid Code: "+code);
}
if(new EmployeeDAO().isDesignationAlloted(code))
{
random.close();
throw new DAOException("Employee Exists with Designation: "+fTitle);
}
File tmpFile=new File("tmp.data");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmprandom=new RandomAccessFile(tmpFile,"rw");
random.seek(0);
tmprandom.writeBytes(random.readLine());
tmprandom.writeBytes("\n");
tmprandom.writeBytes(random.readLine());
tmprandom.writeBytes("\n");
while(random.getFilePointer()<random.length())
{
fCode=Integer.parseInt(random.readLine());
fTitle=random.readLine();
if(code!=fCode)
{
tmprandom.writeBytes(String.valueOf(fCode));
tmprandom.writeBytes("\n");
tmprandom.writeBytes(fTitle);
tmprandom.writeBytes("\n");
}
}
random.seek(0);
tmprandom.seek(0);
random.writeBytes(tmprandom.readLine());
random.writeBytes("\n");
tmprandom.readLine();
String recordCountString=String.valueOf(recordCount-1);
while(recordCountString.length()<10) recordCountString+=" ";
random.writeBytes(recordCountString);
random.writeBytes("\n");
while(tmprandom.getFilePointer()<tmprandom.length())
{
random.writeBytes(tmprandom.readLine());
random.writeBytes("\n");
}
random.setLength(tmprandom.length());
tmprandom.setLength(0);
random.close();
tmprandom.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}








}

//******************************************************************************************

public Set<DesignationDTOInterface> getAll() throws DAOException
{
Set<DesignationDTOInterface> designations=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return designations;
RandomAccessFile random=new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return designations;
}
random.readLine();
random.readLine();
DesignationDTOInterface designationDTO;
while(random.getFilePointer()<random.length())
{
designationDTO=new DesignationDTO();
designationDTO.setCode(Integer.parseInt(random.readLine()));
designationDTO.setTitle(random.readLine());
designations.add(designationDTO);
}
random.close();
return designations;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************************

public DesignationDTOInterface getByCode(int code) throws DAOException
{
try
{
if(code<=0) throw new DAOException("Invalid code: "+code);
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid code: "+code);
RandomAccessFile random=new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
throw new DAOException("Invalid code: "+code);
}
random.readLine();
int recordCount=Integer.parseInt(random.readLine().trim());
if(recordCount==0) 
{
random.close();
throw new DAOException("Invalid code: "+code);
}
int fCode;
String fTitle;
while(random.getFilePointer()<random.length())
{
fCode=Integer.parseInt(random.readLine().trim());
fTitle=random.readLine().trim();
if(fCode==code)
{
random.close();
DesignationDTOInterface designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
random.close();
throw new DAOException("Invalid code: "+code);
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************************

public DesignationDTOInterface getByTitle(String title) throws DAOException
{
title=title.trim();
if(title==null || title.length()==0) throw new DAOException("Invalid Title: "+title);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid Title: "+title);
RandomAccessFile random=new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
throw new DAOException("Invalid Title: "+title);
}
random.readLine();
int recordCount=Integer.parseInt(random.readLine().trim());
if(recordCount==0) 
{
random.close();
throw new DAOException("Invalid Title: "+title);
}
int fCode;
String fTitle;
while(random.getFilePointer()<random.length())
{
fCode=Integer.parseInt(random.readLine().trim());
fTitle=random.readLine().trim();
if(title.equalsIgnoreCase(fTitle))
{
random.close();
DesignationDTOInterface designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
random.close();
throw new DAOException("Invalid Title: "+title);
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************************

public boolean codeExists(int code) throws DAOException
{
try
{
if(code<=0) return false;
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile random=new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return false;
}
random.readLine();
int recordCount=Integer.parseInt(random.readLine().trim());
if(recordCount==0) 
{
random.close();
return false;
}
int fCode;
while(random.getFilePointer()<random.length())
{
fCode=Integer.parseInt(random.readLine().trim());
if(fCode==code)
{
random.close();
return true;
}
random.readLine();
}
random.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************************

public boolean titleExists(String title) throws DAOException
{
title=title.trim();
if(title==null || title.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile random=new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return false;
}
random.readLine();
int recordCount=Integer.parseInt(random.readLine().trim());
if(recordCount==0) 
{
random.close();
return false;
}
String fTitle;
while(random.getFilePointer()<random.length())
{
random.readLine();
fTitle=random.readLine();
if(title.equalsIgnoreCase(fTitle))
{
random.close();
return true;
}
}
random.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************************
//getCount() method
public int getCount() throws DAOException
{
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile random = new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return 0;
}
random.readLine();
int recordCount;
recordCount=Integer.parseInt(random.readLine().trim());
random.close();
return recordCount;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}
//******************************************************************************************
}
