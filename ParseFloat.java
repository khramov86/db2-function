public class ParseFloat {
    public static float parseFloat(String line) {
        float result = 0;
        try {
            result = Float.parseFloat(line);
        }
        catch (Exception e) {
 
        }
        return result;
}
    public static void main(String[] args) {
        System.out.println(parseFloat(args[0]));
    }
}
