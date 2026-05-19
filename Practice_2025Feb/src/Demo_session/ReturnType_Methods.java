package Demo_session;

public class ReturnType_Methods 
{
    public int age(int a)
    {
    	System.out.println("The age is "+a);
    	return a;
    }
    
    public boolean Condition(boolean b)
    {
    	System.out.println("The system is "+b);
    	return b;
    }
    
    public String Colour(String s)
    {
    	System.out.println("The painting has lots of "+s+" colour");
    	return s;
    }
    
	public static void main(String[] args) 
	{
	   ReturnType_Methods m=new ReturnType_Methods();
	   m.age(15);
	   m.Condition(true);
	   m.Colour("Pink");
	}

}
