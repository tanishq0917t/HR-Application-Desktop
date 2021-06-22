import com.tanishq.hr.dl.exceptions.*;
import com.tanishq.hr.dl.interfaces.dto.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.dto.*;
import com.tanishq.hr.dl.dao.*;
public class DesignationCodeExistsTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO = new DesignationDAO();
System.out.println(code+" Exists: "+designationDAO.codeExists(code));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
