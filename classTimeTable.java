
/**
 * An explanation of the algorithm:
 * - table is a 2D array that stores the subjects in the time table, the rows are the days of the week and the columns are the periods in a day
 * - subjects are selected from the inputs for the given grade and randomized and stored in table
 * - iteratively, the number of actual occurrences of each subject are checked against the required number of sessions(from the input file)
 * - more sessions are allocated and extra sessions are removed as required
 * This is repeated for each grade in inputs.csv
 */
import java.io.*;
import java.util.*;

import lib.*;

public class classTimeTable {
    static HashMap2D<String, String, Integer> gradeSubjectMap = new HashMap2D<String, String, Integer>();
    static int sPeriods = 8;

    static String[][] table;

    public static void main(String[] args) {
        try {
            gradeSubjectMap = CSVHandler.readFromFile("./inputs.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String s : gradeSubjectMap.primaryKeySet()) {
            initTable(s);
            validateTable(s);
            try {
                CSVHandler.writeToFile(s, table, gradeSubjectMap.secondaryKeySet(s));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Select a random element from an array
     * 
     * @param <T>   The type of the array
     * @param array The array
     * @return A random element from the array
     */
    static <T> T getRandomElement(T[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    /**
     * Select a random element from a list
     * 
     * @param <T>   The type of the list
     * @param array The list
     * @return A random element from the list
     */
    static <T> T getRandomElement(List<T> list) {
        int rnd = new Random().nextInt(list.size());
        return list.get(rnd);
    }

    /**
     * Counts and returns the number of occurences of a subject in the timetable
     * 
     * @param sub The subject to search for
     * @return The number of occurences
     */
    static int count(String sub) {
        int c = 0;
        for (int i = 0; i < table.length; i++)
            for (int j = 0; j < table[0].length; j++)
                if (table[i][j].equals(sub))
                    c++;
        return c;
    }

    /**
     * Counts the number of occurences of each subject in the timetable for a given
     * grade and subtracts the required number of sessions to give the number of
     * required sessions(is -ve) or extra sessions(is +ve) each subject has
     * 
     * @param grade The grade to look through
     * @return A HashMap containing the subject and the number of occurrences as a
     *         key, value pair
     * @see HashMap
     */
    static HashMap<String, Integer> countSubs(String grade) {
        HashMap<String, Integer> subjectOccurrences = new HashMap<String, Integer>();
        for (String string : gradeSubjectMap.secondaryKeySet(grade)) {
            subjectOccurrences.put(string, count(string) - gradeSubjectMap.get(grade, string)); // subtract the required
                                                                                                // number of sessions
                                                                                                // from the existing
                                                                                                // sessions
        } // to get only the amount of extra or missing sessions, 0 if the exact number of
          // sessions is present
        return subjectOccurrences;
    }

    /**
     * Finds the occurences of a given subject in the timetable
     * 
     * @param sub The subject to search for
     * @return A list containing Pairs of the indexes of the occurences of the
     *         subject
     * 
     * @see Pair
     */
    static List<Pair<Integer, Integer>> getOccurences(String sub) {
        List<Pair<Integer, Integer>> list = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (table[i][j].equals(sub))
                    list.add(new Pair<Integer, Integer>(i, j)); // store the indexes of the occurences(i and j) in a
                                                                // Pair and push them to the list
            }
        }
        return list;
    }

    /**
     * Initializes the time table with random subjects chosen from the input
     * 
     * @param grade The grade to generate the timetable for
     */
    static void initTable(String grade) {
        table = new String[5][sPeriods]; // 5 days a week, 8 periods per day
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = getRandomElement(gradeSubjectMap.secondaryKeySet(grade) // select a random subject from
                                                                                      // the list of subjects for the
                                                                                      // given grade
                        .toArray(new String[gradeSubjectMap.secondaryKeySet(grade).size()])); // and insert it into the
                                                                                              // time table
            }
        }
    };

    /**
     * Validate the time table and make the necessary changes to ensure all the
     * subjects have the required number of sessions per week
     * 
     * @param grade The grade to generate the timetable for
     */
    static void validateTable(String grade) {
        HashMap<String, Integer> subjectOccurrences = new HashMap<String, Integer>();
        subjectOccurrences = countSubs(grade); // count the number of occurences for each subject
        for (String i : gradeSubjectMap.secondaryKeySet(grade)) { // iterate through the subjects for a given grade
            if (subjectOccurrences.get(i) > 0) { // if the subject has more sessions than necessary
                List<Pair<Integer, Integer>> listOfOccurrences = getOccurences(i); // get the speicific occurences of
                                                                                   // the subject with extra sessions
                                                                                   // from the time table
                for (String k : gradeSubjectMap.secondaryKeySet(grade)) { // iterate through the subjects for a given
                                                                          // grade
                    while (listOfOccurrences.size() > 0 && subjectOccurrences.get(k) < 0) { // as long as there are
                                                                                            // extra sessions and the
                                                                                            // second selected subject
                                                                                            // has less sessions than
                                                                                            // required
                        Pair<Integer, Integer> a = getRandomElement(listOfOccurrences); // select a random occurence
                                                                                        // from the time table
                        table[a.x][a.y] = k; // and insert the subject which requires more sessions
                        listOfOccurrences.remove(a); // remove the extra occurence which was just replaced from the list
                        subjectOccurrences.put(i, subjectOccurrences.get(i) - 1); // update the number of occurences of
                                                                                  // the subjects
                        subjectOccurrences.put(k, subjectOccurrences.get(k) + 1);
                    }
                }
            }
        }
    }

}
