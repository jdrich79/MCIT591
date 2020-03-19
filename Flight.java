import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Flight {
    private int rowNum;
    private int dayOfMonth;
    private int dayOfWeek;
    private Date date;
    private String carrier;
    private String tailNum;
    private int originAirportID;
    private String originLetters;
    private int destAirportID;
    private String destLetters;
    private int depTime;
    private int depDelay;
    private int arrTime;
    private int arrDelay;
    private boolean cancelled;
    private String cancelCode;
    private boolean diverted;
    private int airTime;
    private int distance;
    
    
    public Flight() {
        
    }
    
    public Flight(int rowNum, int dayOfMonth, int dayOfWeek, Date date, String carrier, String tailNum,
            int originAirportID, String originLetters, int destAirportID, String destLetters, int depTime, int depDelay,
            int arrTime, int arrDelay, boolean cancelled, String cancelCode, boolean diverted,
            int airTime, int distance) {
        
        
        
        this.rowNum = rowNum;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.carrier = carrier;
        this.tailNum = tailNum;
        this.originAirportID = originAirportID;
        this.originLetters = originLetters;
        this.destAirportID = destAirportID;
        this.destLetters = destLetters;
        this.depTime = depTime;
        this.depDelay = depDelay;
        this.arrTime = arrTime;
        this.arrDelay = arrDelay;
        this.cancelled = cancelled;
        this.cancelCode = cancelCode;
        this.diverted = diverted;
        this.airTime = airTime;
        this.distance = distance;
        
        
    }
    /**
     * Method that takes in file data processed into a HashMap, assigns specified data points
     * to variables, then creates a Flight object.
     * Each new Flight object is also added to an ArrayList of all Flight objects.
     * @param fileReadIn (HashMap) - The read in and cleaned data from the file.
     * @return (ArrayList) - An ArrayList contain each of the Flight objects.
     */
    public ArrayList<Flight> buildFlight (HashMap<Integer, HashMap<String, String>> fileReadIn) {
        
        // Return ArrayList of all Flight Objects
        ArrayList<Flight> flightList = new ArrayList<Flight>();
        
        // Creates ArrayList of primary keys, or row numbers
        ArrayList<Integer> keyList = new ArrayList<Integer>();
        for (Integer key : fileReadIn.keySet()) {
            keyList.add(key);
        }
        int counter = 0; // counter for testing number of rows read in
        for (Integer key : keyList) {
            
            // Assigning each column of data to a variable, 
            // converting to Integer, Boolean, or Date as needed.
      
            if(!fileReadIn.get(key).get("DayofMonth").isBlank()) {
                dayOfMonth = Integer.parseInt(fileReadIn.get(key).get("DayofMonth"));
            } else {dayOfMonth = 0;}
            

            if(!fileReadIn.get(key).get("DayOfWeek").isBlank()) {
                dayOfWeek = Integer.parseInt(fileReadIn.get(key).get("DayOfWeek"));
            } else {dayOfWeek = 0;}
        
            
            String dateTemp = fileReadIn.get(key).get("FlightDate");
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(dateTemp);
            } catch (ParseException e) {
                System.out.println("Issue with a date conversion!!");
                e.printStackTrace();
            }

            carrier = fileReadIn.get(key).get("UniqueCarrier");
            
            tailNum = fileReadIn.get(key).get("TailNum");


            if(!fileReadIn.get(key).get("OriginAirportID").isBlank()) {
                originAirportID = Integer.parseInt(fileReadIn.get(key).get("OriginAirportID"));
            } else {originAirportID = 0;}
            

            originLetters = fileReadIn.get(key).get("Origin");
            
            

            if(!fileReadIn.get(key).get("DestAirportID").isBlank()) {
                destAirportID = Integer.parseInt(fileReadIn.get(key).get("DestAirportID"));
            } else {destAirportID = 0;}
            
            destLetters = fileReadIn.get(key).get("Dest");
            
            
            if(!fileReadIn.get(key).get("DepTime").isBlank()) {
                depTime = Integer.parseInt(fileReadIn.get(key).get("DepTime"));
            } else {depTime = 0;}
            
       
            if(!fileReadIn.get(key).get("DepDelay").isBlank()) {
                depDelay = Integer.parseInt(fileReadIn.get(key).get("DepDelay"));
            } else {depDelay = 0;}
            
            
            if(!fileReadIn.get(key).get("ArrTime").isBlank()) {
                arrTime = Integer.parseInt(fileReadIn.get(key).get("ArrTime"));
            } else {arrTime = 0;}
            

            if(!fileReadIn.get(key).get("ArrDelay").isBlank()) {
                arrDelay = Integer.parseInt(fileReadIn.get(key).get("ArrDelay"));
            } else {arrDelay = 0;}
            

            String cancelledTemp = fileReadIn.get(key).get("Cancelled");
            if (cancelledTemp.equals("1")) {
                cancelled = true;
            } else {cancelled = false;}

            
            cancelCode = fileReadIn.get(key).get("CancellationCode");

            
            String divertedTemp = fileReadIn.get(key).get("Diverted");
            if (divertedTemp.equals("1")) {
                diverted = true;
            } else {diverted = false;}

            
            if(!fileReadIn.get(key).get("AirTime").isBlank()) {
                airTime = Integer.parseInt(fileReadIn.get(key).get("AirTime"));
            } else {airTime = 0;}

            
            if(!fileReadIn.get(key).get("Distance").isBlank()) {
                distance = Integer.parseInt(fileReadIn.get(key).get("Distance"));
            } else {distance = 0;}
            
                        
            // Creating new object of Flight for each row of data.
            Flight flight = new Flight(key, dayOfMonth, dayOfWeek, date, carrier, tailNum, originAirportID,
                    originLetters, destAirportID, destLetters, depTime, depDelay, arrTime, 
                    arrDelay, cancelled, cancelCode, diverted, airTime, distance);

            counter++;
            flightList.add(flight); // adding each new object of Flight to ArrayList 'flightList'
        }
        
//        // FOR TEST PRINTING
//        System.out.println("Number of Flight objects created: " + counter);
//        int limit = 25;
//        int i = 0;
//        for (Flight flight : flightList) {
//            if (i < limit) {
//                String flightDetails = "Plane " + flight.getTailNum() + " from " + flight.getOriginLetters() + " to " + flight.getDestLetters() +
//                        " took off at " + flight.getDepTime() + " and landed at " + flight.getArrTime() + ".\n The flight took " + flight.getAirTime() +
//                        " minutes. With arrival/depature delays of " + flight.getArrDelay() + " and " + flight.getDepDelay() + " ,respectively.";
//                System.out.println(flightDetails);
//                System.out.println();
//                i++;
//            } else {break;}
//        }
        return flightList;
    }
    


    /*
     * Getter Methods
     */
    public int getRowNum() {
        return rowNum;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public Date getDate() {
        return date;
    }

    public String getTailNum() {
        return tailNum;
    }

    public String getCarrier() {
        return carrier;
    }

    public int getOriginAirportID() {
        return originAirportID;
    }

    public String getOriginLetters() {
        return originLetters;
    }

    public int getDestAirportID() {
        return destAirportID;
    }

    public String getDestLetters() {
        return destLetters;
    }

    public int getDepTime() {
        return depTime;
    }

    public int getDepDelay() {
        return depDelay;
    }

    public int getArrTime() {
        return arrTime;
    }

    public int getArrDelay() {
        return arrDelay;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public String getCancelCode() {
        return cancelCode;
    }

    public boolean isDiverted() {
        return diverted;
    }

    public int getAirTime() {
        return airTime;
    }

    public int getDistance() {
        return distance;
    }
}
