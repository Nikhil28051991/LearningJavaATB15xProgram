package ex_20_OOPs_Polymorphism.methodoveridding;

public class Lab190_Methodoverrding_Automation {

    public static void main(String[] args) {
        ChromeTC c1 = new ChromeTC();
        c1.openBrowser();

        FirefoxTc f1 = new FirefoxTc();
        f1.openBrowser();

        CommonToAll commonToAll = new CommonToAll();
        commonToAll.openBrowser();


        // Dynamic Dispatch
        CommonToAll c2 = new ChromeTC();
        c2.openBrowser();

        CommonToAll c3 = new FirefoxTc();
        c3.openBrowser();

//        ChromeDriver driver = new ChromeDriver();
//        driver = new FreifoxDriver();

    }


}


class CommonToAll {
    void openBrowser() {
        System.out.println("Starting the IE browser.");
    }
}

class ChromeTC extends CommonToAll {
    @Override
    void openBrowser() {
        System.out.println("Starting the CHROME browser.");
    }

}

class FirefoxTc extends CommonToAll {
    @Override
    void openBrowser() {
        System.out.println("Starting FirefoxTc, Better Browser!!");
    }
}
