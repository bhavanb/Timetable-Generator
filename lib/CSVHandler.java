package lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

public class CSVHandler {
    /**
     * Write the timetable to a .csv file with appropriate formatting
     * 
     * @param grade      The grade for which the time table has been generated
     * @param table      The timetable
     * @param subjectSet The list of subjects for the given grade
     * @throws IOException In case there was an error writing to the file
     */
    public static void writeToFile(String grade, String[][] table, Set<String> subjectSet) throws IOException {
        File outFolder = new File("./out/");
        outFolder.mkdir();
        File outFile = new File(outFolder, "timetable_" + grade + ".csv");
        outFile.createNewFile(); // if file already exists it will do nothing
        FileWriter fileWriter = new FileWriter(outFile);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(
                "DoW,8:15-9:00,9:00-9:45,9:45-9:55,9:55-10:35,10:35-11:15,11:15-11:55,11:55-12:35,12:35-1:10,1:10-1:55,1:55-2:40,2:40-2:50,2:50-3:30");
        for (int i = 0; i < table.length; i++) {
            switch (i) {
                case 0:
                    printWriter.print("Monday,");
                    break;
                case 1:
                    printWriter.print("Tuesday,");
                    break;
                case 2:
                    printWriter.print("Wednesday,");
                    break;
                case 3:
                    printWriter.print("Thursday,");
                    break;
                case 4:
                    printWriter.print("Friday,");
                    break;
            }
            for (int j = 0; j < 2; j++) {
                printWriter.print(table[i][j] + ",");
            }
            printWriter.print("Break,");
            for (int j = 2; j < 5; j++) {
                printWriter.print(table[i][j] + ",");
            }
            printWriter.print("Activity,");
            printWriter.print("Lunch,");
            for (int j = 5; j < 7; j++) {
                printWriter.print(table[i][j] + ",");
            }
            printWriter.print("Break,");
            printWriter.println(table[i][7]);
        }
        printWriter.println();
        for (String sub : subjectSet) {
            printWriter.print(sub + ",");
        }
        printWriter.close();
    }

    /**
     * Reads the inputs to generate the timetables from a specified file into a
     * HashMap2D
     * 
     * @param filePath The path to the input file
     * @return HashMap2D containing the frade, subjects and the number of sessions
     *         per week
     * @throws FileNotFoundException In case there was an error reading from the
     *                               file
     * @see HashMap2D
     */
    public static HashMap2D<String, String, Integer> readFromFile(String filePath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filePath));
        sc.useDelimiter("\n");
        String[] subs = sc.next().split(",");
        subs = Arrays.copyOfRange(subs, 1, subs.length);
        String[] line;
        HashMap2D<String, String, Integer> gradeSubjectMap = new HashMap2D<String, String, Integer>();
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
        return gradeSubjectMap;
    }
}
