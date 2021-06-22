package com.tanishq.hr.dl.dao;
import com.tanishq.hr.dl.dto.*;
import com.tanishq.hr.dl.interfaces.dto.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.exceptions.*;
import java.util.*;
import java.sql.*;
public class DesignationDAO implements DesignationDAOInterface
{

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
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where title=?");
preparedStatement.setString(1,title);
ResultSet rSet=preparedStatement.executeQuery();
if(rSet.next())
{
rSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation: "+title+" exist.");
}
rSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into designation (title) values(?)");
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
rSet=preparedStatement.getGeneratedKeys();
rSet.next();
int code=rSet.getInt(1);
rSet.close();
preparedStatement.close();
connection.close();
designationDTO.setCode(code);
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

//******************************************************************************************

public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Invalid code");
if(designationDTO.getCode()<0) throw new DAOException("Invalid Code.."+designationDTO.getCode());
int code=designationDTO.getCode();
String title=designationDTO.getTitle();
title=title.trim();
if(title.length()==0) throw new DAOException("Length of title can't be zero..");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
connection.close();
resultSet.close();
throw new DAOException("Designation code "+code+"does not exists");
}
preparedStatement.close();
preparedStatement=connection.prepareStatement("select code from designation where title=? and code<>?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("Designation "+title+" exists");
}
preparedStatement.close();
preparedStatement=connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
resultSet.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

//******************************************************************************************

public void delete(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps;
ps=connection.prepareStatement("select * from designation where code=?");
ps.setInt(1,code);
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
ps.close();
rs.close();
connection.close();
throw new DAOException("Code "+code+" does not exists");
}
String designation=rs.getString("title").trim();
rs.close();
ps.close();
ps=connection.prepareStatement("select gender from employee where designation_code=?");
ps.setInt(1,code);
rs=ps.executeQuery();
if(rs.next())
{
ps.close();
rs.close();
connection.close();
throw new DAOException("Cannot delete designation: "+designation+" as it is alloted to employee(s)");
}
rs.close();
ps.close();
ps=connection.prepareStatement("delete from designation where code=?");
ps.setInt(1,code);
ps.executeUpdate();
ps.close();
connection.close();
}catch(SQLException sqle)
{
throw new DAOException(sqle.getMessage());
}
}

//******************************************************************************************

public Set<DesignationDTOInterface> getAll() throws DAOException
{
Set<DesignationDTOInterface> designations=new TreeSet<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet rSet=statement.executeQuery("select * from designation");
DesignationDTOInterface designationDTO;
while(rSet.next())
{
designationDTO=new DesignationDTO();
designationDTO.setCode(rSet.getInt("code"));
designationDTO.setTitle(rSet.getString("title").trim());
designations.add(designationDTO);
}
rSet.close();
statement.close();
connection.close();
return designations;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

//******************************************************************************************

public DesignationDTOInterface getByCode(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
connection.close();
resultSet.close();
throw new DAOException("Designation code "+code+"does not exists");
}
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim());
preparedStatement.close();
resultSet.close();
connection.close();
return designationDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

//******************************************************************************************

public DesignationDTOInterface getByTitle(String title) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
connection.close();
resultSet.close();
throw new DAOException("Designation title "+title+"does not exists");
}
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim());
preparedStatement.close();
resultSet.close();
connection.close();
return designationDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}

//******************************************************************************************

public boolean codeExists(int code) throws DAOException
{
boolean exists;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
preparedStatement.close();
connection.close();
resultSet.close();
return exists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

//******************************************************************************************

public boolean titleExists(String title) throws DAOException
{
boolean exists;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
preparedStatement.close();
connection.close();
resultSet.close();
return exists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

//******************************************************************************************
//getCount() method
public int getCount() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("Select count(*) as count from designation");
resultSet.next();
int count=resultSet.getInt("count");
connection.close();
statement.close();
resultSet.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
//******************************************************************************************
}