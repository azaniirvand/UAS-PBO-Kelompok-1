import java.util.Scanner;

public class Admin extends Akun {
    Scanner scanner = new Scanner(System.in);
    Barang barang = new Barang();
    Transaksi transaksi = new Transaksi();
    public void menuAdmin(String adminId) {
        while (true) {
            System.out.println("+---------------------------+");
            System.out.println("|        Menu Admin         |");
            System.out.println("+---------------------------+");
            System.out.println("| 1 | Tambah Barang         |");
            System.out.println("| 2 | Edit Barang           |");
            System.out.println("| 3 | Lihat Barang          |");
            System.out.println("| 4 | Terima Penjualan      |");
            System.out.println("| 5 | History Transaksi     |");
            System.out.println("| 6 | Logout                |");
            System.out.println("+---------------------------+");
            System.out.print("Pilih menu: ");
            
            int menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    barang.tambahBarang();
                    break;
                case 2:
                    barang.editBarang();
                    break;
                case 3:
                    barang.lihatBarang();
                    break;
                case 4:
                    transaksi.terimaPenjualan();
                    break;
                case 5:
                    transaksi.riwayatTransaksi();
                    break;
                case 6:
                    System.out.println("+---------------------------+");
                    System.out.println("Logout berhasil.");
                    return;
                default:
                    System.out.println("+---------------------------+");
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
