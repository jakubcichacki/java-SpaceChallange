import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Simulation {

    private int numbersOfRocketsToSend = 0;
    private int budgetU1 = 0;
    private int budgetU2 = 0;

    ArrayList <Item> loadItems(String nameOfFile) {
        ArrayList<Item> listOfItemsFromFile = new ArrayList<>();
        File file = new File(nameOfFile);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File error");
            return null;
        }

        Pattern pattern = Pattern.compile("(.+)=(.+)");

        while (scanner.hasNextLine()) {
            String lineFromFile = scanner.nextLine();
            Matcher matcher = pattern.matcher(lineFromFile);

            if (matcher.matches()) {
                Item item = new Item();
                item.setName(matcher.group(1));
                item.setWeight(Integer.parseInt(matcher.group(2)));
                listOfItemsFromFile.add(item);
            }

        }
        return listOfItemsFromFile;
    }

    ArrayList<Rocket> loadU1 (ArrayList<Item> items) {
        Rocket rocketU1 = new U1();
        ArrayList<Rocket> listOfU1s = new ArrayList<>();
        listOfU1s.add(rocketU1);

        for (Item item : items) {
            if (rocketU1.canCarry(item)) {
                rocketU1.carry(item);
            }
            else {
                rocketU1 = new U1();
                listOfU1s.add(rocketU1);
            }
        }
        return listOfU1s;
    }

    ArrayList<Rocket> loadU2 (ArrayList<Item> items) {
        Rocket rocketU2 = new U2();
        ArrayList<Rocket> listOfU2s = new ArrayList<>();
        listOfU2s.add(rocketU2);

        for (Item item : items) {
            if (rocketU2.canCarry(item)) {
                rocketU2.carry(item);
            }
            else {
                rocketU2 = new U1();
                listOfU2s.add(rocketU2);
            }
        }
        return listOfU2s;
    }

    int runSimulation(ArrayList<Rocket> rocketArrayList) {
        int budget = 0;

        for (Rocket rocket : rocketArrayList) {
            do {
                rocket.launch();
                numbersOfRocketsToSend++;
            } while (!rocket.launch());
        }
        for (Rocket rocket : rocketArrayList) {
            do {
                rocket.land();
                numbersOfRocketsToSend++;
            } while (!rocket.land());
        }
        return budget = Rocket.costOfRocket * numbersOfRocketsToSend;
    }


}
