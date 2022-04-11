import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String file_input = "C:\\Users\\DELL\\IdeaProjects\\lexicalScanner\\src\\main\\resources\\input.vc";
        String file_output = "C:\\Users\\DELL\\IdeaProjects\\lexicalScanner\\src\\main\\resources\\output.vctok";
        Automaton automaton = new Automaton("C:\\Users\\DELL\\IdeaProjects\\lexicalScanner\\src\\main\\resources\\automatondata.dat");
        FileManagement.clearFile(file_output);
        LexicalScanner l = new LexicalScanner(file_input, file_output, automaton);
        l.scanMultipleLines();
    }
}
