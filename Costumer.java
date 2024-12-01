import java.util.Scanner;

public class Costumer extends Akun {
    Barang barang = new Barang();
    Transaksi transaksi = new Transaksi();
    Scanner scanner = new Scanner(System.in);
    public void menuCustomer(String customerId) {
        while (true) {
            System.out.println("Menu Customer:");
            System.out.println("1. Lihat Barang");
            System.out.println("2. Masukkan Barang ke Keranjang");
            System.out.println("3. Checkout Barang");
            System.out.println("4. Lihat Status Pesanan");
            System.out.println("5. Lihat History Transaksi");
            System.out.println("6. Logout");
            System.out.print("Pilih menu: ");
            int menu = scanner.nextInt();
            scanner.nextLine();
    
            switch (menu) {
                case 1:
                    barang.lihatBarang();
                    break;
                case 2:
                    transaksi.masukkanKeKeranjang(customerId);
                    break;
                case 3:
                    transaksi.checkoutBarang(customerId);
                    break;
                case 4:
                    transaksi.lihatStatusPesanan(customerId);
                    break;
                case 5:
                    transaksi.lihatHistory(customerId);
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
