import java.io.*;
import java.util.Scanner;

public class User {

    private String fileName;
    private String userType;

    public User(String fileName, String userType) {
        this.fileName = fileName;
        this.userType = userType;
    }

    // Metode untuk mendaftarkan pengguna
    public void register(Scanner scanner) {
        System.out.println("\n=== Daftar " + userType + " Baru ===");
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        // Menyimpan username dan password ke file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(username + "," + password);
            writer.newLine();
            System.out.println(userType + " berhasil didaftarkan!");
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat menyimpan data: " + e.getMessage());
        }
    }

    // Metode untuk login pengguna
    public void login(Scanner scanner) {
        System.out.println("\n=== Login " + userType + " ===");
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        boolean isAuthenticated = false;

        // Membaca file dan memeriksa kredensial
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] credentials = line.split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    isAuthenticated = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File data pengguna tidak ditemukan.");
        }

        if (isAuthenticated) {
            System.out.println("Login berhasil! Selamat datang, " + userType + " " + username + "!");
        } else {
            System.out.println("Login gagal. Username atau password salah.");
        }
    }
}
