public class main {

    public static void main(String[] args){

        Formula1ChampionshipManager f1CSManager = new Formula1ChampionshipManager();                                    //An object of Formula1ChampionshipManager has been created to call the menu method
        f1CSManager.sortDrivers();                                                                                      //sortDrivers method has been called to sort before running GUI
        f1CSManager.menu();                                                                                             //Menu method has been called
    }
}