package ex_17_OOPs;

public class Lab_160_CONSTRUCTOR_1 {
    public static void main(String[] args) {

        Animal1 dog = new Animal1();
        MySQL a = new MySQL();
        ReadExcelFile e = new ReadExcelFile();

    }
}

class MySQL{
    MySQL(){
        System.out.println("MySQL Connected");
    }
}

class ReadExcelFile{
    ReadExcelFile(){
        System.out.println("ExcelFile is Loaded!");
    }
}
