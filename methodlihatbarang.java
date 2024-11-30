static void lihatBarang() {
        File file = new File(basePath + "/barang.txt");
        if (!file.exists()) {
            System.out.println("Belum ada data barang.");
            return;
        }

        System.out.println("Daftar Barang:");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                System.out.printf("Nama: %s, Harga: %.2f, Stok: %s%n", parts[0], Double.parseDouble(parts[1]), parts[2]);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data barang: " + e.getMessage());
        }
    }
