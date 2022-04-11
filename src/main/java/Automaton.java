import com.google.common.collect.HashBasedTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Automaton {
    HashMap<Integer, String> finalStates;
    HashBasedTable<Integer, String, Integer> statesTable =  HashBasedTable.create();
    List<String> keywords = new ArrayList<>();
    int starting_state;
    String pathname;

    public Automaton(String pathname) {
        this.finalStates = new HashMap<>();
        this.pathname = pathname;
        setAutomaton();
    }


    private void setAutomaton() {
        List<String> lines = FileManagement.readFile(pathname);
        for (int index_line = 0; index_line<lines.size(); index_line++) {
            String [] line = lines.get(index_line).split(",");
            String content = line[0];

            // init states table
            if(content.equals("states_table")) {
                int row = Integer.parseInt(line[1]);
                index_line+=1;
                String [] colum = lines.get(index_line).split(",");
                for(int j = 0;j<row; j++ ) {
                    index_line++;
                    line = lines.get(index_line).split(",");
                    for(int k = 1; k<colum.length; k++) {
                        statesTable.put(Integer.parseInt(line[0]), colum[k], Integer.parseInt(line[k]));
                    }
                }
            }

            //set state starting
            if(content.equals("starting_state")) {
                this.starting_state = Integer.parseInt(line[1]);
            }

            //set keywords
            if(content.equals("key_word")) {
                int count = line.length;
                for(int i = 1; i<count; i++){
                    this.keywords.add(line[i]);
                }
            }

            //set token and token state
            if(content.equals("state_and_token")) {
                int count = Integer.parseInt(line[1]);
                for(int i = 1; i<count; i++){
                    index_line +=1;
                    line = lines.get(index_line).split(",");
                    this.finalStates.put(Integer.parseInt(line[0]), line[1]);
                }
            }
        }
    }
}
