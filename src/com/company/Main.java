package com.company;

import java.io.*;
import java.util.*;

public class Main {
    public static String[] labels = {"Iris-setosa", "Iris-versicolor", "Iris-verginica"};
    public static ArrayList<String[]> readData(File file){
        ArrayList<String[]> arr = new ArrayList<>();
        String row;
        try {
            BufferedReader fr = new BufferedReader(new FileReader(file));

            while ((row = fr.readLine())!=null){
                arr.add(row.split(","));
            }
            fr.close();
            return arr;
        }catch (FileNotFoundException e){
            System.err.println("Data file not found");
            System.exit(-1);
        }catch (IOException e){
            System.err.println("Data file not found");
            System.exit(-1);
        }
        return  null;
    }
    public static double findDistance(String[] d1, String[] d2){
        double sum = 0;
        for(int i = 0; i<d2.length; i++){
            sum = sum + Math.pow(Double.parseDouble(d1[i]) - Double.parseDouble(d2[i]), 2);
        }
        return Math.sqrt(sum);
    }
    public static String classifyData(ArrayList<String[]> dataSet, String[] dataPoint, int k){
        String[] nearest  = new String[k];
        dataSet.sort(new MyComparator(dataPoint));
        for(int i = 0 ; i< nearest.length; i++){
            nearest[i] = dataSet.get(i)[4];
        }
        List<String> list = Arrays.asList(nearest);
        int max = 0;
        int curr = 0;
        String currKey =  null;
        Set<String> unique = new HashSet<String>(list);

        for (String key : unique) {
            curr = Collections.frequency(list, key);

            if(max < curr){
                max = curr;
                currKey = key;
            }
        }
        return currKey;
    }
    public static double testAcc(ArrayList<String[]> dataSet, ArrayList<String[]> testData, int k){
        double accuracy = 0;
        double sum = 0;
        for(String[] data : testData){
            String[] datapoint = new String[data.length-1];
            String dataVerification = data[data.length -1];
            for(int i = 0; i<datapoint.length ; i++){
                datapoint[i] = data[i];
            }
            String answ = classifyData(dataSet, datapoint, k);
            if(answ.compareTo(dataVerification) == 0) {
                sum = sum + 1;
            }
        }
        accuracy = (sum / testData.size())* 100;
        return  accuracy;
    }
    public static void main(String[] args) {

        int k = Integer.parseInt(args[2]);
        File fileTrain = new File(args[0]);
        File fileTest = new File(args[1]);
        ArrayList<String[]> trainData = readData(fileTrain);
        ArrayList<String[]> testData = readData(fileTest);
        System.out.println("Accuracy is: " + testAcc(trainData, testData, k) + "%");
        while(true) {
            String[] answer = new Scanner(System.in).nextLine().split(",");
            System.out.println(classifyData(trainData, answer, k));
        }
    }
}
class MyComparator implements Comparator<String[]>{
    String[] point;
    public MyComparator(String[] point){
        this.point = point;
    }
    @Override
    public int compare(String[] o1, String[] o2) {
        if(Main.findDistance(o1, point) - Main.findDistance(o2, point)>0){
            return 1;
        }else if(Main.findDistance(o1, point) - Main.findDistance(o2, point) ==0){
            return 0;
        }else{
            return -1;
        }

    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}