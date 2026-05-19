package Demo_session;

public class Multiple_Methods 
{
   public void Method_A()
   {
	  System.out.println("The  Method A is Executed"); 
   }
   public void Method_B()
   {
	   System.out.println("The  Method B is Executed");
   }
   public void Method_C()
   {
	   System.out.println("The  Method C is Executed");
   }
   
   public static void main(String[] args) 
   {
	   Multiple_Methods x=new Multiple_Methods();
	   x.Method_A();
	   x.Method_B();
	   x.Method_C();
   }
}
