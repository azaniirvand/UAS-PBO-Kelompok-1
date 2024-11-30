import java.io.*;
import java.util.*;

public class Akun {

    private String id;
    private String password;
    String basePath = "Database";
    Scanner input = new Scanner(System.in);
    public void registrasi() {
        
        System.out.println("+--------------------+");
        System.out.println("|      Menu Utama    |");
        System.out.println("+--------------------+");
        System.out.println("| 1 | Login          |");
        System.out.println("| 2 | Registrasi     |");
        System.out.println("+--------------------+");
        System.out.print("Pilih menu: ");

        int jenisAkun = input.nextInt();
        
        if ((jenisAkun !=1 && jenisAkun !=2)) {
            System.out.println("+--------------------+");
            System.out.println("Pilihan tidak valid!");
            return;
        }
        else{
            input.nextLine();
            System.out.println("+---------------------------+");
            System.out.println("|        Login Akun         |");
            System.out.println("+---------------------------+");
            System.out.print("| Masukkan ID      : ");
            id = input.nextLine();
            System.out.print("| Masukkan Password: ");
            password = input.nextLine();
            System.out.println("+---------------------------+");


            String folderPath = basePath + "/" + id;
            File akunFolder = new File(folderPath);
            if (akunFolder.exists()) {
                System.out.println("+---------------------------+");
                System.out.println("Akun dengan ID ini sudah ada!");
                return;
            }
            akunFolder.mkdirs();//basePath + "/history_" + customerId + ".txt"
            try (PrintWriter writer = new PrintWriter(folderPath + "/"+ id + ".txt")) {
                writer.println(id);
                writer.println(password);
                writer.println(jenisAkun == 1 ? "Admin" : "Customer");
            } catch (IOException e) {
                System.out.println("+---------------------------+");
                System.out.println("Gagal membuat akun: " + e.getMessage());
            }
            System.out.println("+---------------------------+");
            System.out.println("Akun berhasil dibuat!");
        }
        
    }
    void login() {
        Admin admin = new Admin();
        Costumer costumer = new Costumer();
        System.out.println("+--------------------------+");
        System.out.println("|          Login           |");
        System.out.println("+--------------------------+");
        System.out.print("| Masukkan ID      : ");
        String id = input.nextLine();
        System.out.print("| Masukkan Password: ");
        String password = input.nextLine();
        System.out.println("+--------------------------+");
        

        String folderPath = basePath + "/" + id;
        File akunFolder = new File(folderPath);
        if (!akunFolder.exists()) {
            System.out.println("+--------------------------+");
            System.out.println("ID atau Password salah!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(folderPath + "/" + id + ".txt"))) {
            String storedId = reader.readLine();
            String storedPassword = reader.readLine();
            String role = reader.readLine();

            if (id.equals(storedId) && password.equals(storedPassword)) {
                if (role.equals("Admin")) {
                    admin.menuAdmin(id);
                } else if (role.equals("Customer")) {
                    costumer.menuCustomer(id);
                }
            } else {
                System.out.println("+--------------------------+");
                System.out.println("ID atau Password salah!");
            }
        } catch (IOException e) {
            System.out.println("+--------------------------+");
            System.out.println("Gagal memuat data akun: " + e.getMessage());
        }
    }     
}