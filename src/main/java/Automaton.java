import com.google.common.collect.HashBasedTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Automaton {
    HashMap<Integer, Token> finalStates;
    HashBasedTable<Integer, String, Integer> statesTable =  HashBasedTable.create();
    List<String> keywords = new ArrayList<>();
    String pathname;

    public Automaton(String pathname) {
        this.finalStates = new HashMap<>();
        this.pathname = pathname;
        initfinalStates();
        initStateTable();
        initKeywords();
    }

    private void initfinalStates() {
        this.finalStates.put(4, Token.float_literal);
        this.finalStates.put(5, Token.float_literal);
        this.finalStates.put(1, Token.int_literal);
        this.finalStates.put(28, Token.String_literal);
        this.finalStates.put(10, Token.op_less);
        this.finalStates.put(8, Token.op_greater);
        this.finalStates.put(9, Token.op_assign);
        this.finalStates.put(11, Token.op_not);
        this.finalStates.put(14, Token.separator);
        this.finalStates.put(21, Token.op_greater_equal);
        this.finalStates.put(22, Token.op_equal);
        this.finalStates.put(23, Token.op_less_equal);
        this.finalStates.put(24, Token.op_different);
        this.finalStates.put(16, Token.comment_in_line);
        this.finalStates.put(20, Token.mult_comment);
        this.finalStates.put(25, Token.escape);
        this.finalStates.put(26, Token.op_or);
        this.finalStates.put(6, Token.identifier);
        this.finalStates.put(30, Token.op_and);
    }

    private void initKeywords() {
        this.keywords.add("boolean");
        this.keywords.add("break");
        this.keywords.add("continue");
        this.keywords.add("else");
        this.keywords.add("for");
        this.keywords.add("float");
        this.keywords.add("if");
        this.keywords.add("int");
        this.keywords.add("return");
        this.keywords.add("void");
        this.keywords.add("while");
        this.keywords.add("String");
    }
    private void initStateTable() {
        List<String> line = FileManagement.readFile(pathname);
        String [] colum = line.get(0).split(",");
        for(int i = 1; i<line.size(); i++) {
            for(int j = 1; j<18; j++) {
                statesTable.put(Integer.parseInt(line.get(i).split(",")[0]), colum[j], Integer.parseInt(line.get(i).split(",")[j]));
            }
        }
    }
}
