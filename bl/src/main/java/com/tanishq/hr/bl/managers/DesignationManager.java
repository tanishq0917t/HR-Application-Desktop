package com.tanishq.hr.bl.managers;
import com.tanishq.hr.bl.interfaces.pojo.*;
import com.tanishq.hr.bl.interfaces.managers.*;
import com.tanishq.hr.bl.pojo.*;
import com.tanishq.hr.bl.exceptions.*;
import com.tanishq.hr.dl.exceptions.*;
import com.tanishq.hr.dl.interfaces.dto.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.dto.*;
import com.tanishq.hr.dl.dao.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManager designationManager=null;
private DesignationManager() throws BLException
{
populateDataStructure();
}
//*************************************************************************
private void populateDataStructure() throws BLException
{
this.codeWiseDesignationsMap=new HashMap<>();
this.titleWiseDesignationsMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignations= new DesignationDAO().getAll();
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation:dlDesignations)
{
designation=new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationsMap.put(new Integer(designation.getCode()),designation);
this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOException dao)
{
BLException ble=new BLException();
ble.setGenericException(dao.getMessage());
throw ble;
}
}
//*************************************************************************
public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}
//*************************************************************************
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException ble=new BLException();
if(designation==null)
{
ble.setGenericException("Designation Required");
throw ble;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0) ble.addException("code","Code should be zero");
if(title==null)
{
ble.addException("title","Title Required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
ble.addException("title","Title Required");
}
}
if(title.length()>0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
ble.addException("title","Desingaiton: "+title+" Exists");
}
}
if(ble.hasExceptions()) throw ble;
try
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
code=designationDTO.getCode();
designation.setCode(code);
Designation dsDesignation = new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
codeWiseDesignationsMap.put(new Integer(code),dsDesignation);
titleWiseDesignationsMap.put(title,dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException dao)
{
ble.setGenericException(dao.getMessage());
}
}
//*************************************************************************
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException ble= new BLException();
if(designation==null)
{
ble.setGenericException("Designation Required");
throw ble;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code<=0) ble.addException("code","Invalid Code: "+code);
if(code>0)
{
if(this.codeWiseDesignationsMap.containsKey(code)==false)
{
ble.addException("code","Inavlid Code: "+code);
throw ble;
}
}
if(title==null)
{
ble.addException("title","Title Required");
title="";
}
else
{
title=title.trim();
if(title.length()==0) ble.addException("title","Title Required");
}
if(title.length()>0)
{
DesignationInterface d=titleWiseDesignationsMap.get(title.toUpperCase());
if(d!=null && d.getCode()!=code) ble.addException("title","Designation: "+title+" exists.");
}
if(ble.hasExceptions())
{
throw ble;
}
try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
DesignationDTOInterface dlDesignation=new DesignationDTO();
dlDesignation.setCode(code);
dlDesignation.setTitle(title);
new DesignationDAO().update(dlDesignation);
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);
dsDesignation.setTitle(title);
codeWiseDesignationsMap.put(code,dsDesignation);
titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException dao)
{
ble.setGenericException(dao.getMessage());
}
}
//*************************************************************************
public void removeDesignation(int code) throws BLException
{
BLException ble= new BLException();
if(code<=0)
{
ble.addException("code","Invalid Code: "+code);
throw ble;
}
if(code>0)
{
if(this.codeWiseDesignationsMap.containsKey(code)==false)
{
ble.addException("code","Inavlid Code: "+code);
throw ble;
}
}
try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
new DesignationDAO().delete(code);
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);
}catch(DAOException dao)
{
ble.setGenericException(dao.getMessage());
throw ble;
}
}
//*************************************************************************
public DesignationInterface getDesignationByCode(int code) throws BLException
{
DesignationInterface designation=codeWiseDesignationsMap.get(code);
if(designation==null)
{
BLException ble=new BLException();
ble.addException("code","Invalid Code: "+code);
throw ble;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
//*************************************************************************
public DesignationInterface getDesignationByTitle(String title) throws BLException
{
DesignationInterface designation=titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
BLException ble=new BLException();
ble.addException("code","Invalid Designation: "+title);
throw ble;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
//*************************************************************************
DesignationInterface getDSDesignationByCode(int code)
{
DesignationInterface designation=codeWiseDesignationsMap.get(code);
return designation;
}
//*************************************************************************
public int getDesignationCount() throws BLException
{
return designationsSet.size();
}
//*************************************************************************
public boolean designationCodeExists(int code) throws BLException
{
return codeWiseDesignationsMap.containsKey(code);
}
//*************************************************************************
public boolean designationTitleExists(String title) throws BLException
{
return titleWiseDesignationsMap.containsKey(title.toUpperCase());
}
//*************************************************************************
public Set<DesignationInterface> getDesignations()
{
Set<DesignationInterface>designations=new TreeSet<>();
designationsSet.forEach((designation)->{
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
designations.add(d);
});
return designations;
}
//*************************************************************************
}
