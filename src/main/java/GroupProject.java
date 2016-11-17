import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;


public class GroupProject {
    private static Scanner console = new Scanner(System.in);

    private static boolean userSelected;
    // home folder
    private static String home = System.getProperty("user.home");
    public static File file = new File(home + File.separator + "Desktop" + File.separator + "people.txt");
    public static File file2 = new File(home + File.separator + "Desktop" + File.separator + "sortedPeople.txt");
    private static String line = "";
    private static String delimit = ",";

    /**
     * @param args
     */
    public static void main(String[] args) {
        welcome();
    }

    /**
     *
     */
    private static void welcome() {
        System.out.println("Welcome to Central Intelligence. ");
        System.out.println();
        makeSelection(openerAndSelect());
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
            String name = console.nextLine();
            // If the user types in Q, we need to save the ArrayList of people.
            if (name.equalsIgnoreCase("q")) {
                saveFile(personList);
                // WE dont want to ask again, so we set this to be false so the while loop fails
                askAgain = false;
            } else {
                System.out.println("Please insert the type of ticket");
                String ticketType = console.nextLine();
                System.out.println("Please insert the fine amount: ");
                String fine = console.nextLine();

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
        makeSelection(openerAndSelect());
    }

    /**
     * Takes in an ArrayList of ArrayLists which are the people for the fines. Prints them to a file
     *
     * @param
     */
    private static void saveFile(ArrayList<ArrayList<String>> array) {
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

    private static int openerAndSelect() {
        System.out.println("Welcome, please select one of the following options");
        System.out.println("Opt 1: Insert your name, ticket type, and fine ");
        System.out.println("Opt 2: View entered list (Sorted person name)");
        System.out.println("Opt 3: Display file");
        System.out.println("Opt 4: Search person");
        System.out.println("Opt 5: Display people in database");
        System.out.println("Opt 6: Make adjustment to ticket type");
        System.out.println("Opt 7: Delete file  \n");

        System.out.println("Please enter Opt 1-7 from above");

        int i = 0;
        try {
            Scanner console = new Scanner(System.in);
            try {
                i = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (i < 1 || i > 7) {
                openerAndSelect();
            }

        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid response. ");
            openerAndSelect();
        }
        return i;
    }


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
            case 3:
                displayFile();
                break;
            case 4:
                searchPerson();
                break;
            case 5:
                displayPeople();
                break;
            case 6:
                replaceFunction();
                break;
            case 7:
                deleteFiles();
                break;
        }
    }

    private static void displayFile() {
        // use scanner to scan the file.
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        while (in.hasNext()) { // iterates each line in the file
            String line = in.nextLine();
            System.out.println(line);
        }
        in.close();

        int select = openerAndSelect();
        System.out.println(" ");
        makeSelection(select);
    }

    private static void searchPerson() {
        boolean match = false;
        System.out.println("Please search a person's name below");
        String searchName = console.nextLine();
        BufferedReader br = null;
        int count = 0;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(file));
            while (((sCurrentLine = br.readLine()) != null)) {

                //use comma to separate between variables
                String[] person = sCurrentLine.split(" "); //locates which part of the string you want to match with
                if (person[0].equalsIgnoreCase(searchName)) {
                    match = true; //if the name matches, the boolean returns true.
                } else {
                    count++;
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
        }
        makeSelection(openerAndSelect());
    }


    private static void sortedPersonName() {

        BufferedReader reader = null;
        PrintWriter outputStream = null;
        String line = null;
        ArrayList<String> rows = new ArrayList<String>();

        try {
            if (!file2.exists()) {
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
        makeSelection(openerAndSelect());
        System.out.println();
    }

    // this method is to ask user to replace their ticket type.
    private static void replaceFunction() {
        ArrayList<ArrayList<String>> personList = new ArrayList();

        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        while (in.hasNext()) { // iterates each line in the file
            ArrayList<String> person = new ArrayList();
            String line = in.nextLine();
            String[] newLine = line.split(" ");
            person.clear();
            person.add(0, newLine[0]);
            person.add(1, newLine[1]);
            person.add(2, newLine[2]);

            personList.add(person);

            //

        }
        in.close();
        int i = 0;

        //show them the index (which is index 1 for ticket type).  (create the for loop)
        for (ArrayList<String> s: personList) {
            System.out.println("Name: " + s.get(0) + " / Ticket Type: " + s.get(1));
        }

        // sout (which ticket type you want to replace?)
        // System.out.println("From the list above, which ticket type would you like to change?");
        // String index1 = console.nextLine();
        System.out.println("Choose who's ticket you want to change");
        String index0 = console.nextLine();

        // ask user to change ticketType
        System.out.println("Please enter your new ticket type below");
        //ask user to replace it with the new ticket type
        //make a brand new variable "newTicketType"
        String newTicketType = console.nextLine();
        /*if (index1.equalsIgnoreCase(newTicketType)) {
            System.out.println("You entered the same type. Please try again.");
            //return to the opening to allow the user to re-insert.
            makeSelection(openerAndSelect());
        } else {
            // write it back to people.txt with the new type of ticket.
            for (ArrayList<String> s: personList) {
                if(s.get(0).equals(index0)){
                    s.set(1, newTicketType);
                }
            }
        }*/
        for (ArrayList<String> s: personList) {
            if(s.get(0).equals(index0)){
                s.set(1, newTicketType);
            }
        }

        //come out of the else and save the file under the saveFile method.
        saveFile(personList);
        //bring back the option for the user to make selection.
        makeSelection(openerAndSelect());
    }


    private static void displayPeople() {
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        while (in.hasNext()) { // iterates each line in the file
            String line = in.nextLine();
            String[] person = line.split(" ");
            System.out.println(person[0]);
        }
        in.close();

        int select = openerAndSelect();
        System.out.println(" ");
        makeSelection(select);
    }


    /**
     * Deletes the two files created as part of this application
     */
    private static void deleteFiles() {
        if(file.exists()) {
            file.delete();
        }
        if(file2.exists()){
            file2.delete();
        }
    }

    }






