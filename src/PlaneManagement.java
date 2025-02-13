import java.util.Scanner;
import java.util.InputMismatchException;

public class PlaneManagement {

    // array of tickets to store all the tickets sold
    private static Ticket[] tickets = new Ticket[52];

    public static void main(String[] args) {
        System.out.println("Welcome to the Plane Management application");

        //2D array with 4 rows and 14 columns
        int[][] seatArray = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
        };



        Scanner input = new Scanner(System.in);
        int option;
        boolean quit = false;

        //User Menu
        while (!quit) {
            try {
                System.out.println("****************************");
                System.out.println("*         Menu Option      *");
                System.out.println("****************************");
                System.out.println("1) Buy a seat");
                System.out.println("2) Cancel a seat");
                System.out.println("3) Find first available seat");
                System.out.println("4) Show seating plan");
                System.out.println("5) Print tickets information and total sales");
                System.out.println("6) Search ticket");
                System.out.println("0) Quit");
                System.out.println("****************************");


                System.out.print("Please select an option: ");
                option = input.nextInt();
                switch (option) {
                    case 1:
                        buy_seat(seatArray);
                        break;
                    case 2:
                        cancel_seat(seatArray);
                        break;
                    case 3:
                        find_first_available(seatArray);
                        break;
                    case 4:
                        show_seating_plan(seatArray);
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    case 0:
                        quit = true;
                        break;
                    default:
                        System.out.println("Incorrect option");


                }
            }
            catch (InputMismatchException e) {
                System.out.println("Incorrect input. Please enter a valid option (0-6).");
                // closing the scanner object
                input.nextLine();
            }

        }

    }

    //method for buy seat
    private static void buy_seat(int[][] seatArray) {
        Scanner input = new Scanner(System.in);

        // Ask user to input personal details


        System.out.println("Enter your name: ");
        String name = input.nextLine();

        System.out.println("Enter your surname: ");
        String surname = input.nextLine();

        System.out.println("Enter your email address: " );
        String email = input.nextLine();

        Person person = new Person(name, surname, email);

        // Ask user to input a row letter and a seat number
        char rowLetter = 0;
        while(true) {
            try {


                System.out.print("Enter a row letter (A - D): ");
                String userInput = input.next().toUpperCase();

                if (userInput.length() != 1 || userInput.charAt(0) < 'A' || userInput.charAt(0) > 'D') {
                    throw new IllegalArgumentException("Incorrect input. Please enter a single uppercase letter between A and D.");
                }
                rowLetter = userInput.charAt(0);
                break;  // if input correct exit the loop
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        int seatNumber = 0;
        while (true){
            try{
                System.out.print("Enter a seat number (1 - 14): ");
                seatNumber = input.nextInt();

                if ( seatNumber < 1 || seatNumber > 14) {
                    throw new IllegalArgumentException("Incorrect input. Please enter a number between 1 and 14.");

                }
                break;
            }
            catch (InputMismatchException e) {
                System.out.println("Incorrect input. Please enter a valid number.");
                input.nextLine();
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }


        // row letter to array
        int row = rowLetter - 'A';

        // check if the row and seat is valid or not
        if (row < 0 || row >= seatArray.length || seatNumber < 1 || seatNumber > seatArray[row].length) {
            System.out.println("Invalid row or seat number");
            return;
        }

        // check if the seat available
        if (seatArray[row][seatNumber - 1] == 0) {
            // record the seat as sold
            seatArray[row][seatNumber - 1] = 1;
            // calculate price
            double price = calculatePrice( rowLetter, seatNumber);
            Ticket ticket = new Ticket( row, seatNumber, price, person);

            // Add ticket to array (just a call function)
            addTicket(ticket);
            ticket.save();
            System.out.println("The seat has booked successfully");
        }
        else{
            System.out.println("The seat is not available");
        }


    }
    private static void cancel_seat (int[][] seatArray) {
        Scanner input = new Scanner(System.in);

        // ask user to input row letter and a seat number
        System.out.println("Enter a row letter (A - D): ");
        char rowLetter = input.next().toUpperCase().charAt(0);
        System.out.print("Enter a seat number (1 - 14): ");
        int seatNumber = input.nextInt();

        // row letter to array
        int row = rowLetter - 'A';

        // check if the row and seat is valid or not
        if (row < 0 || row >= seatArray.length || seatNumber < 1 || seatNumber > seatArray[row].length) {
            System.out.println("Invalid row or seat number");
            return;
        }
        // check if the seat available
        if (seatArray[row][seatNumber - 1] == 0) {
            System.out.println("Seat is available(free)");
            return;
        }
        //record the seat as available
        for (int i = 0; i < tickets.length; i++){
            if (tickets[i] != null && tickets[i].getRow() == row && tickets[i].getSeat() == seatNumber) {
                seatArray[row][seatNumber - 1] = 0; // mark seat as available
                tickets[i] = null;

                //remove tickets from array
                System.out.println("Seat cancellation successful");
                return;


            }

        }
    }
    // Add tickets to ticket array
    private static void addTicket(Ticket ticket){
        for (int i = 0; i < tickets.length; i++){
            if (tickets[i] == null){
                tickets[i] = ticket;
                return;
            }
        }
    }
    private static double calculatePrice(char rowLetter, int seatNumber){
        if (rowLetter >= 'A' && rowLetter <= 'D') {
            if (seatNumber >= 1 && seatNumber <= 5) {
                return 200.0;
            } else if (seatNumber >= 6 && seatNumber <= 9) {
                return 150.0;
            } else if (seatNumber >= 10 && seatNumber <= 14) {
                return 180.0;
            }
        }
        // default seat price
        return 0.0;
    }
    private static void find_first_available(int[][] seatArray){
        // outer loop for rows
        for (int row = 0; row < seatArray.length; row++){
            // inner loop for seats
            for (int seat = 0; seat < seatArray[row].length; seat++){
                // check if the seat available
                if (seatArray[row][seat] == 0){
                    char rowLetter = (char) ('A' + row);
                    System.out.println("First available seat: Row "+ rowLetter + ", Seat" + (seat + 1));
                    return;
                }
            }

        }

    }
    private static void show_seating_plan(int[][] seatArray){
        for (int row = 0; row < seatArray.length; row++){
            // Add a space at the start of row B and C
            if (row > 0){
                System.out.println(" ");
            }
            for (int seat = 0; seat < seatArray[row].length; seat++){
                //If the seat available put 'O'
                if (seatArray[row][seat] == 0){
                    System.out.print("O");
                }
                //If the seat is not available put 'X'
                else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }
    private static void print_tickets_info() {
        double totalPrice = 0;

        System.out.println("Tickets sold during the session:");

        for (int i = 0; i < tickets.length; i++) {
            Ticket ticket = tickets[i];
            if (ticket != null) {
                System.out.println("Ticket Information:");
                ticket.printTicketInfo();
                System.out.println(); // Add a blank line for better readability
                totalPrice += ticket.getPrice();
            }
        }

        System.out.println("Total price of tickets : £" + totalPrice);
    }
    private static void search_ticket(){
        Scanner input = new Scanner(System.in);
        // Ask user to input row Letter and seat number
        System.out.println("Enter a row Letter (A - D): ");
        char rowLetter = input.next().toUpperCase().charAt(0);
        System.out.println("Enter a seat number (1 - 14): ");
        int seatNumber = input.nextInt();

        // search ticket in array
        boolean ticketFound = false;
        for (int i = 0; i < tickets.length; i++){
            Ticket ticket = tickets[i];

            if (ticket != null && ticket.getRow() == rowLetter  && ticket.getSeat() == seatNumber){
                ticketFound = true;
                System.out.println("Ticket Information: ");
                ticket.printTicketInfo();
                break;
            }
        }
        // if no ticket found
        if (!(ticketFound)){
            System.out.println("This seat is available");
        }

    }

}






