public class Main {
    private Akun akun;
    private Driver driverAkun;
    public String idAdmin;
    public String pwAdmin;

    public void login() {
        Scanner scanner = new Scanner(System.in);  // Jangan tutup scanner di sini
        System.out.println("Masukkan jenis akun (Admin/Customer): ");
        String jenisAkun = scanner.nextLine();

        if (jenisAkun.equalsIgnoreCase("Admin")) {
            this.akun = new Admin();
            System.out.print("Masukkan ID: ");
            String idAdmin = scanner.nextLine();
            System.out.print("Masukkan PW: ");
            String pwAdmin = scanner.nextLine();
            if (idAdmin.equals("guaadmin") && pwAdmin.equals("4321")){
                this.driverAkun = new AdminDriver((Admin) akun);
                driverAkun.login(); 
            }else{
                System.out.println("ID atau PW anda Salah, Mohon masukin yang bener lah kocak");
            }
        
        } else if (jenisAkun.equalsIgnoreCase("Customer")) {
            this.akun = new Customer();
            this.driverAkun = new CustomerDriver((Customer) akun);
            driverAkun.login();
        } else {
            System.out.println("Jenis akun tidak valid.");
        }
        // scanner.close();  // Jangan menutup scanner di sini
    }

    public static void main(String[] args) {
        Main mainApp = new Main();
        mainApp.login();
    }
}
