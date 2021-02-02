package gameRound;

import java.util.ArrayList;
import java.util.List;

public class OutputCareTaker {
    List<OutputNumberMem> outputNumberMemList = new ArrayList<>();

    public void add(OutputNumberMem state){
        outputNumberMemList.add(state);
    }

    public OutputNumberMem get(int index){
        return outputNumberMemList.get(index);
    }

    public OutputNumberMem getLast(){
        return outputNumberMemList.get(outputNumberMemList.size() - 1);
    }
}
