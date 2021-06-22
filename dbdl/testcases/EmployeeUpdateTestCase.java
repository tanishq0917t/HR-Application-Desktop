import com.tanishq.enums.*;
import com.tanishq.hr.dl.exceptions.*;
import com.tanishq.hr.dl.dao.*;
import com.tanishq.hr.dl.dto.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.interfaces.dto.*;
import java.text.*;
import java.util.*;
import java.math.*;
public class EmployeeUpdateTestCase
{
public static void main(String gg[])
{
String employeeId=gg[0];
String name=gg[1];
SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
int designationCode=Integer.parseInt(gg[2]);
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse(gg[3]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return;
}
char gender=gg[4].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[5]);
BigDecimal basicSalary=new BigDecimal(gg[6]);
String panNumber=gg[7];
String aadharCardNumber=gg[8];
try
{
EmployeeDTOInterface employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
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
employeeDAO.update(employeeDTO);
System.out.println("Employee updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
