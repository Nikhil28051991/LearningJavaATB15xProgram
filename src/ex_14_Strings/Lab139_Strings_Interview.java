package ex_14_Strings;

public class Lab139_Strings_Interview {
    public static void main(String[] args) {

        String name = "pramod";
        name = name.toUpperCase(); // It will be Created in String Constant Pool (SCP)
        System.out.println(name);

// String is a class but why not shows location when user Print it because it contains by default function .toString(); which cause the value of itself

    }
}
