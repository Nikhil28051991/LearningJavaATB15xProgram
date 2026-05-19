package Demo_session;

public class Nested_IfElseStatement {

	public static void main(String[] args) 
	{
		int a=10;
		int b=20;
		int c=30;
		
		if (a > b)
		{
			
			System.out.println("Print when only First condition is true");
			
			
			if (c > b)
			{
				System.out.println("Print when both condition is true");
				
			}
			else
			{
				System.out.println("Print when Second condition is false");
				
			}
		
		}
		else
		{
			System.out.println("Print when First condition is false");
			
		}

	}

}
