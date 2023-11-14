package final_algorithms;

import final_algorithms.Methods.BiSearch;

public class App {
    public static void main(String[] args) {
        String path = "graph.txt";
        BiSearch biSearch = new BiSearch();
        biSearch.execute(path, 0, 3);
    }
}
