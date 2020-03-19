import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Assignment {
    Flight flight;
    
    public Assignment() {
        this.flight = new Flight();
        
    }
    /**
     * Method for response to Question 1 of assignment.
     * @param flList (ArrayList) - ArrayList of all Flight Objects
     * @return (String) - Answer to Q1
     */
    public String questionOne(ArrayList<Flight> flList) {
        HashMap<String, int[]> cancelVsTotal = new HashMap<String, int[]>();
        
        /*
         * Iterate thru flights and count total flights by carrier
         * in a HashMap with with the Key as the Carrier and
         * the Value a two element Array with total flights at [1]
         */
        for(Flight flight1 : flList) {
            String carrier1 = flight1.getCarrier();
            
            // check if carrier already exists in 'cancelVsTotal' HashMap
            if (cancelVsTotal.containsKey(carrier1)) {
                int[] toUpdate = cancelVsTotal.get(carrier1);
                int countOfFlights = toUpdate[1];
                countOfFlights++;
                toUpdate[1] = countOfFlights;
                
                cancelVsTotal.put(carrier1, toUpdate);
            }
            else {
                int[] numOfFlights1 = new int[2];
                numOfFlights1[1] = 1;
                cancelVsTotal.put(carrier1, numOfFlights1);
            }
        }
        /*
         * Iterate thru flights again counting number of cancellations
         * and placing them within the Array nested in the HashMap at [0] 
         */
        for (Flight flight2 : flList) {
            String carrier2 = flight2.getCarrier();
            
            // checking the 'Cancelled' value is true
            if (flight2.isCancelled() == true) {
                // check if carrier already exists in 'cancelVsTotal' HashMap
                if (cancelVsTotal.containsKey(carrier2)) {
                    int[] toUpdate2 = cancelVsTotal.get(carrier2);
                    int countOfCancels = toUpdate2[0];
                    countOfCancels++;
                    toUpdate2[0] = countOfCancels;
                    
                    cancelVsTotal.put(carrier2, toUpdate2);
                }
                else {
                    int[] numOfCancels = new int[2];
                    numOfCancels[0] = 1;
                    cancelVsTotal.put(carrier2, numOfCancels);
                }
            }
        }
        
        /*
         * With the cancels and total flights by Carrier collected,
         * extracting the two values, dividing them into a Double and 
         * creating a new HashMap with Key = Carrier, Value = Percentage of Cancellations
         */
        
        HashMap<String, Double> cancelPercent = new HashMap<String, Double>();
        
        for (String carrier : cancelVsTotal.keySet()) {
            String key = carrier.toString();
            
            // calculating the percentage of cancel per carrier
            int[] totals = cancelVsTotal.get(key);
            Double cancel = (double) totals[0];
            Double total = (double) totals[1];
            Double percent = (double) (cancel / total);
            cancelPercent.put(carrier, percent);
        }
        
        String maxCarrier = " ";
        Double maxPercent = 0.0;
        
        // finding the carrier with the highest cancel percentage
        for (String carrier : cancelPercent.keySet()) {
            Double value = cancelPercent.get(carrier);
            if (value > maxPercent) {
                maxCarrier = carrier;
                maxPercent = value;
            }
        }
        // formatting for proper output
        Double returnPercent = maxPercent * 100;
        String percent = returnPercent.toString();
        String toReturn = maxCarrier + "," + percent;
        
        System.out.println("#1: " + toReturn + "   REMOVE THESE BEFORE SUBMITTING!!");
        
        return toReturn;
                
    }
    
    /**
     * Method for response to Q2
     * @param  flList (ArrayList) - ArrayList of all Flight Objects
     * @return (String) - Answer to Q2
     */
    public String questionTwo(ArrayList<Flight> flList) {
        HashMap<String, Integer> cancelCodeCount = new HashMap<String, Integer>();
        
        // checking that flight was cancelled
        for (Flight flight : flList) {
            if (flight.isCancelled() == true) {
                String cancelCode = flight.getCancelCode();
                
                // checking if cancel code already in HashMap,
                // to start count of totals for each cancel code
                if (cancelCodeCount.containsKey(cancelCode)) {
                    int currentCount = cancelCodeCount.get(cancelCode);
                    currentCount++;
                    cancelCodeCount.put(cancelCode, currentCount);
                }
                // if the code was not already in the HashMap,
                // add the code as the key and set count to 1
                else {
                    cancelCodeCount.put(cancelCode, 1);
                } 
            }
        }
        String maxCancelCode = "";
        int maxCancel = 0;
        // finding the cancel code with the max count
        for (String code : cancelCodeCount.keySet()) {
            int cancelCount = cancelCodeCount.get(code);
            
            if (cancelCount > maxCancel) {
                maxCancelCode = code;
                maxCancel = cancelCount;
            }
        }

        System.out.println("#2: " + maxCancelCode);
        
        return maxCancelCode;
    }

    /**
     * Method for response to Q3
     * @param flList (ArrayList) - ArrayList of all Flight Objects
     * @return (String) - Answer to Q3
     */
    public String questionThree(ArrayList<Flight> flList) {
        HashMap<String, Integer> planeMiles = new HashMap<String, Integer>();
        
        // check to only look at flights that were not cancelled
        for (Flight flight : flList) {
            if (flight.isCancelled() == false) {
                
                String tailNum = flight.getTailNum();
                
                /* 
                 * if the tailNum already exists in HashMap, get the current total miles,
                 * then add the existing flight miles to that total and
                 * return the tailNum with new total back to the HashMap
                 */
                if (planeMiles.containsKey(tailNum)) {
                    int milesOfFlight = planeMiles.get(tailNum);
                    int addMiles = flight.getDistance();
                    int newMiles = milesOfFlight + addMiles;
                    
                    planeMiles.put(tailNum, newMiles);
                }
                // if the tailNum is not already in HashMap, add it and
                // the existing flights miles to HashMap
                else {
                    int firstMiles = flight.getDistance();
                    planeMiles.put(tailNum, firstMiles);
                }
            }
        }
                
        String maxTailNum = "";
        int maxMiles = 0;
        // iterate through all the flights in the 'planeMiles' HashMap
        // to find the tailNum with the max miles.
        for (String tailNum : planeMiles.keySet()) {
            int totalMiles = planeMiles.get(tailNum);
            if (totalMiles > maxMiles) {
                maxTailNum = tailNum;
                maxMiles = totalMiles;
            }
        }
        
        System.out.println("#3: " + maxTailNum);
        
        return maxTailNum;
    }
    
    /**
     * Method for response to Q4
     * @param flList (ArrayList) - ArrayList of all Flight Objects
     * @return (int) - Answer to Q4
     */
    public int questionFour(ArrayList<Flight> flList) {
        HashMap<Integer, Integer> flightCntAirport = new HashMap<Integer, Integer>();
        
        for (Flight flight : flList) {
            int flightIn = flight.getOriginAirportID();
            // obtain each flights 'OriginAirportID', then check if it is in
            // the 'flightCntAirport' HashMap, adding 1 for each time it occurs
            if (flightCntAirport.containsKey(flightIn)) {
                int numOfFlights = flightCntAirport.get(flightIn);
                numOfFlights++;
                flightCntAirport.put(flightIn, numOfFlights);
                
            }
            // if not already in HashMap, add it and set count at 1
            else {
                flightCntAirport.put(flightIn, 1);
            }
        }
        
        for (Flight flight : flList) {
            int flightOut = flight.getDestAirportID();
            // obtain each flights 'DestAirportID', then check if it is in
            // the 'flightCntAirport' HashMap, adding 1 for each time it occurs
            if (flightCntAirport.containsKey(flightOut)) {
                int numOfFlights = flightCntAirport.get(flightOut);
                numOfFlights++;
                flightCntAirport.put(flightOut, numOfFlights);
                
            }
            // if not already in HashMap, add it and set count at 1
            else {
                flightCntAirport.put(flightOut, 1);
            }
        }

        int max = 0;
        int airportMax = 0;
        // iterate through all data in 'flightCntAirport' to find airport
        // with max number of flights in and out
        for (int airport : flightCntAirport.keySet()) {
            if (flightCntAirport.get(airport) > max) {
                max = flightCntAirport.get(airport);
                airportMax = airport;
            }
        }
        
        System.out.println("#4: "+ airportMax);
        
        return airportMax;
    }

    /**
     * Method for response to Q5
     * @param flList (ArrayList) - ArrayList of all Flight Objects
     * @return (int) - Answer to Q5
     */
    public int questionFive(ArrayList<Flight> flList) {
        HashMap<Integer, int[]> sourceAvsD = new HashMap<Integer, int[]>();
        
        /*
         * Creating a HashMap with Key = OriginAirportID &
         * the Value is an Int Array with two values: 
         * 1. count of departing flights
         * 2. count of arriving flights
         */
        for (Flight flight : flList) {
            // check to ensure only looking at data for flights not cancelled
            if (flight.isCancelled() == false) {
                int originID = flight.getOriginAirportID();
                // check if HashMap already contain the key value,
                // if so grabbing the [0] element of the int array and 
                // increasing the value by 1 - count of departing flights
                if (sourceAvsD.containsKey(originID)) {
                    int[] values = sourceAvsD.get(originID);
                    int originCount = values[0];
                    originCount++;
                    values[0] = originCount;
                    
                    sourceAvsD.put(originID, values);
                }
                // if the 'originID' key does not already exist in the HashMap,
                // adding it and setting the [0] value to 1;
                else {
                    int[] newOrigin = new int[2];
                    newOrigin[0] = 1;
                    sourceAvsD.put(originID, newOrigin);
                }
            }
        }
        
        for (Flight flight : flList) {
            
            if (flight.isCancelled() == false) {
                int originID2 = flight.getDestAirportID();
                // check if HashMap already contain the key value,
                // if so grabbing the [1] element of the int array and 
                // increasing the value by 1 - count of arriving flights
                if (sourceAvsD.containsKey(originID2)) {
                    int[] values2 = sourceAvsD.get(originID2);
                    int originCount2 = values2[1];
                    originCount2++;
                    values2[1] = originCount2;
                    
                    sourceAvsD.put(originID2, values2);
                }
                else {
                    int[] newOrigin2 = new int[2];
                    newOrigin2[1] = 1;
                    sourceAvsD.put(originID2, newOrigin2);
                }
            }
        }
        /*
         * Using the above HashMap, creating a new HashMap with
         * Key = OriginAirportID
         * Value =  "source" or number of flights in minus number of flights out
         */
        HashMap<Integer, Integer> sourceCount = new HashMap<Integer, Integer>();
        
        for (Integer originID : sourceAvsD.keySet()) {
            Integer key = originID;
            int[] inAndOut = sourceAvsD.get(key);
            int in = inAndOut[0];
            int out = inAndOut[1];
            int source = in - out;
            
            sourceCount.put(originID, source);
        }
        
        Integer maxOriginID = 0;
        Integer maxSource = 0;
        // iterating through the 'sourceCount' HashMap to find airport with max "source"
        for (Integer originID : sourceCount.keySet()) {
            Integer sourceCnt = sourceCount.get(originID);
            if (sourceCnt > maxSource) {
                maxOriginID = originID;
                maxSource = sourceCnt;
            }
        }
        
        System.out.println("#5: " + maxOriginID);
        
        return maxOriginID;
    }
  
    /**
     * Method for response to Q6. 
     * Essential the same as Q5 with a different calculation toward the end.
     * @param flList (ArrayList) - ArrayList of all Flight Objects
     * @return (int) - Answer to Q6
     */
    public int questionSix(ArrayList<Flight> flList) {
        HashMap<Integer, int[]> sinkAvsD = new HashMap<Integer, int[]>();
        /*
         * Creating a HashMap with Key = OriginAirportID &
         * the Value is an Int Array with two values: 
         * 1. count of departing flights
         * 2. count of arriving flights
         */
        for (Flight flight : flList) {
            
            if (flight.isCancelled() == false) {
                int originID = flight.getDestAirportID();
                
                if (sinkAvsD.containsKey(originID)) {
                    int[] values = sinkAvsD.get(originID);
                    int originCount = values[0];
                    originCount++;
                    values[0] = originCount;
                    
                    sinkAvsD.put(originID, values);
                    
                }
                else {
                    int[] newOrigin = new int[2];
                    newOrigin[0] = 1;
                    sinkAvsD.put(originID, newOrigin);
                }
            }
        }
        
        for (Flight flight : flList) {
            
            if (flight.isCancelled() == false) {
                int originID2 = flight.getOriginAirportID();
               
                if (sinkAvsD.containsKey(originID2)) {
                    int[] values2 = sinkAvsD.get(originID2);
                    int originCount2 = values2[1];
                    originCount2++;
                    values2[1] = originCount2;
                    
                    sinkAvsD.put(originID2, values2);

                }
                else {
                    int[] newOrigin2 = new int[2];
                    newOrigin2[1] = 1;
                    sinkAvsD.put(originID2, newOrigin2);
                }
            }
        }
        
        HashMap<Integer, Integer> sinkCount = new HashMap<Integer, Integer>();
        // calculating flights out minus flights in
        for (Integer originID : sinkAvsD.keySet()) {
            Integer key = originID;
            int[] inAndOut = sinkAvsD.get(key);
            int out = inAndOut[0];
            int in = inAndOut[1];
            int sink = out - in;
            
            sinkCount.put(originID, sink);
            
        }
        
        Integer maxOriginID = 0;
        Integer maxSink = 0;
        // finding airport with max 'sink'
        for (Integer originID : sinkCount.keySet()) {
            Integer sinkCnt = sinkCount.get(originID);
            if (sinkCnt > maxSink) {
                maxOriginID = originID;
                maxSink = sinkCnt;
                
            }
        }
        
        System.out.println("#6: " + maxOriginID);
        
        return maxOriginID;
    }

    /**
     * Method for response to Q7.
     * @param flList (ArrayList) - ArrayList of all Flight Objects
     * @return (int) - Answer to Q7
     */
    public int questionSeven(ArrayList<Flight> flList) {
        int delayCount = 0;
        
        for (Flight flight : flList) {
            if (flight.isCancelled() == false) {        // check that flight was not cancelled
                if (flight.getCarrier().equals("AA")) { // check that flight was an "AA" carrier
                    
                    // setting the departure and arrival delays to variables.
                    Integer depDelay = flight.getDepDelay();
                    Integer arrDelay = flight.getArrDelay();
              
                    // The logic is that a if either is greater than 60 minutes the flight should be counted
                    // but if both are a 60+ minute delay, it only counts as one. Therefore, the "or" logic
                    // is used to ensure if both sides were delayed by 60+ the flight is only counted once.
                    if (depDelay >= 60 || arrDelay >= 60) {
                        delayCount++;
                    }
                }
            }
        }
        
        System.out.println("#7: " + delayCount);
        
        return delayCount;
    }
    
    /**
     * Method for response to Q8.
     * @param flList (ArrayList) - ArrayList of all Flight Objects
     * @return (String) - Answer to Q8
     */
    public String questionEight(ArrayList<Flight> flList) {
        int maxDayOfMonth = 0;
        int maxDepDelay = 0;
        String maxTailNum = "";
        
        
        for (Flight flight : flList) {
            if (flight.isCancelled() == false) {        // check that flight is not cancelled
                if (flight.getDepDelay() > 0) {         // check that there was a departure delay
                    if (flight.getArrDelay() <=0 ) {    // check that the flight arrived at or before "on-time" and
                                                        // therefore the delay was made-up
                        
                        // grabbing the details of the flight that need to be returned in response
                        int dayOfMonth = flight.getDayOfMonth();
                        int depDelay = flight.getDepDelay();
                        String tailNum = flight.getTailNum();
                        // checking for flight with the largest time made-up
                        if (depDelay > maxDepDelay) {
                            maxDayOfMonth = dayOfMonth;
                            maxDepDelay = depDelay;
                            maxTailNum = tailNum;
                        }
                    }
                }
            }
        }
        // formatting response for correct answer output
        String output = maxDayOfMonth + "," + maxDepDelay + "," + maxTailNum;
        
        System.out.println("#8: " + output);
        
        return output;
    }
    
    /**
     * Method for our own question number 9 and answer.
     * Question - What was the max miles/hour a plane flew?
     * @param flList (ArrayList) - ArrayList of all Flight Objects
     * @return (String) - Maximum MPH a plane flew in the data set
     */
    public String questionNine(ArrayList<Flight> flList) {
        /*
         * What was the max speed a plane flew?
         */
        
        Double maxSpeed = 0.0;
        String maxPlane = "";
        double maxDist = 0.0;
        double maxAirTime = 0.0;

        
        for (Flight flight : flList) {
            if(flight.isCancelled() == false) { // check that flight was not cancelled
                
                int airTime = flight.getAirTime();      // set flight's air time to a variable
                double airTimeHours = airTime / 60.0;   // convert air time to hours
                int distance = flight.getDistance();    // set flight's distance to a variable
                
                double speed = distance / airTimeHours; // calculate speed as distance "mile" divided by flight time in hours
                
                // find the flight that had the max speed in MPH
                if (speed > maxSpeed) {
                    maxSpeed = speed;
                    maxPlane = flight.getTailNum();
                    maxDist = distance;
                    maxAirTime = airTimeHours;
                }
            }
        }
        
        String speed = maxSpeed.toString().substring(0, 6);
        
        System.out.println("#9: " + speed);
        
        return speed;
    }
}

