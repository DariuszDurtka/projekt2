import java.io.IOException;
import java.util.Scanner;

// Wyjątek sprawdzający poprawność imienia – nie może zawierać spacji.
class WrongStudentName extends Exception { }

// Wyjątek sprawdzający właściwy zakres wieku (1-99).
class WrongAge extends Exception { }

// Wyjątek sprawdzający format daty urodzenia (DD-MM-YYYY – 2-2-4 cyfry).
class WrongDateOfBirth extends Exception { }

class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while(true) {
            try {
                int ex = menu();
                switch(ex) {
                    case 1: 
                        exercise1();
                        break;
                    case 2: 
                        exercise2();
                        break;
                    case 3: 
                        exercise3();
                        break;
                    default: 
                        return;
                }
            } catch(IOException e) {
                System.out.println("Błąd wejścia/wyjścia: " + e.getMessage());
            } catch(WrongStudentName e) {
                System.out.println("Błędne imię studenta!");
            } catch(WrongAge e) {
                System.out.println("Błędny wiek! Poprawny wiek to liczba z przedziału 1-99.");
            } catch(WrongDateOfBirth e) {
                System.out.println("Błędna data urodzenia! Poprawny format: DD-MM-YYYY.");
            }
        }
    }

    public static int menu() {
        System.out.println("Wciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");
        return scan.nextInt();
    }

    public static String ReadName() throws WrongStudentName {
        scan.nextLine(); // czyszczenie bufora
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        if(name.contains(" "))
            throw new WrongStudentName();
        return name;
    }

    // W metodzie exercise1 dodajemy walidację wieku oraz formatu daty urodzenia.
    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth {
        String name = ReadName();
        System.out.println("Podaj wiek: ");
        int age = scan.nextInt();
        // Walidacja wieku – tylko przedział 1-99 jest poprawny.
        if(age < 1 || age > 99)
            throw new WrongAge();
        scan.nextLine(); // czyszczenie bufora
        System.out.println("Podaj datę urodzenia (DD-MM-YYYY): ");
        String date = scan.nextLine();
        // Walidacja formatu daty: musi być dokładnie 2 cyfry, myślnik, 2 cyfry, myślnik, 4 cyfry.
        if(!date.matches("^\\d{2}-\\d{2}-\\d{4}$"))
            throw new WrongDateOfBirth();
        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for(Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine(); // czyszczenie bufora
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        Student wanted = (new Service()).findStudentByName(name);
        if(wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}
