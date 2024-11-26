static void editBarang() {
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
