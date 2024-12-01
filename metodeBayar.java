import java.util.Scanner;
import java.util.UUID;

public class metodeBayar {
    public void pilihMetodePembayaran(String customerId) {
        System.out.println("Pilih Metode Pembayaran:");
        System.out.println("1. QRIS");
        System.out.println("2. Bank Transfer");
        System.out.println("3. COD (Cash on Delivery)");
    
        Scanner scanner = new Scanner(System.in);
        int pilihan = scanner.nextInt();
    
        switch (pilihan) {
            case 1:
                bayarQRIS(customerId);
                break;
            case 2:
                bayarBankTransfer(customerId);
                break;
            case 3:
                bayarCOD(customerId);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }
    public void bayarQRIS(String customerId) {
        // Membuat kode QR acak sebagai kode pembayaran
        String kodeQR = "QRIS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Tampilkan kode QR untuk pembayaran
        System.out.println("Metode Pembayaran: QRIS");
        System.out.println("Kode QR untuk Pembayaran: " + kodeQR);
        System.out.println("Silakan lakukan pembayaran dengan menggunakan QRIS dan konfirmasi setelah pembayaran.");
    }
    public void bayarBankTransfer(String customerId) {
        // Contoh nomor rekening dan bank
        String nomorRekening = "123-456-7890";
        String namaBank = "Bank ABC";
        
        // Tampilkan informasi bank untuk pembayaran
        System.out.println("Metode Pembayaran: Bank Transfer");
        System.out.println("Silakan transfer ke nomor rekening berikut:");
        System.out.println("Nama Bank: " + namaBank);
        System.out.println("Nomor Rekening: " + nomorRekening);
        System.out.println("Konfirmasi setelah transfer dilakukan.");
    }
    public void bayarCOD(String customerId) {
        // Menampilkan informasi untuk pembayaran COD
        System.out.println("Metode Pembayaran: COD (Cash on Delivery)");
        System.out.println("Anda dapat membayar langsung kepada kurir saat barang diterima.");
    }
}
