package InvertedIndex;

import java.util.HashMap;
import java.util.List;

public class IndexModel {
    private HashMap<String, List<Integer>> map;

    public HashMap<String, List<Integer>> getMap() {
        return map;
    }

    public void setMap(HashMap<String, List<Integer>> map) {
        this.map = map;
    }
}