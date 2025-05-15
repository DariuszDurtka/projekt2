import java.io.IOException;
import java.util.InputMismatchException;
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
        while (true) {
            int option = menu();
            if (option == -1) {
                // W przypadku błędnego formatu w menu (np. litera zamiast cyfry)
                continue;
            }
            try {
                switch (option) {
                    case 1:
                        exercise1();
                        break;
                    case 2:
                        exercise2();
                        break;
                    case 3:
                        exercise3();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Niepoprawna opcja menu!");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Błąd wejścia/wyjścia: " + e.getMessage());
            } catch (WrongStudentName e) {
                System.out.println("Błędne imię studenta!");
            } catch (WrongAge e) {
                System.out.println("Błędny wiek! Poprawny wiek to liczba z przedziału 1-99.");
            } catch (WrongDateOfBirth e) {
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
        try {
            return scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Błędny format! Proszę podać cyfrę.");
            scan.nextLine(); // czyszczenie bufora, aby usunąć niepoprawny token
            return -1;
        }
    }

    public static String ReadName() throws WrongStudentName {
        scan.nextLine(); // czyszczenie bufora
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        if (name.contains(" "))
            throw new WrongStudentName();
        return name;
    }

    // Walidacja wieku (1-99) oraz formatu daty (DD-MM-YYYY)
    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth {
        String name = ReadName();
        System.out.println("Podaj wiek: ");
        int age = scan.nextInt();
        if (age < 1 || age > 99)
            throw new WrongAge();
        scan.nextLine(); // czyszczenie bufora
        System.out.println("Podaj datę urodzenia (DD-MM-YYYY): ");
        String date = scan.nextLine();
        if (!date.matches("^\\d{2}-\\d{2}-\\d{4}$"))
            throw new WrongDateOfBirth();
        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for (Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine(); // czyszczenie bufora
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        Student wanted = (new Service()).findStudentByName(name);
        if (wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}
