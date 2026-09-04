package ex_17_OOPs;

public class Lab159_OOps {
    public static void main(String[] args) {

        // Person p1 - -> Object Reference or Object Reference Variable

        Person p1 = new Person(); // [new Person(); This is called Object]  (Person - Class)   (p1 - reference)  It is Created in Heap or Object Area
        p1.name = "Somya";

        Person p2 = new Person(); // p2 Object are created p1 and p2 are two object even if attribute can be same
        p2.name = "Yogesh";

        Person p3;   // It is Created in Null area

        new Person();         // Object Created in Heap Area, but it does not have reference
    }
}
