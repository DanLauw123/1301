import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;


public class GroupProject {
    private static Scanner console = new Scanner(System.in);
    private static boolean userSelected;
    private static String home = System.getProperty("user.home");
    private static String file = home + File.separator + "Desktop" + File.separator + "people.txt";
    private static String line = "";
    private static String delimit = ",";


    public static void main(String[] args) {
        welcome();
    }

    /**
     * This method asks the user(s) to input their name, ticket type, and fine
     * and then saves each one of the records into an arraylist
     *
     * @return
     */
    private static void askUser() {
        // Create the ArrayList for the entire group of people
        ArrayList<ArrayList<String>> personList = new ArrayList();

        // Create a boolean to tell us if the user puts in a Q or not. If the user types in Q, thn the variable is set to false
        boolean askAgain = true;

        // Create an iterator so we know where to store each person in the personList ArrayList
        int i = 0;
        do {
            // Create the ArrayList for the person being entered in. Its in the do becuase we need it to be reset each time the loop runs
            ArrayList<String> person = new ArrayList();

            // Ask the user to input the name, and retrieve it
            System.out.println("Please insert your name: Else, press 'Q' to quit");
            String name = console.next();
            // If the user types in Q, we need to save the ArrayList of people.
            if (name.equalsIgnoreCase("q")) {
                saveFile(personList);
                // WE dont want to ask again, so we set this to be false so the while loop fails
                askAgain = false;
            } else {
                System.out.println("Please insert the type of ticket");
                String ticketType = console.next();
                System.out.println("Please insert the fine amount: ");
                String fine = console.next();

                // Now we need to save the person to the list
                person.add(0, name);
                person.add(1, ticketType);
                person.add(2, fine);
                // Now we need to add the person to the master list of people
                personList.add(i, person);
                i++;
            }
        }
        while (askAgain);
        int select = openerAndSelect();
        makeSelection(select);


    }

    /**
     * Takes in an ArrayList of ArrayLists which are the people for the fines. Prints them to a file
     *
     * @param
     */
    private static void saveFile(ArrayList<ArrayList<String>> array) {
        File file = new File(home + File.separator + "Desktop" + File.separator + "people.txt");

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < array.size(); i++) {
                for (int j = 0; j < array.get(i).size(); j++) {
                    bw.write(array.get(i).get(j));
                    bw.write(" ");
                }
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {

        }
    }

    /**
     *
     * @return
     */
    private static int openerAndSelect() {
        System.out.println("Welcome, please select one of the following options");
        System.out.println("Opt 1: Insert your name, ticket type, and fine ");
        System.out.println("Opt 2: View entered list (Sorted person name)");
        System.out.println("Opt 3: Display file");
        System.out.println("Opt 4: Search person");
        System.out.println("Opt 5: View file");
        System.out.println("Opt 6: Make adjustment to ticket type");
        System.out.println("Opt 7: Delete file  \n");

        System.out.println("Please enter Opt 1-7 from above");

        int i = 0;
        try {
            Scanner console = new Scanner(System.in);
            i = console.nextInt();
            if (i < 1 || i > 7) {
                openerAndSelect();
            }

        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid response. ");
            openerAndSelect();
        }
        return i;
    }

    /**
     *
     * @param option
     */
    private static void makeSelection(int option) {
        switch (option) {
            case 0:
                openerAndSelect();
                break;
            case 1:
                askUser();
                break;
            case 2:
                sortedPersonName();
                break;
            case 3 :
                displayFile();
                break;
            case 4:
                searchPerson();
                break;
            case 5:
                break;
            case 6:
                replaceFunction();
                break;
            case 7:
                break;
        }
    }

    /**
     *
     */
    private static void displayFile(){
        Scanner in = null;
        try {
            in = new Scanner(new File(file));
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        while (in.hasNext()) { // iterates each line in the file
            String line = in.nextLine();
            System.out.println(line);


        }
        in.close();
    }

    /**
     *
     */
    private static void welcome() {
        System.out.println("Welcome to Central Intelligence. ");
        System.out.println();
        int select = openerAndSelect();
        makeSelection(select);
    }

    /**
     *
     */
    private static void searchPerson() {
        boolean match = false;
        System.out.println("Please search a person's name below");
        String searchName = console.nextLine();
        String home = System.getProperty("user.home");
        File file = new File(home + File.separator + "Desktop" + File.separator + "people.txt");

        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(file));
            while (((sCurrentLine = br.readLine()) != null)) {

                //use comma to separate between variables
                String[] person = sCurrentLine.split(" "); //locates which part of the string you want to match with
                if (person[0].equalsIgnoreCase(searchName)) {
                    match = true; //if the name matches, the boolean returns true.
                }


            }


        } catch (IOException e) { //method to catch an exception.
            e.printStackTrace();
        }
        if (match) {
            System.out.println("We found a match in our system.");
            System.out.println(searchName + " exist ");

        } else {
            System.out.println("Sorry, no such name is found in our inventory. ");
            openerAndSelect();
        }


    }

    /**
     *
     */
    private static void sortedPersonName() {

        BufferedReader reader = null;
        PrintWriter outputStream = null;
        String line = null;
        ArrayList<String> rows = new ArrayList<String>();
        File file = new File(home + File.separator + "Desktop" + File.separator + "people.txt");
        File file2 = new File(home + File.separator + "Desktop" + File.separator + "sortedPeople.txt");


        try {
            if (!file2.exists()){
                file2.createNewFile();
            }
            reader = new BufferedReader(new FileReader(file));
            outputStream = new PrintWriter(new FileWriter(file2));

            while ((line = reader.readLine()) != null) {
                rows.add(line);
            }
            Collections.sort(rows);
            String[] strArr = rows.toArray(new String[0]);
            for (String cur : strArr)
                outputStream.println(cur);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
        outputStream.close();
        System.out.println("Your File is successfully saved under 'sortedPeople.txt'. ");
        int choice = openerAndSelect();
        makeSelection(choice);
        System.out.println();
    }

    /**
     *
     */
    private static void replaceFunction() {
        System.out.println("Which type of ticket would you like to change?");
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("people.txt"));
            try {
                String line;
                while((line = br.readLine()) != null) {
                    System.out.println(line);
                }

            }catch (IOException e) {
                e.printStackTrace();

            }

        }catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        String newTicketType = console.nextLine();



    }


}




