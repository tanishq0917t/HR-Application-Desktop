import com.tanishq.hr.bl.managers.*;
import com.tanishq.enums.*;
import com.tanishq.hr.bl.interfaces.managers.*;
import com.tanishq.hr.bl.interfaces.pojo.*;
import com.tanishq.hr.bl.pojo.*;
import com.tanishq.hr.bl.exceptions.*;
import java.util.*;
import java.text.*;
import java.math.*;
class EmployeeManagerUpdateTestCase
{
public static void main(String gg[])
{
try
{
String name="Suresh";
DesignationInterface designation=new Designation();
String employeeId="A10000001  ";
designation.setCode(3);
Date dateOfBirth=new Date();
boolean isIndian=false;
BigDecimal basicSalary=new BigDecimal("120000");
String panNumber="A12345";
String aadharCardNumber="U12345";
EmployeeInterface employee=new Employee();
employee.setEmployeeId(employeeId);
employee.setName(name);
employee.setIsIndian(isIndian);
employee.setDesignation(designation);
employee.setGender(GENDER.MALE);
employee.setDateOfBirth(dateOfBirth);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.updateEmployee(employee);
System.out.printf("Employee Updated......");
}catch(BLException ble)
{
if(ble.hasGenericException())System.out.println(ble.getGenericException());
List<String>properties=ble.getProperties();
for(String property:properties)
{
System.out.println(ble.getException(property));
}
}
}
}