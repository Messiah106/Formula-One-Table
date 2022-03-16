import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Formula1ChampionshipManager implements ChampionshipManager {

    public Scanner F1CsM = new Scanner(System.in);                                                                      //A public object of Scanner class is created
    public String temp;                                                                                                 //A public string is created
    protected static ArrayList<Formula1Driver> f1Driver = new ArrayList<>();                                            //An arraylist is created to store driver details
    protected static ArrayList<Race> race = new ArrayList<>();                                                          //An arraylist is created to store race details
    protected static ArrayList<Formula1Driver> pSort;                                                                   //An arraylist is created to store existing drivers but in descending order

    public Formula1ChampionshipManager() {                                                                              //Load method is called to load previously saved data
        load();
    }

    public void menu() {                                                                                                //Menu method has been created to print menu options and allow user to navigate through program
        boolean x = false;
        while (!x) {
            System.out.println("\nWelcome to Formula One Championship!");
            System.out.println("\nChoose an option from the menu below:");
            System.out.println("\nA: Add a driver");
            System.out.println("C: Change the Driver for Team");
            System.out.println("D: Delete a Driver");
            System.out.println("V: Display Stats");
            System.out.println("F: Display Formula1 Table");
            System.out.println("R: Add Complete Race Stats");
            System.out.println("G: Graphical User Interface");
            System.out.println("X: Exit Program");

            String option = F1CsM.next().toUpperCase();                                                                 //User asked to choose option from Menu
            switch (option) {
                case ("A"):
                    add();
                    break;
                case ("D"):
                    delete();
                    break;
                case ("C"):
                    change();
                    break;
                case ("V"):
                    view();
                    break;
                case ("F"):
                    viewTable();
                    break;
                case ("R"):
                    addRace();
                    break;
                case ("G"):
                    new gui();
                    break;
                case ("X"):
                    x = true;
                    break;
                default:
                    System.out.println("Invalid Input");                                                                //Message Appears if Input is invalid
            }
            store();                                                                                                    //store method called to save user progress
        }
    }

    public void add() {                                                                                                 //Method to add driver details
        int[] position = new int[10];                                                                                   //Array created to store position inside 1-10
        int races = 0;                                                                                                  //Variable to store number of races completed details

        System.out.println("Enter Name of the Driver: ");
        String name = F1CsM.next();

        System.out.println("Enter Driver's Location: ");
        String location = F1CsM.next();

        System.out.println("Enter Driver's Team: ");
        String team = F1CsM.next();

        do {
            System.out.println("Which place did " + name + " finish?   : (1 - 10)");
            int place = F1CsM.nextInt();
            System.out.println("How many times did " + name + " finish " + place + " : ");
            int times = F1CsM.nextInt();
            position[place - 1] = times;
            System.out.println("Do you want to enter more points? y/n");
            temp = F1CsM.next().toLowerCase();
                                                                                                                        //user asked if they want to keep entering points
        } while (!temp.equals("n"));                                                                                    //if user input is not 'n', it keeps looping
        for (int x : position) {
            races = races + x;
        }

        System.out.println("Name of Driver      : " + name);
        System.out.println("Location of Driver  : " + location);
        System.out.println("Team of Driver      : " + team);

        System.out.println("\nDo You want to add this Driver to the Championship Table? y/n)");                         //User asked if they want to add input to Table
        temp = F1CsM.next().toLowerCase();

        if (temp.equals("y")) {
            Formula1Driver driverAdded = new Formula1Driver(name, location, team, races, position);                     //if user enters 'y', details get stored to f1Driver, leading to Formula1Driver class
            f1Driver.add(driverAdded); // add driver to list
            System.out.println("\nDriver Details added to Championship!");
            sortDrivers();
        } else {
            System.out.println("\nInvalid>>> No Driver has been added!!!");                                             //If user input not 'y', returns to main menu
        }
    }

    public void delete() {                                                                                              //Method to delete drivers from table
        System.out.println("\nEnter Index of Driver you want to Delete: ");
        System.out.println("");
        printDriverList();                                                                                              //Driver details printed

        while (true) {                                                                                                  //user input is asked in a loop till user inputs valid loop
            int index = F1CsM.nextInt();
            if (index <= f1Driver.size()) {
                Formula1Driver f1 = f1Driver.remove(index);                                                             //the details which matches the user input in the f1Driver is deleted
                System.out.println("\n" + f1.getdName() + " has been Deleted!!!");
                break;                                                                                                  //program breaks out of loop once driver has been deleted

            } else {
                System.out.println("\nInvalid input");
                System.out.println("Try again");
                System.out.println("");
                printDriverList();                                                                                      //Driver details printed again when user input isn't valid
            }
        }
    }

    public void view() {                                                                                                //Method to view all drivers and their details (Name, team, location, rankings & points)
        sortDrivers();
        System.out.println("Select Driver: ");
        printDriverList();                                                                                              //Another method is called to print all the drivers
        while (true) {                                                                                                  //Will loop till user enters a valid input
            int input = F1CsM.nextInt();                                                                                //Users are asked to enter index to choose driver
            if (input <= f1Driver.size()) {
                Formula1Driver f1 = f1Driver.get(input);
                System.out.println("\nDriver's Name: " + f1.getdName());
                System.out.println("Driver's Location: " + f1.getdLocation());
                System.out.println("Driver's Team: " + f1.getdTeam());
                System.out.println("Number of Races: " + f1.getNumOfRaces());
                System.out.println("Points: " + f1.getPts());
                break;                                                                                                  //Break out of loop
            } else {
                System.out.println("Invalid input");
                System.out.println("Try again");
                System.out.println("");                                                                                 //Allows user to choose driver again if they input invalid index
                printDriverList();
            }
        }
    }

    public void change() {                                                                                              //Method to allow user to change driver to another team
        System.out.println("Select team to change driver  ");
        printDriverList();                                                                                              //Available Drivers are printed
        while (true) {                                                                                                  //Loops till user enters valid input
            int input = F1CsM.nextInt();
            if (input <= f1Driver.size()) {
            System.out.println("Enter New Driver for " + f1Driver.get(input).getdTeam());
            temp = F1CsM.next();
            f1Driver.get(input).setdName(temp);
            sortDrivers();
                break;                                                                                                  //Break out of loop
            } else {
                System.out.println("Invalid input");
                System.out.println("Try again");
                System.out.println("");                                                                                 //Allows user to choose driver again if they input invalid index
                printDriverList();
            }
        }
    }

    public void viewTable() {                                                                                           //Method created to display drivers, their teams and points in a table format
        sortDrivers();
        System.out.println(">>>>>>>>>>>>>>>>>>> FORMULA ONE POINTS TABLE >>>>>>>>>>>>>>>>>>>");
        System.out.println("\tname\t                 Team\t                 Points\t");                                 //Table structure
        for (Formula1Driver f1 : pSort) {
            System.out.println(f1.getdName() + "\t                      " +
                    f1.getdTeam() + "\t                       " +
                    f1.getPts() + "\t");
        }
    }

    public void addRace() {                                                                                             //Method to add a race on a specific date
        System.out.println("Enter date of race in yyyy-mm-dd format: ");
        String dateInput = F1CsM.next();
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateInput);                                          //date format set as Year-Month-Day
        } catch (ParseException ex) {
            System.out.println("Invalid Date Format");
            return;
        }
        int i = 0;

        ArrayList<Formula1Driver> f1copy = new ArrayList<>(f1Driver);
        ArrayList<Formula1Driver> racesPos = new ArrayList<>();
        while (i < f1Driver.size()) {
            for (Formula1Driver drivers : f1copy) {
                System.out.println(drivers.getdName());
            }
            System.out.println("Enter driver who won position: " + (i + 1));
            temp = F1CsM.next();
            Formula1Driver selectedDriver = null;
            for (Formula1Driver f1 : f1Driver) {
                if (f1.getdName().equalsIgnoreCase(temp) && f1copy.contains(f1)) {
                    selectedDriver = f1Driver.get(f1Driver.indexOf(f1));
                }
            }
            if (selectedDriver == null || f1Driver.contains(selectedDriver) == false) {
                System.out.println("Invalid Input");
            } else {
                selectedDriver.setNumOfRaces(selectedDriver.getNumOfRaces() + 1);
                selectedDriver.addPos(i);
                selectedDriver.totalPts();
                racesPos.add(selectedDriver);
                sortDrivers();
                f1copy.removeIf(Formula1Driver -> Formula1Driver.getdName().equalsIgnoreCase(temp));
                i++;                                                                                                    //https://stackoverflow.com/questions/36028995/remove-object-from-arraylist-with-some-object-property
            }
        }
        race.add(new Race(date, racesPos));                                                                             //Adding Race details and positions to race arraylist
    }

    public void sortDrivers() {                                                                                         //Method to sort drivers by descending order based on Points
        pSort = new ArrayList<>(f1Driver);

        Collections.sort(pSort, new Comparator<>() {

            public int compare(Formula1Driver d1, Formula1Driver d2) {
                if (d1.getPts() > d2.getPts()) {                                                                        //checks if first racers' points is more than second or second racers' is more
                    return -1;                                                                                          //https://www.youtube.com/watch?v=dRX6qO46l44
                } else if (d1.getPts() < d2.getPts()) {
                    return 1;
                } else {
                    if (d1.getPos(0) > d2.getPos(0)) {
                        return -1;
                    } else if (d1.getPos(0) < d2.getPos(0)) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        });
    }

    public void store() {                                                                                               //Method to store data
        try {
            FileOutputStream fo = new FileOutputStream("DriverFile.txt");                                         //data stored in DriverFile.txt
            ObjectOutputStream ob = new ObjectOutputStream(fo);

            ob.writeObject(f1Driver);

            ob.close();
            fo.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("");
        }
    }
                                                                                                                        //https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    public void load() {                                                                                                //Method to load saved data
        try {
            FileInputStream fs = new FileInputStream("DriverFile.txt");                                           //Data load from DriverFile.txt
            ObjectInputStream os = new ObjectInputStream(fs);

            f1Driver = (ArrayList<Formula1Driver>) os.readObject();

            os.close();
            fs.close();

        } catch (IOException e) {
            System.out.println("No File Available");
        } catch (ClassNotFoundException e) {
            System.out.println("");
        }
    }

    public void printDriverList() {                                                                                     //Method to print list of stored drivers
        for (int i = 1; i < f1Driver.size(); i++) {
            System.out.println(i + ". " + f1Driver.get(i).getdName() + " " + f1Driver.get(i).getdTeam());               //driver details are printed (Name and Team)
        }
    }
}