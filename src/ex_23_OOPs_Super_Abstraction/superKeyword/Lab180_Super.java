package ex_23_OOPs_Super_Abstraction.superKeyword;

public class Lab180_Super {

}

class BaseClass{
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    private String browser;

    BaseClass(){
        System.out.println("DC- Parent");
    }

    BaseClass(String browser){
        this.browser = browser;
        System.out.println("Parameterized Constructor");

    }
    void openBrowser(String browserName) {
        System.out.println("Open Browser!! -> " + browserName);
    }

    int aa;

    void closeBrowser() {
        System.out.println("Close Browser!!");
    }
}

class TestCase extends BaseClass {
    void test() {
    }
    String a;


    TestCase() {
             // Parent or Father
        // super(); // or

        // super("Chrome" ); // or

        super.openBrowser("Chrome");

        super.closeBrowser();

        System.out.println(super.getBrowser());

        super.setBrowser("Firefox"); // set

        System.out.println(super.aa);

        // Mine

        this.test();

        System.out.println(this.a);
    }

}