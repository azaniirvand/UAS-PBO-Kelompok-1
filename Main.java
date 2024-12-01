import java.io.*;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String basePath = "Database";
    public static void main(String[] args) {
        Akun akun = new Akun();
        File baseFolder = new File(basePath);
        if (!baseFolder.exists()) baseFolder.mkdirs();
        
        while (true) {
            System.out.println("+--------------------+");
            System.out.println("|      Menu Utama    |");
            System.out.println("+--------------------+");
            System.out.println("| 1 | Login          |");
            System.out.println("| 2 | Registrasi     |");
            System.out.println("| 3 | Exit           |");
            System.out.println("+--------------------+");
            System.out.print("Pilih menu: ");

            int menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    akun.login();
                    break;
                case 2:
                    akun.registrasi();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        
    }
    
}
