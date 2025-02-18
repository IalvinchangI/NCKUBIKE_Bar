package indi.IalvinchangI.nckubikebar.utils;

import java.util.ArrayList;
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
     * <p>
     * if all the sum are negative, the function will return 0
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

    /**
     * get the name of the series which has the max sum
     * <p>
     * if the sums of any series are equal, the function will list all of them and output the results
     * 
     * @return a list of name
     */
    public ArrayList<String> getArgmax() {
        ArrayList<String> columnNames = new ArrayList<>(this.keySet());
        ArrayList<String> names = new ArrayList<>();
        names.add(columnNames.get(0));
        int max = this.get(names.get(0)).sum();

        for (int i = 1; i < columnNames.size(); i++) {
            int temp = this.get(columnNames.get(i)).sum();
            if (temp > max) {
                names.clear();
                names.add(columnNames.get(i));
                max = temp;
            }
            else if (temp == max) {
                names.add(columnNames.get(i));
            }
        }

        return names;
    }

    /**
     * whether all the elements in the specific row are zero
     * <p>
     * if the row does not exist, the function will return true
     * 
     * @param rowIndex the index of row in DataFrame
     * @return
     */
    public boolean isRowZero(int rowIndex) {
        if (rowIndex >= this.get(this.keySet().toArray()[0]).size()) {  // out of range
            return true;
        }
        for (Series series : this.values()) {
            if (series.get(rowIndex) != 0) {
                return false;
            }
        }
        return true;  // the elements in the same row are 0
    }
}
