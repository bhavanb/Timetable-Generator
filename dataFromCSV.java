import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import lib.HashMap2D;

public class dataFromCSV {
    static HashMap2D<String, String, Integer> gradeSubjectMap = new HashMap2D<String, String, Integer>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("./inputs.csv"));
        sc.useDelimiter("\n");
        String[] subs = sc.next().split(",");
        subs = Arrays.copyOfRange(subs, 1, subs.length);
        String[] line;
        while (sc.hasNext()) {
            line = sc.next().split(",");
            String sub = line[0].trim();
            if (sub.equals(""))
                continue;
            line = Arrays.copyOfRange(line, 1, line.length);
            for (int i = 0; i < line.length; i++) {
                if (line[i].trim().equals(""))
                    continue;
                else if (!line[i].trim().equals("0")) {
                    gradeSubjectMap.put(subs[i].trim(), sub, Integer.parseInt(line[i].trim()));
                }
            }
        }
        System.out.println(gradeSubjectMap.get("ISC 11", "ENGLISH"));
    }
}
