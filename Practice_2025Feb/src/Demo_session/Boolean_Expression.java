package Demo_session;

public class Boolean_Expression {

	public static void main(String[] args) 
	{
		int a=50;
		int b=30;
		int c=30;
		
		System.out.println(a > b);
		System.out.println(a >= b);
		System.out.println(a < b);
		System.out.println(a <= b);
		System.out.println(a == b);
		System.out.println(b == c);
		System.out.println(a != b);
		System.out.println(b != c);
		
		System.out.println("And---------------&& , Or----------||------");
		
		
		System.out.println(a > b && b > c);
		System.out.println(a > b && b >= c);
		System.out.println(a > b || b > c);
		System.out.println(a < b || b > c);
		System.out.println(a > b != b > c);
		
	}

}
