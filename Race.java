import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Race implements Serializable {                                                                             //Serializable is used to save race details

    private final Date date;                                                                                            //date variable is created; a product of Date data type
    private final ArrayList<Formula1Driver> racer;                                                                      //Arraylist created to store drivers in their finishing order

    public Race(Date date, ArrayList<Formula1Driver> racer){                                                            //Race constructor
        this.date = date;
        this.racer = racer;
    }

    public Date getDate() {                                                                                             //returns date of race
        return date;
    }

    public ArrayList<Formula1Driver> getRacer(){                                                                        //returns driver list in their finishing order
        return racer;
    }

    public String toString(){                                                                                           //Converts saved data to readable string
        return "date" + date
                + "racer" + racer;
    }
}
