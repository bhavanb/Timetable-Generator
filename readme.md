# Timetable Generator
This is a Java program to generate class timetables for schools. Made for a school project
## Using the generator:
In order to use the timetable generator, first the inputs must be provided in the `inputs.csv` file. 
The format of `inputs.csv` is as follows:
The different grades for which the timetables are to be generated are the columns, and the subjects for those grades are the row headings. The data at the intersection of a grade and a subject is the number of sessions a class of that grade requires of the subject in a week. A sample has been provided. 
Run the following command to generate the timetables:
`javac lib/*;java classTimeTable.java -cp ./lib`
## Output:
The generated timetable(s) will be written to individual .csv files in the `out/` directory. Timings for break, lunch etc. are not currently customizable. This may be added in the future.

## Working:
The working of this project has been documented thoroughly with inline and Javadoc comments along with the code.