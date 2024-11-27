static void menuPembayaran(String customerId) {
    System.out.println("Pilih Metode Pembayaran:");
    System.out.println("1. Transfer Bank");
    System.out.println("2. E-Wallet");
    System.out.println("3. Tunai");
    System.out.print("Pilih metode: ");
    int pilihan = scanner.nextInt();
    scanner.nextLine(); // Konsumsi newline

    String metode;
    switch (pilihan) {
        case 1:
            metode = "Transfer Bank";
            break;
        case 2:
            metode = "E-Wallet";
            break;
        case 3:
            metode = "Tunai";
            break;
        default:
            System.out.println("Pilihan tidak valid.");
            return;
    }

    System.out.println("Anda memilih metode pembayaran: " + metode);
    catatMetodePembayaran(customerId, metode);
}
