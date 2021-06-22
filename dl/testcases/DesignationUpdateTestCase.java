import com.tanishq.hr.dl.exceptions.*;
import com.tanishq.hr.dl.interfaces.dto.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.dto.*;
import com.tanishq.hr.dl.dao.*;
public class DesignationUpdateTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
String title=gg[1];
try
{
DesignationDTOInterface designationDTO = new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO = new DesignationDAO();
designationDAO.update(designationDTO);
System.out.println("Designation Updated.....");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
