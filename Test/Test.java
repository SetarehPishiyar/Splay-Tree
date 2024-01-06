package DSProject;

import java.io.FileWriter;
import java.util.*;

public class Test {

    public static void test_uniform(int count){
        long currentTime = System.currentTimeMillis();
        Random r1 = new Random(currentTime);
        Random r2 = new Random(currentTime+100);

        SplayTree tree = new SplayTree();

        for (int i=0; i<count; i++){
            int num = (Math.abs(r1.nextInt())%100)+1;
            tree.add(num);
            int num2 = (Math.abs(r2.nextInt())%100)+1;
            tree.find(num2);
        }
    }

    public static void test_normal(int count){
        int mean = 50;
        int variance = 25;
        long currentTime = System.currentTimeMillis();
        Random r1 = new Random(currentTime);

        SplayTree tree = new SplayTree();

        for (int i=0; i<count; i++) {
            int num = (int) (r1.nextGaussian(mean,variance));

            if (!tree.find(num))
                tree.add(num);
            else
                tree.find(num);
        }
    }

    public static void main(String[] args) throws Exception {
        long start;
        long end;
        long time;
        FileWriter uwriter = new FileWriter("D:\\uniform.txt");
        FileWriter nwriter = new FileWriter("D:\\normal.txt");
        FileWriter cwriter = new FileWriter("D:\\counts.txt");
        long start_all = System.currentTimeMillis();
        for (int i=10000; i<250000; i+=25000){
            for (int j=0; j<1000; j++) {
                start = System.currentTimeMillis();
                test_uniform(i);
                end = System.currentTimeMillis();
                time = end - start;
                uwriter.write(time + "\n");

                cwriter.write(i + "\n");

                start = System.currentTimeMillis();
                test_normal(i);
                end = System.currentTimeMillis();
                time = end - start;
                nwriter.write(time + "\n");
            }
        }
        for (int i=250000; i<=500000; i+=50000){
            for (int j=0; j<100; j++) {
                start = System.currentTimeMillis();
                test_uniform(i);
                end = System.currentTimeMillis();
                time = end - start;
                uwriter.write(time + "\n");

                cwriter.write(i + "\n");

                start = System.currentTimeMillis();
                test_normal(i);
                end = System.currentTimeMillis();
                time = end - start;
                nwriter.write(time + "\n");
            }
        }
        uwriter.close();
        nwriter.close();
        cwriter.close();
        long end_all = System.currentTimeMillis();
        long total_time = end_all - start_all;
        System.out.println("total time of 21200 tests : " + (total_time/1000 +1) + " seconds.");
    }
}
