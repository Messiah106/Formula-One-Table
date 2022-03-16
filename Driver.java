import java.io.Serializable;

public abstract class Driver implements Serializable  {                                                                 //Abstract class created
    private String dName;                                                                                               //Variable to store driver name
    private final String dLocation;                                                                                     //Variable to store driver location
    private final String dTeam;                                                                                         //Variable to store driver team

    public Driver(String dName, String dLocation, String dTeam) {                                                       //Driver Constructor
        this.dName = dName;
        this.dLocation = dLocation;
        this.dTeam = dTeam;
    }

    public void setdName(String name){                                                                                  //Setter created to use for change driver method
        dName = name;
    }

    public String getdName() {                                                                                          //Getter created to return driver name
        return dName;
    }

    public String getdLocation() {                                                                                      //Getter created to return driver location
        return dLocation;
    }

    public String getdTeam() {                                                                                          //Getter created to return driver team
        return dTeam;
    }

    public String toString(){                                                                                           //Converts Name, team and Location to readable string
        return "Driver Name=" + dName +
                "Driver Team=" +dTeam +
                "Driver Location=" +dLocation;
    }
}
