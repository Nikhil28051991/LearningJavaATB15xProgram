package ex_14_Strings;

public class StringDeclaration {

    public static void main(String[] args) {

        // ============================================================
        // 1. Declare first, Initialize later
        // ============================================================

        String s1;                     // s1 → Stack. No String object created yet.

        s1 = "Nikhil";                 // s1 → Stack, "Nikhil" → String Constant Pool (SCP)


        // ============================================================
        // 2. Declare and Initialize at the same time
        // ============================================================

        String s2 = "Nikhil";          // s2 → Stack, "Nikhil" → SCP
        // Existing "Nikhil" from SCP is reused.


        // ============================================================
        // 3. Using new Keyword
        // ============================================================

        String s3 = new String("Nikhil");
        // s3 → Stack
        // New String Object → Heap / Object Area
        // "Nikhil" literal → SCP


        // ============================================================
        // 4. Declare multiple Strings separately
        // ============================================================

        String s4;                     // s4 → Stack
        String s5;                     // s5 → Stack

        s4 = "Java";                   // "Java" → SCP
        s5 = "Selenium";               // "Selenium" → SCP


        // ============================================================
        // 5. Multiple variables with the same String literal
        // ============================================================

        String s6 = "Nikhil";          // s6 → Stack, existing "Nikhil" → SCP
        String s7 = "Nikhil";          // s7 → Stack, same "Nikhil" → SCP
        // No new "Nikhil" String is created.


        // ============================================================
        // 6. String literals are case-sensitive
        // ============================================================

        String s8 = "Hello";            // "Hello" → SCP, String Created
        String s9 = "Hello";            // Same "Hello" → SCP, Reused

        String s10 = "hello";           // "hello" → SCP, DIFFERENT String
        // "Hello" and "hello" are different.

        String s11 = "HELLO";           // "HELLO" → SCP, DIFFERENT String


        // ============================================================
        // 7. Using new String()
        // ============================================================

        String s12 = new String("Hello");
        // s12 → Stack
        // New String Object → Heap
        // "Hello" literal → SCP

        String s13 = new String("Hello");
        // s13 → Stack
        // ANOTHER new String Object → Heap
        // Same "Hello" literal → SCP


        // ============================================================
        // 8. String is Immutable
        // ============================================================

        String s14 = "hello";

        s14 = s14.concat("world");
        // Original "hello" is NOT changed.
        // concat() creates/returns "helloworld".
        // s14 now refers to "helloworld".

        System.out.println(s14);       // Output: helloworld


        // ============================================================
        // 9. String concat()
        // ============================================================

        String s15 = "Java";

        System.out.println(s15.concat("Selenium"));
        // Output: JavaSelenium

        // Original s15 is still "Java".
        System.out.println(s15);
        // Output: Java


        // ============================================================
        // 10. toUpperCase()
        // ============================================================

        String name = "pramod";

        name = name.toUpperCase();
        // "pramod" → "PRAMOD"
        // New String "PRAMOD" is created because the value changed.
        // name now refers to "PRAMOD".

        System.out.println(name);       // Output: PRAMOD


        // ============================================================
        // 11. toUpperCase() when String is already uppercase
        // ============================================================

        String s16 = "ABCD";

        String s17 = s16.toUpperCase();
        // Result is already "ABCD".
        // No content change is required.
        // Java can return the same String object.

        System.out.println(s17);        // Output: ABCD


        // ============================================================
        // 12. String Class Object Printing
        // ============================================================

        String s18 = "Hello";

        System.out.println(s18);
        // Output: Hello
        //
        // String is a Class.
        // String overrides toString().
        // Therefore, printing a String displays its actual value.


        // ============================================================
        // 13. Normal Object Printing
        // ============================================================

        Lab138_Create_ObjectOfClass o =
                new Lab138_Create_ObjectOfClass();

        System.out.println(o);
        // Output will look similar to:
        // ex_14_Strings.Lab138_Create_ObjectOfClass@27716f4
        //
        // Normal Object does not override toString().
        // Object.toString() generally gives:
        // ClassName + @ + hash-code in hexadecimal form.


        // ============================================================
        // 14. char vs String
        // ============================================================

        char c = 'A';                   // char stores ONE character

        System.out.println(c);          // Output: A


        String s19 = "ABCD";            // String stores a sequence of characters

        System.out.println(s19);        // Output: ABCD
        System.out.println(s19.length());// Output: 4


        // ============================================================
        // 15. toLowerCase()
        // ============================================================

        System.out.println(s19.toLowerCase());
        // Output: abcd


        // ============================================================
        // 16. toUpperCase()
        // ============================================================

        System.out.println(s19.toUpperCase());
        // Output: ABCD
        //
        // s19 already contains uppercase characters.
        // No content change is required.


        // ============================================================
        // 17. concat()
        // ============================================================

        System.out.println(s19.concat("E"));
        // Output: ABCDE

        System.out.println(s19.concat("1"));
        // Output: ABCD1


        // ============================================================
        // 18. Add int to String using +
        // ============================================================

        int num = 28;

        System.out.println(s19 + num);
        // Output: ABCD28


        // ============================================================
        // 19. Add int to String using concat()
        // ============================================================

        System.out.println(s19.concat(String.valueOf(num)));
        // Output: ABCD28


        // ============================================================
        // 20. == vs equals()
        // ============================================================

        String a = "Hello";             // "Hello" → SCP
        String b = "Hello";             // Same "Hello" → SCP

        String x = new String("Hello"); // New String Object → Heap
        String y = new String("Hello"); // Another String Object → Heap


        // == checks REFERENCES
        System.out.println(a == b);
        // Output: true
        // Both point to the same "Hello" in SCP.


        System.out.println(a == x);
        // Output: false
        // a → SCP
        // x → Heap


        System.out.println(x == y);
        // Output: false
        // x and y point to different Heap objects.


        // equals() checks CONTENT
        System.out.println(a.equals(x));
        // Output: true
        // Both contain "Hello".


        System.out.println(x.equals(y));
        // Output: true
        // Both contain "Hello".


        // ============================================================
        // 21. equalsIgnoreCase()
        // ============================================================

        String p = "pramod";

        System.out.println(p.equalsIgnoreCase("PRAMOD"));
        // Output: true

        System.out.println(p.equalsIgnoreCase("PramOd"));
        // Output: true


        // ============================================================
        // 22. CharSequence
        // ============================================================

        CharSequence cs = "Pramod";

        System.out.println(cs);
        // Output: Pramod

        System.out.println(cs.subSequence(1, 4));
        // Output: ram
        //
        // Index:
        // P r a m o d
        // 0 1 2 3 4 5
        //
        // 1 to 4 → 1,2,3
        // Result → "ram"


        // ============================================================
        // 23. substring()
        // ============================================================

        String sub = "Java".substring(2);

        System.out.println(sub);
        // Output: va


        // "unhappy".substring(2) → happy
        // "Harbison".substring(3) → bison
        // "emptiness".substring(9) → empty String ""


        // ============================================================
        // 24. toCharArray()
        // ============================================================

        char[] arr = "Java".toCharArray();

        System.out.println(arr);
        // Output: Java
        //
        // Array internally contains:
        // ['J', 'a', 'v', 'a']


        // ============================================================
        // 25. isBlank()
        // ============================================================

        boolean blank = "   ".isBlank();

        System.out.println(blank);
        // Output: true
        //
        // String contains only whitespace.


        // ============================================================
        // 26. isEmpty()
        // ============================================================

        boolean empty = "".isEmpty();

        System.out.println(empty);
        // Output: true
        //
        // String length is 0.


        // ============================================================
        // 27. repeat()
        // ============================================================

        String repeat1 = "ab".repeat(3);

        System.out.println(repeat1);
        // Output: ababab


        String repeat2 = "Pramod".repeat(3);

        System.out.println(repeat2);
        // Output: PramodPramodPramod


        // ============================================================
        // 28. equalsIgnoreCase()
        // ============================================================

        boolean result = "Java".equalsIgnoreCase("java");

        System.out.println(result);
        // Output: true


        // ============================================================
        // 29. lines()
        // ============================================================

        long count = "a\nb\nc".lines().count();

        System.out.println(count);
        // Output: 3


        // ============================================================
        // 30. repeat() with separator
        // ============================================================

        System.out.println("=".repeat(10));
        // Output: ==========

        System.out.println("Here we got!!");
        // Output: Here we got!!

        System.out.println("=".repeat(10));
        // Output: ==========


        // ============================================================
        // 31. charAt()
        // ============================================================

        String s20 = "Java";

        char ch = s20.charAt(2);

        System.out.println(ch);
        // Output: v
        //
        // J a v a
        // 0 1 2 3


        // ============================================================
        // 32. compareTo()
        // ============================================================

        int r1 = "ABC".compareTo("abc");
        int r2 = "abc".compareTo("abc");
        int r3 = "abc".compareTo("ABC");

        System.out.println(r1);
        // Output: -32

        System.out.println(r2);
        // Output: 0

        System.out.println(r3);
        // Output: 32


        // ============================================================
        // 33. indexOf()
        // ============================================================

        int index1 = "Java".indexOf("a");

        System.out.println(index1);
        // Output: 1
        //
        // J a v a
        // 0 1 2 3


        // ============================================================
        // 34. lastIndexOf()
        // ============================================================

        int index3 = "Java".lastIndexOf("a");

        System.out.println(index3);
        // Output: 3


        // ============================================================
        // 35. String.join()
        // ============================================================

        String joined = String.join("*", "Java", "Python");

        System.out.println(joined);
        // Output: Java*Python


        // ============================================================
        // 36. replace()
        // ============================================================

        String replaced = "Java".replace('a', 'o');

        System.out.println(replaced);
        // Output: Jovo
        //
        // replace() replaces ALL matching characters.


        // ============================================================
        // 37. startsWith()
        // ============================================================

        boolean starts = "Java".startsWith("Ja");

        System.out.println(starts);
        // Output: true


        // ============================================================
        // 38. concat()
        // ============================================================

        String combined = "Java".concat("Mava");

        System.out.println(combined);
        // Output: JavaMava


        // ============================================================
        // 39. Size vs Length
        // ============================================================

        // String      → length()
        // Array       → length
        // Collection  → size()


        // IMPORTANT INTERVIEW POINT:
        //
        // String:
        // "Java".length()
        //
        // Array:
        // arr.length
        //
        // Collection:
        // list.size()


        // ============================================================
        // FINAL MEMORY SHORTCUT
        // ============================================================

        // String s = "Hello";
        // s       → Stack
        // "Hello" → String Constant Pool (SCP)
        //
        //
        // String s = new String("Hello");
        // s          → Stack
        // String Obj → Heap
        // "Hello"    → SCP
        //
        //
        // Arrays:
        // int[] arr = {10, 20, 30};
        // arr        → Stack
        // Array Obj  → Heap
    }
}
