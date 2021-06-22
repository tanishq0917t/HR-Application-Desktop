import com.tanishq.enums.*;
import com.tanishq.hr.dl.exceptions.*;
import com.tanishq.hr.dl.dao.*;
import com.tanishq.hr.dl.dto.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.interfaces.dto.*;
import java.text.*;
import java.util.*;
import java.math.*;
public class EmployeeAddTestCase
{
public static void main(String gg[])
{
String name=gg[0];
SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
int designationCode=Integer.parseInt(gg[1]);
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse(gg[2]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return;
}
char gender=gg[3].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[4]);
BigDecimal basicSalary=new BigDecimal(gg[5]);
String panNumber=gg[6];
String aadharCardNumber=gg[7];
try
{
EmployeeDTOInterface employeeDTO = new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M' || gender=='m') employeeDTO.setGender(GENDER.MALE);
if(gender=='F' || gender=='f') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO= new EmployeeDAO();
employeeDAO.add(employeeDTO);
System.out.println("Employee added with Employee Id as: "+employeeDTO.getEmployeeId());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
