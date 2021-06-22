package com.tanishq.hr.dl.interfaces.dao;
import com.tanishq.hr.dl.interfaces.dto.*;
import com.tanishq.hr.dl.exceptions.*;
import java.util.*;
public interface DesignationDAOInterface
{
public void add(DesignationDTOInterface designationDAO) throws DAOException;
public void update(DesignationDTOInterface designationDAO) throws DAOException;
public void delete(int code) throws DAOException;
public Set<DesignationDTOInterface> getAll() throws DAOException;
public DesignationDTOInterface getByCode(int code) throws DAOException;
public DesignationDTOInterface getByTitle(String title) throws DAOException;
public boolean codeExists(int code) throws DAOException;
public boolean titleExists(String title) throws DAOException;
public int getCount() throws DAOException;
}
