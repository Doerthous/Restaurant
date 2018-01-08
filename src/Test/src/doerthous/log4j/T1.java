package doerthous.log4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class T1 {
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        URL url =  T1.class.getClassLoader().getResource("doerthous/log4j/T1.class"); // 当前包下，即package指定的路径
        System.out.println(url.toString());
        URI uri = url.toURI();
        System.out.println(uri.toString());
        Scanner scanner = new Scanner(new FileInputStream(new File(uri)));
        System.out.println(scanner.nextLine());
        scanner.close();
    }
}
