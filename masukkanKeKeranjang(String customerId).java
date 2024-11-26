static void masukkanKeKeranjang(String customerId) {
        lihatBarang(); // Menampilkan daftar barang yang tersedia
        
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
