import java.io.*;
import java.util.*;

public class Transaksi {
    Barang barang = new Barang();
    Scanner scanner = new Scanner(System.in);
    String basePath = "Database";
    public void masukkanKeKeranjang(String customerId) {
        barang.lihatBarang(); // Menampilkan daftar barang yang tersedia
        
        System.out.print("Masukkan nama barang yang ingin ditambahkan ke keranjang: ");
        String namaBarang = scanner.nextLine();
        System.out.print("Masukkan jumlah barang yang ingin dibeli: ");
        int jumlahBarang = scanner.nextInt();
        scanner.nextLine();  // Consume newline after nextInt()
    
        // Membaca file barang untuk memeriksa apakah barang tersedia
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(basePath + "/barang.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equalsIgnoreCase(namaBarang)) {
                    double hargaSatuan = Double.parseDouble(parts[1]);  // Menggunakan Double.parseDouble
                    int stokBarang = Integer.parseInt(parts[2]);
    
                    if (jumlahBarang <= stokBarang) {  // Memeriksa apakah stok cukup
                        int totalHarga = (int)(hargaSatuan * jumlahBarang);  // Menghitung total harga
    
                        // Menambahkan barang ke keranjang customer
                        try (PrintWriter writer = new PrintWriter(new FileWriter(basePath + "/keranjang_" + customerId + ".txt", true))) {
                            writer.println(namaBarang + ";" + hargaSatuan + ";" + jumlahBarang + ";" + totalHarga);
                        }
                        
                        // Mengurangi stok barang
                        stokBarang -= jumlahBarang;
                        parts[2] = String.valueOf(stokBarang); // Update stok barang
    
                        // Menyimpan perubahan stok barang
                        List<String> allBarang = new ArrayList<>();
                        try (BufferedReader barangReader = new BufferedReader(new FileReader(basePath + "/barang.txt"))) {
                            String barangLine;
                            while ((barangLine = barangReader.readLine()) != null) {
                                allBarang.add(barangLine);
                            }
                        }
    
                        try (PrintWriter writer = new PrintWriter(new FileWriter(basePath + "/barang.txt"))) {
                            for (String barang : allBarang) {
                                String[] itemParts = barang.split(";");
                                if (itemParts[0].equalsIgnoreCase(namaBarang)) {
                                    writer.println(String.join(";", parts));
                                } else {
                                    writer.println(barang);
                                }
                            }
                        }
    
                        System.out.println("Barang berhasil ditambahkan ke keranjang.");
                        System.out.println("Nama Barang: " + namaBarang);
                        System.out.println("Harga Satuan: " + hargaSatuan);
                        System.out.println("Jumlah: " + jumlahBarang);
                        System.out.println("Total Harga: " + totalHarga);
                        found = true;
                    } else {
                        System.out.println("Stok tidak mencukupi untuk jumlah yang diminta.");
                        found = true;
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data barang: " + e.getMessage());
        }
    
        if (!found) {
            System.out.println("Barang tidak ditemukan.");
        }
    }
    public void checkoutBarang(String customerId) {
        String keranjangFile = basePath + "/keranjang_" + customerId + ".txt";
        File fileKeranjang = new File(keranjangFile);
    
        if (!fileKeranjang.exists() || fileKeranjang.length() == 0) {
            System.out.println("Keranjang kosong.");
            return;
        }
    
        System.out.println("Barang dalam keranjang Anda:");
        int totalHargaSemua = 0;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(keranjangFile))) {
            String line;
            /*while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                System.out.println("Nama: " + parts[0] + ", Harga Satuan: " + parts[1] +
                                   ", Jumlah: " + parts[2] + ", Total: " + parts[3]);
                totalHargaSemua += Integer.parseInt(parts[3]);
            } */
            System.out.printf("%-15s %-15s %-10s %-10s%n", "Nama", "Harga Satuan", "Jumlah", "Total");
            System.out.printf("%-15s %-15s %-10s %-10s%n", "---------------", "---------------", "--------", "--------");
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                System.out.printf("%-15s %-15s %-10s %-10s%n", parts[0], parts[1], parts[2], parts[3]);
                totalHargaSemua += Integer.parseInt(parts[3]);
            }                       
        } catch (IOException e) {
            System.out.println("Gagal membaca keranjang: " + e.getMessage());
            return;
        }
    
        System.out.println("Total harga semua barang: " + totalHargaSemua);
        System.out.print("Lakukan checkout? (y/n): ");
        String confirm = scanner.nextLine();
    
        if (confirm.equalsIgnoreCase("y")) {
            // Langsung meminta pemilihan metode pembayaran setelah checkout
            pilihMetodePembayaran(customerId);
    
            // Simpan transaksi dengan status pending
            try (PrintWriter writer = new PrintWriter(new FileWriter(basePath + "/transaksi.txt", true))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(keranjangFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.println(customerId + ";" + line + ";Pending");
                    }
                }
            } catch (IOException e) {
                System.out.println("Gagal mencatat transaksi: " + e.getMessage());
                return;
            }
    
            if (fileKeranjang.delete()) {
                System.out.println("Checkout berhasil. Transaksi menunggu persetujuan admin.");
            } else {
                System.out.println("Gagal mengosongkan keranjang.");
            }
        } else {
            System.out.println("Checkout dibatalkan.");
        }
    }
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
    
    public void lihatStatusPesanan(String customerId) {
        File transaksiFile = new File(basePath + "/transaksi.txt");
    
        if (!transaksiFile.exists() || transaksiFile.length() == 0) {
            System.out.println("Tidak ada transaksi untuk dilihat.");
            return;
        }
    
        boolean adaPesanan = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(transaksiFile))) {
            String line;

            System.out.printf("%-15s %-15s %-10s %-10s %-10s%n", "Nama", "Harga Satuan", "Jumlah", "Total", "Status");
            System.out.printf("%-15s %-15s %-10s %-10s %-10s%n", "---------------", "---------------", "--------", "--------", "--------");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(customerId)) {
                    adaPesanan = true;
                    String status = parts[parts.length - 1].equals("Pending") ? "Pending" : "Diterima";
                    System.out.printf("%-15s %-15s %-10s %-10s %-10s%n", parts[1], parts[2], parts[3], parts[4], status);
                }
            }
           
        } catch (IOException e) {
            System.out.println("Gagal membaca status pesanan: " + e.getMessage());
            return;
        }
    
        if (!adaPesanan) {
            System.out.println("Tidak ada pesanan dengan ID pelanggan tersebut.");
        }
    }
    
    public void lihatHistory(String customerId) {
        File fileHistory = new File(basePath + "/transaksi.txt");
    
        if (!fileHistory.exists() || fileHistory.length() == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }
    
        System.out.println("History Transaksi Anda:");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileHistory))) {
            String line;
            System.out.printf("%-15s %-15s %-10s %-10s%n", "Nama", "Harga Satuan", "Jumlah", "Total");
            System.out.printf("%-15s %-15s %-10s %-10s%n", "---------------", "---------------", "--------", "--------");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(customerId) && parts[5].equals("Approved")) {
                    System.out.printf("%-15s %-15s %-10s %-10s%n", parts[1], parts[2], parts[3], parts[4]);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca history transaksi: " + e.getMessage());
        }
    }
    public void riwayatTransaksi() {
        File fileHistory = new File(basePath + "/transaksi.txt");
    
        if (!fileHistory.exists() || fileHistory.length() == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }
    
        System.out.println("History Transaksi:");
        System.out.println("================================================================================");
        System.out.printf("| %-4s | %-10s | %-15s | %-12s | %-8s | %-12s |%n", "No.", "User", "Nama Barang", "Harga Satuan", "Jumlah", "Total");
        System.out.println("================================================================================");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileHistory))) {
            String line;
            int nomor = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[5].equals("Approved")) {
                    System.out.printf("| %-4d | %-10s | %-15s | %-12s | %-8s | %-12s |%n",
                                      nomor, parts[0], parts[1], parts[2], parts[3], parts[4]);
                    nomor++;
                }else if(!parts[5].equals("Approved")){
                    System.out.println("Transaksi anda belum di terima Admin!");
                }
            }
            System.out.println("================================================================================");
        } catch (IOException e) {
            System.out.println("Gagal membaca history transaksi: " + e.getMessage());
        }
    }
    public void terimaPenjualan() {
        File transaksiFile = new File(basePath + "/transaksi.txt");
        if (!transaksiFile.exists() || transaksiFile.length() == 0) {
            System.out.println("Belum ada transaksi yang perlu disetujui.");
            return;
        }
    
        List<String> transaksiList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(transaksiFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transaksiList.add(line);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data transaksi: " + e.getMessage());
            return;
        }
    
        System.out.println("Daftar Transaksi Pending:");
        for (int i = 0; i < transaksiList.size(); i++) {
            String[] parts = transaksiList.get(i).split(";");
            if (parts[parts.length - 1].equals("Pending")) {
                System.out.println((i + 1) + ". " + Arrays.toString(parts));
            }
        }
    
        System.out.print("Pilih nomor transaksi untuk disetujui (0 untuk batal): ");
        int pilihan = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        if (pilihan <= 0 || pilihan > transaksiList.size()) {
            System.out.println("Tidak ada transaksi yang dipilih.");
            return;
        }
    
        String transaksiDipilih = transaksiList.get(pilihan - 1);
        String[] parts = transaksiDipilih.split(";");
        if (parts[parts.length - 1].equals("Pending")) {
            parts[parts.length - 1] = "Approved";
            transaksiList.set(pilihan - 1, String.join(";", parts));
            System.out.println("Transaksi disetujui.");
        } else {
            System.out.println("Transaksi sudah disetujui sebelumnya.");
        }
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(transaksiFile))) {
            for (String transaksi : transaksiList) {
                writer.println(transaksi);
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan perubahan: " + e.getMessage());
        }
    }
}
