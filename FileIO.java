import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileIO {
    private Scanner FileScanner;

    public FileIO() {
    }
    
    /**
     * Method to read in the .csv file, grab first row as header, assign a Row Number
     * as primary 'key', with a 'value' hashmap of key = column name, value = cell
     * value
     * @return HashMap<Integer (rowNum), HashMap<String (header), String (cell)>>
     */
    public HashMap<Integer, HashMap<String, String>> readFlightFile() {
        HashMap<Integer, HashMap<String, String>> allData = new HashMap<Integer, HashMap<String, String>>();
        String filename = "flights_small.csv";
        //String filename = "flights.csv";
        try {
            FileScanner = new Scanner(new File(filename));
            Integer rowNum = 1;

            FileScanner.hasNextLine(); // Just wanting first line for headers
            String headerRow = FileScanner.nextLine();
            String[] headers = headerRow.split(",");

            /*
             * Parsing rows 2 to end Giving each a row num 'rowNum' starting at 1 Counting
             * rows of data 'rowsOfData' starting at 0
             */
            while (FileScanner.hasNextLine()) {
                String row = FileScanner.nextLine();
                String[] rowElements = row.split(",");
                rowNum++;

                /*
                 * Main 'Key' is the 'rowNum' 'Value' is a HashMap with: 'key' = column name
                 * 'value' = cell data
                 */
                Integer mainKey = rowNum;
                HashMap<String, String> tempMap = new HashMap<String, String>();


                for (int i = 0; i < headers.length; i++) {
                    String key = headers[i];
                    String value = rowElements[i];
                    tempMap.put(key, value);
                }
                allData.put(mainKey, tempMap);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file " + filename + " could not be found! Sorry!! Bye now...");
            e.printStackTrace();
        }
        return allData;
    }
 
    
    /**
     * Removes rows of data from 'fileReadIn' that have no Cancel Flag,
     * and have missing data points.
     * @param fileReadIn (HashMap) - Of original data read in.
     * @return fileReadIn (HashMap) - With removed rows.
     */
    public HashMap<Integer, HashMap<String, String>> cleanFile(HashMap<Integer, HashMap<String, String>> fileReadIn) {
        
        // Creates ArrayList of primary keys as row numbers
        ArrayList<Integer> keyList = new ArrayList<Integer>();
        for (Integer key : fileReadIn.keySet()) {
            keyList.add(key);
        }
        
        ArrayList<Integer> rowsWiBadData = new ArrayList<Integer>();
        
        for (int i = 2; i < keyList.size() + 2; i++) {
            String cancelFlag = fileReadIn.get(i).get("Cancelled");
            if (cancelFlag.equals("0")) { // checking that flight was not cancelled
                
                // checking if any of the columns have missing data
                if (fileReadIn.get(i).get("DayofMonth").isEmpty() ||
                        fileReadIn.get(i).get("DayofMonth").isEmpty() ||
                        fileReadIn.get(i).get("FlightDate").isEmpty() ||
                        fileReadIn.get(i).get("UniqueCarrier").isEmpty() ||
                        fileReadIn.get(i).get("OriginAirportID").isEmpty() ||
                        fileReadIn.get(i).get("Origin").isEmpty() ||
                        fileReadIn.get(i).get("OriginStateName").isEmpty() ||
                        fileReadIn.get(i).get("DestAirportID").isEmpty() ||
                        fileReadIn.get(i).get("Dest").isEmpty() ||
                        fileReadIn.get(i).get("DestStateName").isEmpty() ||
                        fileReadIn.get(i).get("DepTime").isEmpty() ||
                        fileReadIn.get(i).get("DepDelay").isEmpty() ||
                        fileReadIn.get(i).get("WheelsOff").isEmpty() ||
                        fileReadIn.get(i).get("WheelsOn").isEmpty() ||
                        fileReadIn.get(i).get("ArrTime").isEmpty() ||
                        fileReadIn.get(i).get("ArrDelay").isEmpty() ||
                        fileReadIn.get(i).get("Diverted").isEmpty() ||
                        fileReadIn.get(i).get("AirTime").isEmpty() ||
                        fileReadIn.get(i).get("Distance").isEmpty() ||
                        fileReadIn.get(i).get("TailNum").isEmpty()) {

                    if (!rowsWiBadData.contains(i)) {   //Checks if the row is already listed in ArrayList
                        rowsWiBadData.add(i);           // Adds row num to Arraylist, to be removed.
                    }
                }
            }
        }
        //Removes the rows identified as having missing data
        for (Integer num : rowsWiBadData) {
            fileReadIn.remove(num);
        }
        
        HashMap<Integer, HashMap<String, String>> cleanedFile = fileReadIn;
        return cleanedFile;
    }

}
