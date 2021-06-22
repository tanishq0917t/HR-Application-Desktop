package com.tanishq.hr.dl.dao;
import com.tanishq.enums.*;
import com.tanishq.hr.dl.dto.*;
import com.tanishq.hr.dl.interfaces.dto.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
import java.io.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private final static String FILE_NAME="employee.data";
//*****************************************************************************************

public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
//Validations Starts here........

if(employeeDTO==null) throw new DAOException("Employee in null");
String employeeId;
String name=employeeDTO.getName();
if(name==null) throw new DAOException("Employee in null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name cannot be zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid Designation Code: "+designationCode);
DesignationDAOInterface designationDAO= new DesignationDAO();
boolean isDesignationValid=designationDAO.codeExists(designationCode);
if(!isDesignationValid) throw new DAOException("Invalid Designation Code: "+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender is not set to MALE/FEMALE");
boolean isIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic Salary is null");
if(basicSalary.signum()==-1) throw new DAOException("Basis Salary is Negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("Pan Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length Pan Number is Zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar Card Number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length Aadhar Card Number is Zero");

//Validations Ends here........
try
{
int lastGeneratedEmployeeId=10000000;
String lastGeneratedEmployeeIdString="";
int recordCount=0;
String recordCountString="";
File file=new File(FILE_NAME);
RandomAccessFile random=new RandomAccessFile(file,"rw");
if(random.length()==0)
{
lastGeneratedEmployeeIdString=String.format("%-10s","10000000");
random.writeBytes(lastGeneratedEmployeeIdString);
random.writeBytes("\n");
recordCountString=String.format("%-10s","0");
random.writeBytes(recordCountString);
random.writeBytes("\n");
}
else
{
lastGeneratedEmployeeIdString=random.readLine().trim();
recordCountString=random.readLine().trim();
lastGeneratedEmployeeId=Integer.parseInt(lastGeneratedEmployeeIdString);
recordCount=Integer.parseInt(recordCountString);
}
boolean panNumberExists=false,aadharCardNumberExists=false;
String fPanNumber,fAadharCardNumber;
int x;
while(random.getFilePointer()<random.length())
{
for(x=1;x<=7;x++)random.readLine();
fPanNumber=random.readLine();
fAadharCardNumber=random.readLine();
if(panNumberExists==false && fPanNumber.equalsIgnoreCase(panNumber)) panNumberExists=true;
if(aadharCardNumberExists==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)) aadharCardNumberExists=true;
if(aadharCardNumberExists && panNumberExists) break;
}
if(panNumberExists && aadharCardNumberExists)
{
random.close();
throw new DAOException("PAN Number ("+panNumber+") and Aadhar Card Number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
random.close();
throw new DAOException("PAN Number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
random.close();
throw new DAOException("Aadhar Card Number ("+aadharCardNumber+") exists");
}
lastGeneratedEmployeeId++;
employeeId="A"+lastGeneratedEmployeeId;
recordCount++;
random.writeBytes(employeeId+"\n");
random.writeBytes(name+"\n");
random.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
random.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
random.writeBytes(gender+"\n");
random.writeBytes(isIndian+"\n");
random.writeBytes(basicSalary.toPlainString()+"\n");
random.writeBytes(panNumber+"\n");
random.writeBytes(aadharCardNumber+"\n");
random.seek(0);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
random.writeBytes(lastGeneratedEmployeeIdString+"\n");
random.writeBytes(recordCountString+"\n");
random.close();
employeeDTO.setEmployeeId(employeeId);
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//*****************************************************************************************

public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee in null");
String employeeId=employeeDTO.getEmployeeId().trim();
if(employeeId==null) throw new DAOException("EmployeeId is null");
if(employeeId.length()==0) throw new DAOException("Lenght of EmployeeId cannot be zero");
String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name in null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name cannot be zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid Designation Code: "+designationCode);
DesignationDAOInterface designationDAO= new DesignationDAO();
boolean isDesignationValid=designationDAO.codeExists(designationCode);
if(!isDesignationValid) throw new DAOException("Invalid Designation Code: "+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender is not set to MALE/FEMALE");
boolean isIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic Salary is null");
if(basicSalary.signum()==-1) throw new DAOException("Basis Salary is Negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("Pan Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length Pan Number is Zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar Card Number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length Aadhar Card Number is Zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid Employee Id: "+employeeId);
RandomAccessFile random=new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
throw new DAOException("Invalid Employee Id: "+employeeId);
}
random.readLine();
random.readLine();
SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
String fEmployeeId;
String fName;
int fDesignationCode;
char fGender;
Date fDateOfBirth;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber;
int x;
boolean employeeIdFound=false;
boolean panNumberFound=false;
boolean aadharCardNumberFound=false;
String panNumberFoundAgainstEmployeeId="";
String aadharCardNumberFoundAgainstEmployeeId="";
long foundAt=0;
while(random.getFilePointer()<random.length())
{
if(employeeIdFound==false) foundAt=random.getFilePointer();
fEmployeeId=random.readLine();
for(x=1;x<=6;x++)random.readLine();
fPANNumber=random.readLine();
fAadharCardNumber=random.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
}
if(panNumberFound==false && fPANNumber.equalsIgnoreCase(panNumber))
{
panNumberFound=true;
panNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(aadharCardNumberFound==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberFound=true;
aadharCardNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(employeeIdFound && aadharCardNumberFound && panNumberFound)break;
}
if(employeeIdFound==false)
{
random.close();
throw new DAOException("Invalid EmployeeId: "+employeeId);
}
boolean panNumberExists=false;
if(panNumberFound && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
panNumberExists=true;
}
boolean aadharCardNumberExists=false;
if(aadharCardNumberFound && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
aadharCardNumberExists=true;
}
if(panNumberExists && aadharCardNumberExists)
{
random.close();
throw new DAOException("PAN Number ("+panNumber+") and Aadhar Card Number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
random.close();
throw new DAOException("PAN Number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
random.close();
throw new DAOException("Aadhar Card Number ("+aadharCardNumber+") exists");
}
random.seek(foundAt);
for(x=1;x<=9;x++) random.readLine();
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmpRandom=new RandomAccessFile(tmpFile,"rw");
while(random.getFilePointer()<random.length())
{
tmpRandom.writeBytes(random.readLine()+"\n");
}
random.seek(foundAt);
random.writeBytes(employeeId+"\n");
random.writeBytes(name+"\n");
random.writeBytes(designationCode+"\n");
random.writeBytes(sdf.format(dateOfBirth)+"\n");
random.writeBytes(gender+"\n");
random.writeBytes(isIndian+"\n");
random.writeBytes(basicSalary.toPlainString()+"\n");
random.writeBytes(panNumber+"\n");
random.writeBytes(aadharCardNumber+"\n");
tmpRandom.seek(0);
while(tmpRandom.getFilePointer()<tmpRandom.length())
{
random.writeBytes(tmpRandom.readLine()+"\n");
}
random.setLength(random.getFilePointer());
tmpRandom.setLength(0);
random.close();
tmpRandom.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}

}

//*****************************************************************************************

public void delete(String employeeId) throws DAOException
{
//employeeId=employeeId.trim();
if(employeeId==null) throw new DAOException("EmployeeId is null");
if(employeeId.length()==0) throw new DAOException("Lenght of EmployeeId cannot be zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid Employee Id: "+employeeId);
RandomAccessFile random=new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
throw new DAOException("Invalid Employee Id: "+employeeId);
}
random.readLine();
int recordCount=Integer.parseInt(random.readLine().trim());
String fEmployeeId;
int x;
boolean employeeIdFound=false;
long foundAt=0;
while(random.getFilePointer()<random.length())
{
foundAt=random.getFilePointer();
fEmployeeId=random.readLine();
for(x=1;x<=8;x++)random.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
break;
}
}
if(employeeIdFound==false)
{
random.close();
throw new DAOException("Invalid EmployeeId: "+employeeId);
}
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmpRandom=new RandomAccessFile(tmpFile,"rw");
while(random.getFilePointer()<random.length())
{
tmpRandom.writeBytes(random.readLine()+"\n");
}
random.seek(foundAt);
tmpRandom.seek(0);
while(tmpRandom.getFilePointer()<tmpRandom.length())
{
random.writeBytes(tmpRandom.readLine()+"\n");
}
random.setLength(random.getFilePointer());
recordCount--;
String recordCountString=String.format("%-10d",recordCount);
random.seek(0);
random.readLine();
random.writeBytes(recordCountString+"\n");
tmpRandom.setLength(0);
random.close();
tmpRandom.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}

}

//*****************************************************************************************

public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees = new TreeSet<>();
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile random = new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return employees;
}
random.readLine();
random.readLine();
EmployeeDTOInterface employeeDTO;
SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
char fGender;
while(random.getFilePointer()<random.length())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(random.readLine());
employeeDTO.setName(random.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(random.readLine()));
try
{
employeeDTO.setDateOfBirth(sdf.parse(random.readLine()));
}catch(ParseException pe)
{
//nothing to write here
}
fGender=random.readLine().charAt(0);
if(fGender=='M' || fGender=='m') employeeDTO.setGender(GENDER.MALE);
if(fGender=='F' || fGender=='f') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(random.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(random.readLine()));
employeeDTO.setPANNumber(random.readLine());
employeeDTO.setAadharCardNumber(random.readLine());
employees.add(employeeDTO);
}
random.close();
return employees;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//*****************************************************************************************

public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
if(new DesignationDAO().codeExists(designationCode)==false)
{
throw new DAOException("Invalid Designation Code: "+designationCode);
}
Set<EmployeeDTOInterface> employees = new TreeSet<>();
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile random = new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return employees;
}
random.readLine();
random.readLine();
EmployeeDTOInterface employeeDTO;
SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
String fEmployeeId;
String fName;
int fDesignationCode;
int x;
char fGender;
while(random.getFilePointer()<random.length())
{
fEmployeeId=random.readLine();
fName=random.readLine();
fDesignationCode=Integer.parseInt(random.readLine());
if(fDesignationCode!=designationCode)
{
for(x=1;x<=6;x++) random.readLine();
continue;
}
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
try
{
employeeDTO.setDateOfBirth(sdf.parse(random.readLine()));
}catch(ParseException pe)
{
//nothing to write here
}
fGender=random.readLine().charAt(0);
if(fGender=='M' || fGender=='m') employeeDTO.setGender(GENDER.MALE);
if(fGender=='F' || fGender=='f') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(random.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(random.readLine()));
employeeDTO.setPANNumber(random.readLine());
employeeDTO.setAadharCardNumber(random.readLine());
employees.add(employeeDTO);
}
random.close();
return employees;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//*****************************************************************************************

public boolean isDesignationAlloted(int designationCode) throws DAOException
{
if(new DesignationDAO().codeExists(designationCode)==false)
{
throw new DAOException("Invalid Designation Code: "+designationCode);
}
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile random = new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return false;
}
random.readLine();
random.readLine();
int fDesignationCode;
int x;
while(random.getFilePointer()<random.length())
{
random.readLine();
random.readLine();
fDesignationCode=Integer.parseInt(random.readLine());
if(fDesignationCode==designationCode)
{
random.close();
return true;
}
for(x=1;x<=6;x++) random.readLine();
}
random.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}
//*****************************************************************************************

public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Invalid EmployeeId: "+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of EmployeeId can not be Zero");
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid EmployeeId: "+employeeId);
RandomAccessFile random = new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
throw new DAOException("Invalid EmployeeId: "+employeeId);
}
random.readLine();
random.readLine();
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
char fGender;
int x;
while(random.getFilePointer()<random.length())
{
fEmployeeId=random.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(random.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(random.readLine()));
try
{
employeeDTO.setDateOfBirth(sdf.parse(random.readLine()));
}catch(ParseException pe)
{
//nothing to write here
}
fGender=random.readLine().charAt(0);
if(fGender=='M' || fGender=='m') employeeDTO.setGender(GENDER.MALE);
if(fGender=='F' || fGender=='f') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(random.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(random.readLine()));
employeeDTO.setPANNumber(random.readLine());
employeeDTO.setAadharCardNumber(random.readLine());
random.close();
return employeeDTO;
}
for(x=1;x<=8;x++) random.readLine();
}
random.close();
throw new DAOException("Invalid EmployeeId: "+employeeId);
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}

}

//*****************************************************************************************

public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("Invalid PANNumber: "+panNumber);
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of PANNumber can not be Zero");
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid PANNumber: "+panNumber);
RandomAccessFile random = new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
throw new DAOException("Invalid PANNumber: "+panNumber);
}
random.readLine();
random.readLine();
EmployeeDTOInterface employeeDTO;
String fPANNumber;
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fAadharCardNumber;
SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
int x;
while(random.getFilePointer()<random.length())
{
fEmployeeId=random.readLine();
fName=random.readLine();
fDesignationCode=Integer.parseInt(random.readLine());
try
{
fDateOfBirth=sdf.parse(random.readLine());
}catch(ParseException pe)
{
//nothing to write here
}
fGender=random.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(random.readLine());
fBasicSalary=new BigDecimal(random.readLine());
fPANNumber=random.readLine();
fAadharCardNumber=random.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setGender((fGender=='M' || fGender=='m')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
random.close();
return employeeDTO;
}
}
random.close();
throw new DAOException("Invalid PANNumber: "+panNumber);
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}

}

//*****************************************************************************************

public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Invalid Aadhar Card Number: "+aadharCardNumber);
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of Aadhar Card Number can not be Zero");
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid Aadhar Card Number: "+aadharCardNumber);
RandomAccessFile random = new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
throw new DAOException("Invalid Aadhar Card Number: "+aadharCardNumber);
}
random.readLine();
random.readLine();
EmployeeDTOInterface employeeDTO;
String fPANNumber;
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fAadharCardNumber;
SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
int x;
while(random.getFilePointer()<random.length())
{
fEmployeeId=random.readLine();
fName=random.readLine();
fDesignationCode=Integer.parseInt(random.readLine());
try
{
fDateOfBirth=sdf.parse(random.readLine());
}catch(ParseException pe)
{
//nothing to write here
}
fGender=random.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(random.readLine());
fBasicSalary=new BigDecimal(random.readLine());
fPANNumber=random.readLine();
fAadharCardNumber=random.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setGender((fGender=='M' || fGender=='m')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
random.close();
return employeeDTO;
}
}
random.close();
throw new DAOException("Invalid Aadhar Card Number: "+aadharCardNumber);
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}

}

//*****************************************************************************************

public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) return false;
employeeId=employeeId.trim();
if(employeeId.length()==0) return false;
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile random = new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return false;
}
random.readLine();
random.readLine();
String fEmployeeId;
int x;
while(random.getFilePointer()<random.length())
{
fEmployeeId=random.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
random.close();
return true;
}
for(x=1;x<=8;x++) random.readLine();
}
random.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}

}

//*****************************************************************************************

public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile random = new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return false;
}
random.readLine();
random.readLine();
String fAadharCardNumber;
int x;
while(random.getFilePointer()<random.length())
{
for(x=1;x<=8;x++)random.readLine();
fAadharCardNumber=random.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
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

//*****************************************************************************************

public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile random = new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return false;
}
random.readLine();
random.readLine();
String fPANNumber;
int x;
while(random.getFilePointer()<random.length())
{
for(x=1;x<=7;x++)random.readLine();
fPANNumber=random.readLine();
random.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber))
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

//*****************************************************************************************

public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)return 0;
RandomAccessFile random= new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return 0;
}
random.readLine();
int recordCount=Integer.parseInt(random.readLine().trim());
random.close();
return recordCount;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//*****************************************************************************************

public int getCountByDesignation(int designationCode) throws DAOException
{
try
{
if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("Inavlid Designation Code: "+designationCode);
File file=new File(FILE_NAME);
if(file.exists()==false)return 0;
RandomAccessFile random= new RandomAccessFile(file,"rw");
if(random.length()==0)
{
random.close();
return 0;
}
random.readLine();
random.readLine();
int fDesignationCode;
int x;
int recordCount=0;
while(random.getFilePointer()<random.length())
{
random.readLine();
random.readLine();
fDesignationCode=Integer.parseInt(random.readLine());
if(fDesignationCode==designationCode) recordCount++;
for(x=1;x<=6;x++)random.readLine();
}
random.close();
return recordCount;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}

}

//*****************************************************************************************
}
