import java.util.ArrayList;
import java.util.HashMap;

public class RunnerClass {
    
    /**
     * Method to house all the functionality to run the program.
     */
    public void run() {
        // Create new instance of the FileIO class
        FileIO reader = new FileIO();
        
        // Read the file data file into a HashMap with the 'readFlightFile' method
        HashMap<Integer, HashMap<String, String>> dataHMap = reader.readFlightFile();
        
        // Run the data read in through the 'cleanFile' method to remove rows with missing data that were not cancelled.
        HashMap<Integer, HashMap<String, String>> cleanedHMap = reader.cleanFile(dataHMap);
        
        // Create new instance of the Flight class
        Flight flights = new Flight();  

        /*
         *  Using the 'buildFlight' method, new instance of the Flight class are
         *  created from each row of data. And each Flight instance is added to 
         *  an ArrayList 'flightList' to be used as the data source for each
         *  question in the assignment. 
         */
        ArrayList<Flight> flightList = flights.buildFlight(cleanedHMap); 
        
        /* 
         * Create new instance of the Assignment class,
         * and then run each of methods associated with the
         * nine questions from the assignment.
         */
        Assignment hmwrk = new Assignment();
        String q1 = hmwrk.questionOne(flightList);
        String q2 = hmwrk.questionTwo(flightList);
        String q3 = hmwrk.questionThree(flightList);
        int q4 = hmwrk.questionFour(flightList);
        int q5 = hmwrk.questionFive(flightList);
        int q6 = hmwrk.questionSix(flightList);
        int q7 = hmwrk.questionSeven(flightList);
        String q8 = hmwrk.questionEight(flightList);
        String q9 = hmwrk.questionNine(flightList);
        
        /*
         * Create new instance of the FormattedOutput class,
         * and then added the answer found for each of the 
         * assignment questions.
         * 
         * Then called the 'writeAnswer' method to produce the
         * answers.txt document.
         */
        FormattedOutput output = new FormattedOutput();
        output.addAnswer(1, q1);
        output.addAnswer(2, q2);
        output.addAnswer(3, q3);
        output.addAnswer(4, q4);
        output.addAnswer(5, q5);
        output.addAnswer(6, q6);
        output.addAnswer(7, q7);
        output.addAnswer(8, q8);
        output.addAnswer(9, q9);
        output.writeAnswers();
    }
}
