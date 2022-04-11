import java.util.List;
public class LexicalScanner {
    int currentState;
    List<String> lines;
    Automaton automaton;
    String path_file_output;
    String path_file_input;

    public LexicalScanner(String path_file_input, String path_file_output, Automaton automaton) {
        this.automaton = automaton;
        this.path_file_input = path_file_input;
        this.path_file_output = path_file_output;
        currentState = automaton.starting_state;
        setLines();
    }

    public void setLines() {
       this.lines  = FileManagement.readFile(path_file_input);
    }
    public void scanLine(String line) {
        String str = "";
        for(int i=0; i< line.length(); i++) {
            char c = line.charAt(i);
            int nextState = 0;
            if (isCommentinLine(currentState)) {
                str = line.substring(i - 2);
                writeOutput(str, currentState);
                break;
            } else if(isMultComment(currentState)) {
                nextState = getnextState(c);
                if(nextState != 19 && nextState !=20) {
                    nextState = 17;
                }
            } else if(isString(currentState)) {
                nextState = getnextState(c);
                if(nextState !=28 && nextState != 25) {
                    nextState = 15;
                }
            } else if (isSeparator(c)) {
                writeOutput(str, currentState);
                str="" + c;
                nextState = getState(currentState, "[]{}();");
                setCurrentState(nextState);
            } else {
                nextState = getnextState(c);
            }

            if(nextState == -1 || nextState == 14) {
                writeOutput(str, currentState);
                setCurrentState(0);
                str = "";
            } else {
                str += c;
                setCurrentState(nextState);
            }
        }
        if(!isMultComment(currentState) && !isString(currentState)) {
            writeOutput(str, currentState);
        } else {
            FileManagement.writeFile(str+ "\n", path_file_output);
        }
    }

    public boolean isCommentinLine(int currentState) {
        return currentState == 16;
    }

    public boolean isMultComment(int currentState) {
        return currentState == 17 || currentState == 19;
    }

    public boolean isSeparator(char c) {
        return c=='[' || c==']' || c=='(' || c==')' || c=='{' || c=='}' || c==';' || c==',';
    }

    public  boolean isString(int currentState) {
        return currentState == 15;
    }
    public int getnextState(char c) {
        int nextState = 0;
        if(Character.isDigit(c)) {
            nextState = getState(currentState, "number");
        } else if((((int)c >= 65 && (int)c <=90) || ((int)c >= 97 && (int)c <=122) || (int)c == 95) && currentState != 4 && currentState != 1) {
            nextState = getState(currentState, "char");
        } else if(c == '+' || c == '-') {
            nextState = getState(currentState, "+-");
        } else if(c== '*') {
            nextState = getState(currentState, "*");
        } else if(c=='/') {
            nextState = getState(currentState, "/");
        } else if(c=='>') {
            nextState = getState(currentState, ">");
        } else if(c=='=') {
            nextState = getState(currentState, "=");
        } else if(c=='<') {
            nextState = getState(currentState, "<");
        } else if(c=='!') {
            nextState = getState(currentState, "!");
        } else if(c=='\\') {
            nextState = getState(currentState, "\\");
        } else if(c=='.') {
            nextState = getState(currentState, ".");
        }  else if(c=='/') {
            nextState = getState(currentState, "/");
        } else if(c=='E' || c== 'e') {
            nextState = getState(currentState, "E");
        } else if(c=='|') {
            nextState = getState(currentState, "|");
        } else if(c=='b' || c=='f' || c=='n' || c=='n' || c=='r' || c=='t' ) {
            nextState = getState(currentState, "bfnrt'");
        } else if(c=='\"') {
            nextState = getState(currentState, "\"");
        } else if(c=='&') {
            nextState = getState(currentState, "&");
        } else {
            nextState = -1;
        }
        return nextState;
    }
    
    public void scanMultipleLines() {
        lines.forEach((element) ->{
             if(element != null) scanLine(element);
        });
    }
    public int getState (int row, String colum){
        int state = -1;
        if(automaton.statesTable.get(row,colum) != null){
            state = automaton.statesTable.get(row,colum);
        };
        return state;
    }

    private void setCurrentState(int state) {
        this.currentState = state;
    }

    public void writeOutput(String str,int state) {
        if(iskeyword(str)) {
            String tokenType = "key_word";
            FileManagement.writeFile(str+ "\t: " + tokenType+"\n", path_file_output);
        } else {
            String tokenType = automaton.finalStates.get(state);
            if(tokenType != null) FileManagement.writeFile(str+ "\t: " + tokenType+"\n", path_file_output);
        }
        setCurrentState(0);
    }

    public boolean iskeyword(String str) {
        int length = automaton.keywords.size();
        for(int i = 0; i<length; i++) {
            if(str.equals(automaton.keywords.get(i))) return true;
        }
        return false;
    }
}
