package indi.IalvinchangI.nckubikebar.utils;

import java.util.HashMap;


/**
 * the data type used by the Bar Chart for plotting and storage
 * 
 * @author IalvinchangI
 */
public class DataFrame extends HashMap<String, Series> {
    
    /**
     * create DataFrame
     */
    public DataFrame() {
        super();
    }
    /**
     * create DataFrame and set up its columns
     * 
     * @param columnNames 
     */
    public DataFrame(String[] columnNames) {
        super();
        for (String name : columnNames) {
            this.put(name, new Series());
        }
    }

    /**
     * clear the data in DataFrame
     */
    public void clearData() {
        for (Series series : this.values()) {
            series.clear();
        }
    }

    /**
     * put data into DataFrame at the same row
     * 
     * @param data
     */
    public void putRow(HashMap<String, Integer> data) {
        for (String label : this.keySet()) {
            this.get(label).add(data.get(label));
        }
    }

    /**
     * get data at the specific row
     * 
     * @param data
     */
    public HashMap<String, Integer> getRow(int index) {
        HashMap<String, Integer> out = new HashMap<>();
        for (String label : this.keySet()) {
            try {
                out.put(label, this.get(label).get(index));
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return out;
    }
    
    /**
     * sum the value in each series and return the max one
     * 
     * @return the max sum of the series in data
     */
    public int getMaxSum() {
        int max = 0;
        for (Series series : this.values()) {
            int sum = series.sum();
            max = (max < sum) ? sum : max;
        }
        return max;
    }
}
