package com.tanishq.hr.dl.dao;
import com.tanishq.enums.*;
import com.tanishq.hr.dl.dto.*;
import com.tanishq.hr.dl.interfaces.dto.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
import java.sql.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
//*****************************************************************************************

public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
String employeeId=employeeDTO.getEmployeeId();
String name=employeeDTO.getName();
int designationCode=employeeDTO.getDesignationCode();
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
char gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
String panNumber=employeeDTO.getPANNumber();
String aadharNumber=employeeDTO.getAadharCardNumber();
DesignationDAOInterface designationDAO=new DesignationDAO();

Connection connection=null;
ResultSet resultSet;
PreparedStatement preparedStatement;
try
{
boolean panNumberExists;
boolean aadharNumberExists;
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharNumber);
resultSet=preparedStatement.executeQuery();
aadharNumberExists=resultSet.next();
if(aadharNumberExists && panNumberExists) 
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("aadhar Number ("+aadharNumber+"( exists and PAN Number ("+panNumber+") exists");
}
else if(aadharNumberExists) 
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("aadhar number exists:"+aadharNumber);
}
else if(panNumberExists) 
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("pan Number exists: "+panNumber);
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException(" Invalid designation code "+designationCode);
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,basic_salary,gender,is_indian,aadhar_card_number,pan_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,aadharNumber);
preparedStatement.setString(8,panNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeId=resultSet.getInt(1);
employeeDTO.setEmployeeId("A"+(1000000+generatedEmployeeId));
connection.close();
preparedStatement.close();
resultSet.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}

}//add ends
//*****************************************************************************************

public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("employee is null");
String employeeId=employeeDTO.getEmployeeId().trim();
String name=employeeDTO.getName();
int designationCode=employeeDTO.getDesignationCode();//
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
char gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
String panNumber=employeeDTO.getPANNumber();//
String aadharNumber=employeeDTO.getAadharCardNumber();//

Connection connection=null;
ResultSet resultSet;
PreparedStatement preparedStatement;
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
try
{
boolean panNumberExists;
boolean aadharNumberExists;
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=? and employee_id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=? and employee_id<>?");
preparedStatement.setString(1,aadharNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
aadharNumberExists=resultSet.next();
if(aadharNumberExists && panNumberExists) 
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("aadhar Number ("+aadharNumber+"( exists and PAN Number ("+panNumber+") exists");
}
else if(aadharNumberExists) 
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("aadhar number exists:"+aadharNumber);
}
else if(panNumberExists) 
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("pan Number exists: "+panNumber);
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException(" Invalid employee id "+employeeId);
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,basic_salary=?,gender=?,is_indian=?,aadhar_card_number=?,pan_number=?  where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,aadharNumber);
preparedStatement.setString(8,panNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
connection.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}
}

//*****************************************************************************************

public void delete(String employeeId) throws DAOException
{

if(employeeId==null) throw new DAOException("employee is null");

Connection connection=null;
ResultSet resultSet;
PreparedStatement preparedStatement;
employeeId=employeeId.trim();
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException(" Invalid employee id "+employeeId);
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
connection.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}

}

//*****************************************************************************************

public Set<EmployeeDTOInterface> getAll() throws DAOException
{

Set<EmployeeDTOInterface> employees=new TreeSet<>();
String employeeId;
String name;
int designationCode;
java.util.Date dateOfBirth;
char gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
Connection connection=null;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
resultSet=statement.executeQuery("select * from employee");
EmployeeDTOInterface employeeDTO;
while(resultSet.next())
{
employeeId="A"+String.valueOf((resultSet.getInt("employee_id")+1000000));
name=resultSet.getString("name");
designationCode=resultSet.getInt("designation_code");
gender=(resultSet.getString("gender")).charAt(0);
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
java.sql.Date sqlDate=resultSet.getDate("date_of_birth");
dateOfBirth=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
aadharCardNumber=resultSet.getString("aadhar_card_number");
panNumber=resultSet.getString("pan_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
if(gender=='M')employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDTO.setPANNumber(panNumber);
employees.add(employeeDTO);
}
resultSet.close();
connection.close();
statement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}

//*****************************************************************************************

public Set<EmployeeDTOInterface> getByDesignationCode(int code) throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
String employeeId;
String name;
int designationCode;
java.util.Date dateOfBirth;
char gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();


preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("Invalid designation code "+code);
}




preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("No Employee exist against Designation Code: "+code);
}
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
EmployeeDTOInterface employeeDTO;
while(resultSet.next())
{
employeeId="A"+String.valueOf((resultSet.getInt("employee_id")+1000000));
name=resultSet.getString("name");
designationCode=resultSet.getInt("designation_code");
gender=(resultSet.getString("gender")).charAt(0);
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
java.sql.Date sqlDate=resultSet.getDate("date_of_birth");
dateOfBirth=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
aadharCardNumber=resultSet.getString("aadhar_card_number");
panNumber=resultSet.getString("pan_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
if(gender=='M')employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDTO.setPANNumber(panNumber);
employees.add(employeeDTO);
}
resultSet.close();
connection.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}

//*****************************************************************************************

public boolean isDesignationAlloted(int designationCode) throws DAOException
{
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean exists=false;
try
{
connection=DAOConnection.getConnection();

preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("Invalid designation code "+designationCode);
}

preparedStatement=connection.prepareStatement("select gender from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()) exists=true;
preparedStatement.close();
resultSet.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return exists;
}
//*****************************************************************************************

public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{

if(employeeId==null) throw new DAOException("employeeId can not be null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("employeeId can not be empty");
String name;
int designationCode;
java.util.Date dateOfBirth;
char gender;
boolean isIndian;
BigDecimal basicSalary;
String aadharCardNumber;
String panNumber;
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("Invalid employeeId "+ employeeId);
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
EmployeeDTOInterface employeeDTO=null;
while(resultSet.next())
{
employeeId="A"+String.valueOf((resultSet.getInt("employee_id")+100000));
name=resultSet.getString("name");
designationCode=resultSet.getInt("designation_code");
gender=(resultSet.getString("gender")).charAt(0);
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
java.sql.Date sqlDate=resultSet.getDate("date_of_birth");
dateOfBirth=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
aadharCardNumber=resultSet.getString("aadhar_card_number");
panNumber=resultSet.getString("pan_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
if(gender=='M')employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDTO.setPANNumber(panNumber);
}
resultSet.close();
connection.close();
preparedStatement.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}


}

//*****************************************************************************************

public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{

if(panNumber==null) throw new DAOException("panNumber can not be null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("pan number can not be empty");
String employeeId;
String name;
int designationCode;
java.util.Date dateOfBirth;
char gender;
boolean isIndian;
BigDecimal basicSalary;
String aadharCardNumber;

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("Invalid PAN number "+ panNumber);
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
EmployeeDTOInterface employeeDTO=null;
while(resultSet.next())
{
employeeId="A"+String.valueOf((resultSet.getInt("employee_id")+100000));
name=resultSet.getString("name");
designationCode=resultSet.getInt("designation_code");
gender=(resultSet.getString("gender")).charAt(0);
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
java.sql.Date sqlDate=resultSet.getDate("date_of_birth");
dateOfBirth=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
aadharCardNumber=resultSet.getString("aadhar_card_number");
panNumber=resultSet.getString("pan_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
if(gender=='M')employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDTO.setPANNumber(panNumber);
}
resultSet.close();
connection.close();
preparedStatement.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}

//*****************************************************************************************

public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{

if(aadharCardNumber==null) throw new DAOException("aadharNumber can not be null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("aadhar number can not be empty");
String employeeId;
String name;
int designationCode;
java.util.Date dateOfBirth;
char gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("Invalid aadhar number "+ aadharCardNumber);
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
EmployeeDTOInterface employeeDTO=null;
while(resultSet.next())
{
employeeId="A"+String.valueOf((resultSet.getInt("employee_id")+1000000));
name=resultSet.getString("name");
designationCode=resultSet.getInt("designation_code");
gender=(resultSet.getString("gender")).charAt(0);
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
java.sql.Date sqlDate=resultSet.getDate("date_of_birth");
dateOfBirth=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
aadharCardNumber=resultSet.getString("aadhar_card_number");
panNumber=resultSet.getString("pan_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
if(gender=='M')employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDTO.setPANNumber(panNumber);
}
resultSet.close();
connection.close();
preparedStatement.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}


}

//*****************************************************************************************

public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("employeeId can not be null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("employeeId can not be empty");

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean exists=false;
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
preparedStatement.close();
resultSet.close();
connection.close();
return exists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

//*****************************************************************************************

public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("aadharNumber can not be null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("aadhar number can not be empty");

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean exists=false;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
preparedStatement.close();
resultSet.close();
connection.close();
return exists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}


}

//*****************************************************************************************

public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("panNumber can not be null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("pan number can not be empty");

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean exists=false;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
preparedStatement.close();
resultSet.close();
connection.close();
return exists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}

//*****************************************************************************************

public int getCount() throws DAOException
{
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
int count=0;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select count(*) as cnt from employee");
resultSet=preparedStatement.executeQuery();
resultSet.next();
count=resultSet.getInt("cnt");
preparedStatement.close();
resultSet.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return count;
}

//*****************************************************************************************

public int getCountByDesignation(int designationCode) throws DAOException
{
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
int count=0;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select count(*) as cnt from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
resultSet.next();
count=resultSet.getInt("cnt");
preparedStatement.close();
resultSet.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return count;

}

//*****************************************************************************************
}