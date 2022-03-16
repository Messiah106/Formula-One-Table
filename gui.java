import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class gui extends  Formula1ChampionshipManager implements ActionListener {                                       //GUI class created inheriting from Formula1ChampionshipManager

    private final String[] columns ={"Name","Team","Location","Points","1st","2nd","3rd"} ;                             //First pages' columns
    private final String[] searchDColumns = {"Date ", "Position"};                                                      //Columns appearing in the search driver tab
    private final String[] viewRColumns = {"Date","1st","2nd","3rd"};                                                   //Columns appearing in the view race tab

    private final JTable dTable;

    private final JButton rRace = new JButton("Generate Random Race");                                              //Generate Random Race Object created
    private final JButton viewRace = new JButton("View Race");                                                      //View Race Object created
    private final JButton refreshPage = new JButton("Refresh Page");                                                //Refresh Page Object created
    private final JButton search = new JButton("Search Driver");                                                    //Search Driver Object created
    private final JRadioButton AscBut = new JRadioButton("Ascending Order");                                        //Ascending Order Object created
    private final JRadioButton DescBut = new JRadioButton("Descending Order");                                      //Descending Order Object created
    private final JCheckBox sortFP = new JCheckBox("Sort by First Position");                                       //Sort by First Position Object created

    private final JTextField text;                                                                                      //Text area to type
    private final JLabel dLabel = new JLabel();                                                                         //Label to show drivers name

    public gui(){                                                                                                       //GUI Constructor

        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(2,1));

        DefaultTableModel tableModel = new DefaultTableModel(driverDetails(pSort), columns);                            //to Create table passing column names and data

        dTable = new JTable(tableModel);
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(dTable));                                                                             //adding table to panel

        ButtonGroup group = new ButtonGroup();                                                                          //to select one button at a time
        group.add(AscBut);
        group.add(DescBut);

        DescBut.setSelected(true);                                                                                      //Drivers Appear in descending
        AscBut.setBounds(20,700,150,50);                                                              //Ascending button dimension and location
        AscBut.addActionListener(this);
        JPanel panel2 = new JPanel();
        panel2.add(AscBut);
        DescBut.setBounds(200,700,150,50);
        DescBut.addActionListener(this);
        panel2.add(DescBut);

        sortFP.addActionListener(this);
        panel2.add(sortFP);

        rRace.setBounds(20,800,150,50);
        rRace.addActionListener(this);
        panel2.add(rRace);

        viewRace.setBounds(20,900,150,50);
        viewRace.addActionListener(this);
        panel2.add(viewRace);

        refreshPage.addActionListener(this);
        panel2.add(refreshPage);

        text = new JTextField(30);
        search.addActionListener(this);
        panel2.add(text);
        panel2.add(search);
        panel2.add(dLabel);                                                                                             //search driver components

        frame.add(panel);
        frame.add(panel2);

        frame.setSize(600,500);                                                                             //Frame Size
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Object[][] driverDetails(ArrayList<Formula1Driver> drivers){                                                 //Method to return driver details to display on the table

        Object[][] dDetails = new Object[drivers.size()][7];
        for(int i =0 ;i< drivers.size();i++){
            dDetails[i][0] = drivers.get(i).getdName();                                                                 //https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
            dDetails[i][1] = drivers.get(i).getdTeam();
            dDetails[i][2] = drivers.get(i).getdLocation();
            dDetails[i][3] = drivers.get(i).getPts();
            dDetails[i][4] = drivers.get(i).getPos(0);
            dDetails[i][5] = drivers.get(i).getPos(1);
            dDetails[i][6] = drivers.get(i).getPos(2);
        }
        return  dDetails;                                                                                               //Returns a two dimension array containing driver details
    }

    public void sortBy1stPos(){                                                                                         //Method to sort drivers by first position
        ArrayList<Formula1Driver> posSort = new ArrayList<>(f1Driver);
        posSort.sort(new Comparator<>() {
            public int compare(Formula1Driver d1, Formula1Driver d2) {                                                  //predefined method used to compare drivers by position
                return Integer.compare(d2.getPos(0), d1.getPos(0));
            }
        });
        dTable.setModel(new DefaultTableModel(driverDetails(posSort),columns));                                         //Updates Table
    }

    public void randomRace(){                                                                                           //Method to Generate Random Race
        JFrame j = new JFrame("Add Date");
        String x = JOptionPane.showInputDialog(j,"Enter date of race (yyyy-mm-dd)");
        Date date;
        try{
            date = new SimpleDateFormat("yyyy-MM-dd").parse(x);                                                   //Date is asked from user
        } catch (ParseException ex){
            JFrame warning = new JFrame();
            JOptionPane.showMessageDialog(warning,"Invalid Input","Alert",JOptionPane.WARNING_MESSAGE);    //Date entered is not valid
            return;
        }
        ArrayList<Formula1Driver> r = new ArrayList<>(f1Driver);                                                        //Array created to jumble drivers in list
        Collections.shuffle(r);                                                                                         //Predefined method used to jumble drivers
        int i = 0;
        for(Formula1Driver f1 : r){
            for(Formula1Driver f : f1Driver){
                if(f.getdName().equals(f1.getdName())){
                    f.setNumOfRaces(f.getNumOfRaces() + 1 );
                    f.addPos(i);
                    f.totalPts();
                    sortDrivers();
                    i++;
                }
            }
        }
        race.add(new Race(date,r));                                                                                     //Random race is added to array
    }

    public void search(){                                                                                               //Method to Search Driver
        String input = text.getText();
        for(Formula1Driver f1 : f1Driver){
            if(f1.getdName().equalsIgnoreCase(input)){                                                                  //Checks for user input in array
                dLabel.setText(f1.getdName());
                Object[][] dDetails = new Object[race.size()][2];
                int i = 0;
                for(Race r : race){
                    for(Formula1Driver f : r.getRacer()){
                            if(f.getdName().equals(f1.getdName())){
                                dDetails[i][0] = r.getDate();
                                dDetails[i][1] = (r.getRacer().indexOf(f) + 1 );
                                i++;
                            }
                    }
                }
                text.setText("");
                JFrame driver = new JFrame("Driver Details");                                                       //Window for driver details
                DefaultTableModel driverModel = new DefaultTableModel(dDetails, searchDColumns);
                JTable detailsTable = new JTable(driverModel);
                driver.add(new JScrollPane(detailsTable));
                driver.setSize(300,300);
                driver.setVisible(true);
                return;
            }
        }
        dLabel.setText("Invalid input");                                                                                //Message when Input not available
        text.setText("");
    }

    public void viewRace(){                                                                                             //Method to view races taken place
        ArrayList<Race> dateSorted = new ArrayList<>(race);
        dateSorted.sort(new Comparator<>() {
            public int compare(Race r1, Race r2) {
                if (r1.getDate() == null || r2.getDate() == null) {
                    return 0;
                }
                return r1.getDate().compareTo(r2.getDate());
            }
        });
        Object[][] dDetails = new Object[dateSorted.size()][4];                                                         //Stores Race details
        int i =0;
        for(Race r : dateSorted){
            ArrayList<Formula1Driver> racers = r.getRacer();
                dDetails[i][0] = r.getDate();
                dDetails[i][1] = racers.get(0).getdName();
                dDetails[i][2] = racers.get(1).getdName();
                dDetails[i][3] = racers.get(2).getdName();
                i++;
        }
        dTable.setModel(new DefaultTableModel(dDetails,viewRColumns));                                                  //Updates Table
        }

    public void actionPerformed(ActionEvent e) {                                                                        //providing button click actions
        if(e.getSource() == rRace){
            randomRace();                                                                                               //randomRace method if random button clicked
        }else if(e.getSource() == sortFP){
            if(sortFP.isSelected()){
                sortBy1stPos();
                AscBut.setEnabled(false);
                DescBut.setEnabled(false);
            }else if (!sortFP.isSelected()){
                AscBut.setEnabled(true);
                DescBut.setEnabled(true);
                dTable.setModel(new DefaultTableModel(driverDetails(pSort),columns));
            }
        }else if(e.getSource() == viewRace){
            viewRace();                                                                                                 //viewRac method if random button clicked
        }else if(e.getSource() == search){
            search();                                                                                                   //search method if random button clicked
        }else if(e.getSource() == DescBut){
            dTable.setModel(new DefaultTableModel(driverDetails(pSort),columns));
        }else if(e.getSource() == AscBut){
            ArrayList<Formula1Driver> duplicate = new ArrayList<>(pSort);
            Collections.reverse(duplicate);
            dTable.setModel(new DefaultTableModel(driverDetails(duplicate),columns));
        }else if(e.getSource() == refreshPage){
            dTable.setModel(new DefaultTableModel(driverDetails(pSort),columns));
            sortFP.setSelected(false);
            AscBut.setEnabled(true);
            DescBut.setEnabled(true);
            DescBut.setSelected(true);
        }
    }
}
                                                                                                                        //https://www.javatpoint.com/java-swing
                                                                                                                        //https://www.youtube.com/watch?v=5o3fMLPY7qY


