import com.tanishq.hr.bl.managers.*;
import com.tanishq.enums.*;
import com.tanishq.hr.bl.interfaces.managers.*;
import com.tanishq.hr.bl.interfaces.pojo.*;
import com.tanishq.hr.bl.pojo.*;
import com.tanishq.hr.bl.exceptions.*;
import java.util.*;
import java.text.*;
import java.math.*;
class EmployeeManagerGetEmployeeByDesignationTestCase
{
public static void main(String gg[])
{
try
{
int designationCode=3;
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
Set<EmployeeInterface>ets;
ets=employeeManager.getEmployeesByDesignationCode(designationCode);
for(EmployeeInterface employee:ets)
{
System.out.println("Name: "+employee.getName());
System.out.println("Gender: "+employee.getGender());
System.out.println("Employee ID: "+employee.getEmployeeId());
System.out.println("Is Indian: "+employee.getIsIndian());
System.out.println("Date of Birth: "+employee.getDateOfBirth());
DesignationInterface dsDesignation=employee.getDesignation();
DesignationInterface designation=new Designation();
System.out.println("Designation Code: "+dsDesignation.getCode());
System.out.println("Basic Salary: "+employee.getBasicSalary());
System.out.println("PAN Number: "+employee.getPANNumber());
System.out.println("Aadhar Card Number: "+employee.getAadharCardNumber());
System.out.println("****************************************************************");
}
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