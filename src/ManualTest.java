public class ManualTest {
    public static void main(String[] args) {
        HashListAutocomplete hla = new HashListAutocomplete(new String[]{
            "apple","apricot","avocado","almond","alupug"},new double[]{1.0, 3.0, 4.0, 2.0, 3.0});

        System.out.println(hla.sizeInBytes());

    }
}
