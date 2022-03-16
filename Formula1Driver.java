import java.io.Serializable;

public class Formula1Driver extends Driver implements Serializable {                                                    //Use of inheritance to use Driver class in Formula1Driver class
     private final int[] positions;                                                                                     //Array to store number of times driver finished in a certain position
     private int points;                                                                                                //Variable to store total points of driver
     private int numOfRaces;                                                                                            //Variable to store total races completed
     private final  static int[] pointSch = {25,18,15,12,10,8,6,4,2,1};                                                 //Final array containing Points Scheme created

    public Formula1Driver(String dName, String dLocation, String dTeam,int numOfRaces,int[] positions) {                //Driver Details Constructor
        super(dName, dLocation, dTeam);
        this.numOfRaces = numOfRaces;
        this.positions = positions;
        totalPts();                                                                                                     //method called carrying the calculated points
    }

    public void addPos(int pos){                                                                                       //Method used in addRace to increment number of times driver finishes in particular position
         this.positions[pos] += 1;
    }

    public void setNumOfRaces(int numOfRaces){                                                                         //Method used in addRace to increment number of races every time a race is completed
         this.numOfRaces = numOfRaces;
    }

    public int getPos(int i){                                                                                           //Method used to return number of times driver finished at a partiuclar position
        return positions[i];
    }

    public int getPts(){                                                                                                //Method used to return points
        return  points;
    }

    public int getNumOfRaces() {                                                                                        //Method used to return number of races
        return numOfRaces;
    }

    public void totalPts() {                                                                                            //Method used to calculate points earned based on points scheme
        points = 0;
        for (int i = 0; i < positions.length; i++) {
            points = positions[i] * pointSch[i] + points;
        }
    }

    public String toString(){                                                                                           //Converts saved data to readable string
        return super.toString() +
                "positions=" + positions +
                "points=" + points +
                "num of races=" + numOfRaces;

    }
}
