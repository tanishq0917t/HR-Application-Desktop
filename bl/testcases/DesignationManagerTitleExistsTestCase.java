import com.tanishq.hr.bl.interfaces.managers.*;
import com.tanishq.hr.bl.interfaces.pojo.*;
import com.tanishq.hr.bl.exceptions.*;
import com.tanishq.hr.bl.pojo.*;
import com.tanishq.hr.bl.managers.*;
import java.util.*;
class DesignationManagerTitleExistsTestCase
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
System.out.println(title+" Exists: "+designationManager.designationTitleExists(title));
}catch(BLException ble)
{
List<String>properties=ble.getProperties();
properties.forEach((property)->{
System.out.println(ble.getException(property));
});
}
}
}