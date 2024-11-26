import java.io.*;
import java.util.*;

public class UASPBO {
    static Scanner scanner = new Scanner(System.in);
    static String basePath = "Database";

    public static void main(String[] args) {
        File baseFolder = new File(basePath);
        if (!baseFolder.exists()) baseFolder.mkdirs();

        while (true) {
            System.out.println("Menu Utama:");
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
            System.out.print("Pilih menu: ");
            int menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    login();
                    break;
                case 2:
                    registrasi();
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static void registrasi() {
        System.out.println("Registrasi Akun:");
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.print("Pilih jenis akun: ");
        int jenisAkun = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Masukkan ID: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();

        String folderPath = basePath + "/" + id;
        File akunFolder = new File(folderPath);
        if (akunFolder.exists()) {
            System.out.println("Akun dengan ID ini sudah ada!");
            return;
        }

        akunFolder.mkdirs();
        try (PrintWriter writer = new PrintWriter(folderPath + "/credentials.txt")) {
            writer.println(id);
            writer.println(password);
            writer.println(jenisAkun == 1 ? "Admin" : "Customer");
        } catch (IOException e) {
            System.out.println("Gagal membuat akun: " + e.getMessage());
        }
        System.out.println("Akun berhasil dibuat!");
    }

    static void login() {
        System.out.println("Login:");
        System.out.print("Masukkan ID: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();

        String folderPath = basePath + "/" + id;
        File akunFolder = new File(folderPath);
        if (!akunFolder.exists()) {
            System.out.println("ID atau Password salah!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(folderPath + "/credentials.txt"))) {
            String storedId = reader.readLine();
            String storedPassword = reader.readLine();
            String role = reader.readLine();

            if (id.equals(storedId) && password.equals(storedPassword)) {
                if (role.equals("Admin")) {
                    menuAdmin(id);
                } else if (role.equals("Customer")) {
                    menuCustomer(id);
                }
            } else {
                System.out.println("ID atau Password salah!");
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat data akun: " + e.getMessage());
        }
    }

    static void menuAdmin(String adminId) {
        while (true) {
            System.out.println("Menu Admin:");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Edit Barang");
            System.out.println("3. Lihat Barang");
            System.out.println("4. Terima Penjualan");
            System.out.println("5. History Transaksi");
            System.out.println("6. Logout");
            System.out.print("Pilih menu: ");
            int menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    System.out.println("Fitur Tambah Barang belum tersedia.");
                    break;
                case 2:
                    System.out.println("Fitur Edit Barang belum tersedia.");
                    break;
                case 3:
                    System.out.println("Fitur Lihat Barang belum tersedia.");
                    break;
                case 4:
                    System.out.println("Fitur Terima Penjualan belum tersedia.");
                    break;
                case 5:
                    System.out.println("Fitur History Transaksi belum tersedia.");
                    break;
                case 6:
                    System.out.println("Logout berhasil.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static void menuCustomer(String customerId) {
        while (true) {
            System.out.println("Menu Customer:");
            System.out.println("1. Lihat Barang");
            System.out.println("2. Masukkan Barang ke Keranjang");
            System.out.println("3. Checkout");
            System.out.println("4. Lihat Status Pesanan");
            System.out.println("5. Lihat History Transaksi");
            System.out.println("6. Logout");
            System.out.print("Pilih menu: ");
            int menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    System.out.println("Fitur Lihat Barang belum tersedia.");
                    break;
                case 2:
                    System.out.println("Fitur Masukkan Barang ke Keranjang belum tersedia.");
                    break;
                case 3:
                    System.out.println("Fitur Checkout belum tersedia.");
                    break;
                case 4:
                    System.out.println("Fitur Pilih Menu Lihat Status Pesanan belum tersedia.");
                    break;
                case 5:
                    System.out.println("Fitur History Transaksi belum tersedia.");
                    break;
                case 6:
                    System.out.println("Logout berhasil.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
