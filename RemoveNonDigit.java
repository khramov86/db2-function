import java.io.*;
public class RemoveNonDigit
{
public static int removeNonDigit(String line)  throws IOException 
{
    String clean = line.replaceAll("\\D+","");
    if(clean == "") {
        return 0;
    }
    return Integer.parseInt(clean);
    
}
}