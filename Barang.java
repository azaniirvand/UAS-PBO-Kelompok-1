import java.io.*;
import java.util.*;

public class Barang {
    String namaBarang;
    double hargaBarang;
    int stokBarang;
    String basePath = "Database";
    Scanner scanner = new Scanner(System.in);
    public void tambahBarang() {
        System.out.println("+------------------------------------+");
System.out.println("|         Input Data Barang          |");
System.out.println("+------------------------------------+");
System.out.print("| Masukkan Nama Barang  : ");
String namaBarang = scanner.nextLine();
System.out.print("| Masukkan Harga Barang : ");
double hargaBarang = scanner.nextDouble();
scanner.nextLine(); // Membersihkan buffer
System.out.print("| Masukkan Stok Barang  : ");
int stokBarang = scanner.nextInt();
System.out.println("+------------------------------------+");
scanner.nextLine(); // Membersihkan buffer setelah input


        try (PrintWriter writer = new PrintWriter(new FileWriter(basePath + "/barang.txt", true))) {
            writer.println(namaBarang + ";" + hargaBarang + ";" + stokBarang);
            System.out.println("Barang berhasil ditambahkan!");
        } catch (IOException e) {
            System.out.println("Gagal menambah barang: " + e.getMessage());
        }
    }
    public void editBarang() {
        File file = new File(basePath + "/barang.txt");
        if (!file.exists()) {
            System.out.println("Belum ada data barang.");
            return;
        }

        System.out.print("Masukkan Nama Barang yang Ingin Diedit: ");
        String namaBarang = scanner.nextLine();
        List<String> barangList = new ArrayList<>();
        boolean ditemukan = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equalsIgnoreCase(namaBarang)) {
                    ditemukan = true;
                    System.out.print("Masukkan Harga Baru: ");
                    parts[1] = String.valueOf(scanner.nextDouble());
                    System.out.print("Masukkan Stok Baru: ");
                    parts[2] = String.valueOf(scanner.nextInt());
                    scanner.nextLine();
                    line = String.join(";", parts);
                }
                barangList.add(line);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data barang: " + e.getMessage());
        }

        if (!ditemukan) {
            System.out.println("Barang tidak ditemukan.");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String barang : barangList) {
                writer.println(barang);
            }
            System.out.println("Barang berhasil diedit!");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan perubahan: " + e.getMessage());
        }
    }
    public void lihatBarang() {
        File file = new File(basePath + "/barang.txt");
        if (!file.exists()) {
            System.out.println("Belum ada data barang.");
            return;
        }

        System.out.println("Daftar Barang:");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("========================================");
            System.out.printf("| %-12s | %-8s | %-6s |%n",   "Nama Barang", "Harga Satuan", "Jumlah");
            System.out.println("========================================");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                //System.out.printf("Nama: %s, Harga: %.2f, Stok: %s%n", parts[0], Double.parseDouble(parts[1]), parts[2]);
                System.out.printf("| %-12s | %-12s | %-6s |%n",
                                        parts[0], Double.parseDouble(parts[1]), parts[2]);
            }
            System.out.println("========================================");
        } catch (IOException e) {
            System.out.println("Gagal membaca data barang: " + e.getMessage());
        }
    }
}
