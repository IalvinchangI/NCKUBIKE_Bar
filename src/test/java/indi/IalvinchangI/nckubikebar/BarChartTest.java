package indi.IalvinchangI.nckubikebar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Random;

import org.junit.jupiter.api.Test;

import indi.IalvinchangI.nckubikebar.utils.DataFrame;

public class BarChartTest {

    public HashMap<String, Integer> generateData(String[] labels) {
        HashMap<String, Integer> out = new HashMap<>();
        Random random = new Random();
        for (String label : labels) {
            out.put(label, random.nextInt(-100, 101));
        }
        return out;
    }

    public DataFrame getData(BarChart bar, String fieldName) {
        DataFrame out = null;
        try {
            Field field = BarChart.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            out = (DataFrame) field.get(bar);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return out;
    }

    public int[] getArray(BarChart bar, String fieldName) {
        int[] out = null;
        try {
            Field field = BarChart.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            out = (int[]) field.get(bar);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return out;
    }

    public DataFrame getPlotData(BarChart bar) {
        DataFrame out = null;
        try {
            Method method = BarChart.class.getDeclaredMethod("getPlotData");
            method.setAccessible(true);
            out = (DataFrame) method.invoke(bar);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return out;
    }
    
    // @Test
    public void updateTest() throws InterruptedException {
        BarChart bar = new BarChart(ControlFrame.LABELS);
        DataFrame data = new DataFrame(ControlFrame.LABELS);
        for (int i = 0; i < 500; i++) {
            HashMap<String, Integer> newData = generateData(ControlFrame.LABELS);
            data.putRow(newData);

            bar.update(newData, Integer.toString(i));

            assertEquals(data, getData(bar, "data"));
            Thread.sleep(1500);
            assertEquals(getPlotData(bar), getData(bar, "frameData"));
        }
    }

    @Test
    public void updataClearTest() throws InterruptedException {
        boolean cleanState = false;

        BarChart bar = new BarChart(ControlFrame.LABELS);
        DataFrame data = new DataFrame(ControlFrame.LABELS);
        for (int i = 0; i < 500; i++) {
            if (cleanState == true) {  // clear
                bar.clear();
                data.clearData();
                
                assertEquals(data, getData(bar, "data"));
                cleanState = false;
            }
            else {  // update
                HashMap<String, Integer> newData = generateData(ControlFrame.LABELS);
                data.putRow(newData);

                bar.update(newData, Integer.toString(i));

                assertEquals(data, getData(bar, "data"));
                Thread.sleep(1500);
                cleanState = true;
            }
            assertEquals(getPlotData(bar), getData(bar, "frameData"));

            int[] frameValue = getArray(bar, "frameValue");
            for (int j = 0; j < data.size(); j++) {
                assertEquals(data.get(ControlFrame.LABELS[j]).sum(), frameValue[j]);
            }
        }
    }
}
