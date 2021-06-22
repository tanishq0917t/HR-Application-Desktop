package com.tanishq.hr.bl.pojo;
import com.tanishq.hr.bl.interfaces.pojo.*;
public class Designation implements DesignationInterface
{
private int code;
private String title;
public Designation()
{
this.code=0;
this.title=null;
}
public void setCode(int code)
{
this.code=code;
}
public void setTitle(String title)
{
this.title=title;
}
public int getCode()
{
return this.code;
}
public String getTitle()
{
return this.title;
}
public int hashCode()
{
return code;
}
public boolean equals(Object other)
{
if(!(other instanceof DesignationInterface)) return false;
DesignationInterface designation=(DesignationInterface)other;
return this.code==designation.getCode();
}
public int compareTo(DesignationInterface designation)
{
return this.code-designation.getCode();
}
}
