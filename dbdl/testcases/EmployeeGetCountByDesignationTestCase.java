import com.tanishq.hr.dl.exceptions.*;
import com.tanishq.hr.dl.interfaces.dto.*;
import com.tanishq.hr.dl.interfaces.dao.*;
import com.tanishq.hr.dl.dto.*;
import com.tanishq.hr.dl.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeGetCountByDesignationTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
System.out.println("Number of records for Designation Code "+designationCode+" is/are: "+employeeDAO.getCountByDesignation(designationCode));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
