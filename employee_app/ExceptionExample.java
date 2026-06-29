public class ExceptionExample {

    public static void main(String[] args) {

        String name = null;

        try {
            System.out.println(name.length());
        } catch (NullPointerException e) {
            System.out.println("Oops! Name was null.");
        }

        System.out.println("Program continues running.");
    }
}