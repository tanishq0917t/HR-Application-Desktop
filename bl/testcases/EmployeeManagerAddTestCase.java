import com.tanishq.hr.bl.managers.*;
import com.tanishq.enums.*;
import com.tanishq.hr.bl.interfaces.managers.*;
import com.tanishq.hr.bl.interfaces.pojo.*;
import com.tanishq.hr.bl.pojo.*;
import com.tanishq.hr.bl.exceptions.*;
import java.util.*;
import java.text.*;
import java.math.*;
class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
try
{
String name="Rahul";
DesignationInterface designation=new Designation();
designation.setCode(4);
Date dateOfBirth=new Date();
boolean isIndian=true;
BigDecimal basicSalary=new BigDecimal("90000");
String panNumber="A82345";
String aadharCardNumber="U12345";
EmployeeInterface employee=new Employee();
employee.setName(name);
employee.setIsIndian(isIndian);
employee.setDesignation(designation);
employee.setGender(GENDER.MALE);
employee.setDateOfBirth(dateOfBirth);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.printf("Employee added with employee Id: %s\n",employee.getEmployeeId());
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