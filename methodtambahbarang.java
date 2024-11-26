static void tambahBarang() {
        System.out.print("Masukkan Nama Barang: ");
        String namaBarang = scanner.nextLine();
        System.out.print("Masukkan Harga Barang: ");
        double hargaBarang = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Masukkan Stok Barang: ");
        int stokBarang = scanner.nextInt();
        scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter(basePath + "/barang.txt", true))) {
            writer.println(namaBarang + ";" + hargaBarang + ";" + stokBarang);
            System.out.println("Barang berhasil ditambahkan!");
        } catch (IOException e) {
            System.out.println("Gagal menambah barang: " + e.getMessage());
        }
    }
