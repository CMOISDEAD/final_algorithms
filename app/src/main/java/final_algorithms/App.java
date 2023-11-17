package final_algorithms;

import final_algorithms.Methods.BiSearch;

public class App {
    public static void main(String[] args) {
        Utils utils = new Utils();
        String path = "graph.txt";
        BiSearch biSearch = new BiSearch();
        long time = biSearch.execute(path, 0, 3);
        utils.writeTime("BiSearch", time);
    }
}
