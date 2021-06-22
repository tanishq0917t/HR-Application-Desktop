import com.tanishq.hr.pl.model.*;
import java.awt.*;
import javax.swing.*;
import com.tanishq.hr.bl.exceptions.*;
class DesignationModelTestCase extends JFrame
{
private JTable tb;
private DesignationModel designationModel;
private Container container;
private JScrollPane jsp;
DesignationModelTestCase()
{
designationModel=new DesignationModel();
tb=new JTable(designationModel);
container=getContentPane();
container.setLayout(new BorderLayout());
jsp=new JScrollPane(tb,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container.add(jsp);
setLocation(100,200);
setSize(500,400);
setVisible(true);
}
public static void main(String gg[])
{
DesignationModelTestCase dmtc=new DesignationModelTestCase();
}
}