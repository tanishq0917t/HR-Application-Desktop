import com.tanishq.hr.bl.managers.*;
import com.tanishq.enums.*;
import com.tanishq.hr.bl.interfaces.managers.*;
import com.tanishq.hr.bl.interfaces.pojo.*;
import com.tanishq.hr.bl.pojo.*;
import com.tanishq.hr.bl.exceptions.*;
import java.util.*;
import java.text.*;
import java.math.*;
class EmployeeManagerRemoveTestCase
{
public static void main(String gg[])
{
try
{
EmployeeInterface employee=new Employee();
String employeeId="A10000003";
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.removeEmployee(employeeId);
System.out.printf("Employee Removed......");
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