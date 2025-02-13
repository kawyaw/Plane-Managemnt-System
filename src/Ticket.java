import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
public class Ticket {

        private int row;
        private int seat;
        private double price;
        private Person person;

        // add constructor
        public Ticket (int row, int seat, double price, Person person){
            this.row = row;
            this.seat = seat;
            this.price = price;
            this.person = person;
        }
        // getters and setters
        public  int getRow(){

            return row;
        }
        public void setRow(int row){

            this.row = row;
        }
        public int getSeat(){

            return seat;
        }
        public void setSeat(int seat){

            this.seat = seat;
        }
        public double getPrice(){

            return price;
        }
        public void setPrice(double price){

            this.price = price;
        }
        public Person getPerson(){

            return person;
        }
        public void setPerson(Person person){

            this.person = person;
        }
        // print ticket informations
        public void printTicketInfo(){
            System.out.println("Ticket Informations");
            char rowLetter = (char) ('A' + row);
            System.out.println("Row: " + rowLetter);

            System.out.println("Seat: " + seat);
            System.out.println("Price: " + price);
            System.out.println("Person Information: ");
            person.printPersonInfo();
        }

        public void save(){
            try{
                char rowLetter = (char) ('A' + row);
                FileWriter file = new FileWriter(rowLetter + seat + ".txt");

                file.write("Row: " + rowLetter +"\n");
                file.write("Seat: " + seat + "\n");
                file.write("Price: " + price + "\n");
                file.write("Person Infornation:" + "\n");
                file.write("Name: " + person.getName() + "\n");
                file.write("Surname: " + person.getSurname() + "\n");
                file.write("Email: " + person.getEmail() + "\n");
                file.close();

            }
            catch (IOException e){
                System.out.println("Error saving ticket information to file.");
                e.printStackTrace();
            }
        }


}
