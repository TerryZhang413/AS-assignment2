package Ozlympic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {
    public ArrayList<String> readText(String address) throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            BufferedReader buffReader = new BufferedReader(
                    new FileReader(address));
            String data = buffReader.readLine();
            while (data != null) {
                String[] dataArray = data.split(" ");
                for (String element : dataArray) {
                    arrayList.add(element);
                }
                data = buffReader.readLine();
            }
            buffReader.close();
            return arrayList;
        } catch (Exception e) {
            return null;
        }
    }

}
