
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManagement {

    public static List<String> readFile(String pathname) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File(pathname);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data) ;
            }
            myReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeFile(String content, String pathname) {
        try{
            //Here true is to append the content to file
            FileWriter fw = new FileWriter(pathname,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            //Closing BufferedWriter Stream
            bw.close();
        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
    }

    public static void clearFile(String namepath) throws FileNotFoundException {
        File file = new File(namepath);
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
    }

}
