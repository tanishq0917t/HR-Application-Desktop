import com.tanishq.hr.bl.interfaces.managers.*;
import com.tanishq.hr.bl.interfaces.pojo.*;
import com.tanishq.hr.bl.exceptions.*;
import com.tanishq.hr.bl.pojo.*;
import com.tanishq.hr.bl.managers.*;
import java.util.*;
class DesignationManagerGetDesignationCountTestCase
{
public static void main(String gg[])
{
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
System.out.println("Designation Count: "+designationManager.getDesignationCount());
}catch(BLException ble)
{
List<String>properties=ble.getProperties();
properties.forEach((property)->{
System.out.println(ble.getException(property));
});
}
}
}