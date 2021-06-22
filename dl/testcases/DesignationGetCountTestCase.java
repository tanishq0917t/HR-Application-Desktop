import com.tanishq.hr.dl.exceptions.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.dao.*;
public class DesignationGetCountTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO = new DesignationDAO();
System.out.println("Designation count: "+designationDAO.getCount());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
