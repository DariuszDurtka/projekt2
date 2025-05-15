import java.io.IOException;
import java.util.Scanner;

class WrongStudentName extends Exception { }

// Definicja nowego wyjątku sprawdzającego wiek studenta.
class WrongAge extends Exception { }

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

    // W metodzie exercise1 dodajemy walidację wieku.
    public static void exercise1() throws IOException, WrongStudentName, WrongAge {
        String name = ReadName();
        System.out.println("Podaj wiek: ");
        int age = scan.nextInt();
        // Sprawdzenie poprawności wieku: 1-99
        if(age < 1 || age > 99)
            throw new WrongAge();
        scan.nextLine(); // czyszczenie bufora
        System.out.println("Podaj datę urodzenia: ");
        String date = scan.nextLine();
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
