package atm;

import nasabah.Nasabah;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FileATM {
    Scanner inputUser = new Scanner(System.in);
    BufferedReader inputUserBuffer = new BufferedReader(new InputStreamReader(System.in));
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDateTime now = LocalDateTime.now();

    // Arahkan Directory
    String directory = "D:\\IdeaProjects\\ATM\\src\\";

    int Login(){
        int noRekening;
        int pin;
        boolean ketemu = false;
        Nasabah N = new Nasabah();

        // Baca Inputan User
        System.out.println("------------- Login -------------");
        System.out.printf("%-15s : ", "No Rekening");
        noRekening = inputUser.nextInt();
        System.out.printf("%-15s : ", "PIN");
        pin = inputUser.nextInt();
        System.out.println("---------------------------------");

        ObjectInputStream in;

        // Arahkan ke Directory File
        File fNasabah = new File(directory+"nasabah\\nasabah.dat");

        // Baca File
        // Jika No Rekening dan PIN Berhasil DItemukan Sesuai Dengan Yang Dicari
        // Keluar dari Loop
        try {
            in = new ObjectInputStream(new FileInputStream(fNasabah));
            Object curR = in.readObject();

            try {
                while (ketemu == false){
                    N = (Nasabah) curR;
                    if ((noRekening == N.getNoRekening()) && (pin == N.getPin())){
                        ketemu = true;
                        System.out.println("Login Berhasil\n");
                    } else{
                        curR = in.readObject();
                    }
                }

            }
            catch (EOFException e){
            }catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        // Return No Rekening Jika Ketemu Bernilai True
        if (ketemu == true){
            return N.getNoRekening();
        } else{
            System.out.println("Login Gagal\n");
            return 0;
        }
    }

    int mainMenuATM(){
        System.out.println("---------- ATM Bank ABC ---------");
        System.out.println("1. Setor Tunai");
        System.out.println("2. Tarik Tunai");
        System.out.println("3. Tampilkan Saldo");
        System.out.println("4. Mutasi Rekening");
        System.out.println("0. Exit");
        System.out.print("Masukan Pilihan : ");
        int pilihan = inputUser.nextInt();
        System.out.println("---------------------------------\n");
        return pilihan;
    }

    public void setorTunai(int noRekening){
        Nasabah N = new Nasabah();
        int total;
        int jumlahSetoran = 0;
        boolean ketemu = false;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        // Deklarasikan Lokasi File
        File fNasabah = new File(directory+"nasabah\\nasabah.dat");
        File fNasabahTemporary = new File(directory+"nasabah\\nasabahTemporary.dat");

        // Baca File Nasabah
        // Cari Data Nasabah Yang Mempunyai No Rekening Yang Sama Dengan No Rekening Di Parameter
        // Jika Ketemu, Keluar Dari Pengulangan
        // Jika Belum Ketemu, Copy Datanya ke Temporary
        try {
            in = new ObjectInputStream(new FileInputStream(fNasabah));
            out = new ObjectOutputStream(new FileOutputStream(fNasabahTemporary));
            Object curR = in.readObject();
            try {
                while (ketemu == false){
                    N = (Nasabah) curR;
                    if (noRekening == N.getNoRekening()){
                        ketemu = true;
                    } else{
                        out.writeObject(N);
                        curR = in.readObject();
                    }
                }
            } catch (EOFException e){
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Baca Jumlah Setoran
            // Set Ulang Saldo Dari Nasabah Yang Tadi Sudah Ditemukan Sesuai Dengan No Rekening Parameter
            // Copy Ulang Sisa Data Nasabah ke Temporary
            if (ketemu == true){
                System.out.println("---------- Setor Tunai ----------");
                System.out.printf("%-24s : ","Masukan Jumlah Setoran");
                jumlahSetoran = inputUser.nextInt();
                total = N.getSaldo() + jumlahSetoran;
                N.setSaldo(total);
                out.writeObject(N);
                System.out.printf("%-24s : %d\n","Total Saldo Saat Ini", N.getSaldo());
                System.out.println("---------------------------------\n");

                try {
                    curR = in.readObject();
                    while (true){
                        N = (Nasabah) curR;
                        out.writeObject(N);
                        curR = in.readObject();
                    }
                } catch (EOFException e){
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            in.close();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Copy Ulang Semua Data Dari Temporary Telah Selesai Dimanipulasi ke File Nasabah
        try {
            in = new ObjectInputStream(new FileInputStream(fNasabahTemporary));
            Object curR = in.readObject();

            out = new ObjectOutputStream(new FileOutputStream(fNasabah));

            total = 0;

            try {
                while (true){
                    N = (Nasabah) curR;
                    out.writeObject(N);
                    curR = in.readObject();
                }
            } catch (EOFException e){
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            in.close();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Tulis Riwayat Mutasi
        String tanggal = dtf.format(now);
        Transaksi T = new Transaksi(noRekening, tanggal, "Setor Tunai", jumlahSetoran);
        tulisTransaksi(T);
    }

    public void tarikTunai(int noRekening){
        Nasabah N = new Nasabah();
        int total;
        int jumlahTarikan = 0;
        boolean ketemu = false;
        boolean berhasil = false;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        // Deklarasikan Lokasi File
        File fNasabah = new File(directory+"nasabah\\nasabah.dat");
        File fNasabahTemporary = new File(directory+"nasabah\\nasabahTemporary.dat");

        // Baca File Nasabah
        // Cari Data Nasabah Yang Mempunyai No Rekening Yang Sama Dengan No Rekening Di Parameter
        // Jika Ketemu, Keluar Dari Pengulangan
        // Jika Belum Ketemu, Copy Datanya ke Temporary
        try {
            in = new ObjectInputStream(new FileInputStream(fNasabah));
            out = new ObjectOutputStream(new FileOutputStream(fNasabahTemporary));
            Object curR = in.readObject();
            try {
                while (ketemu == false && true){
                    N = (Nasabah) curR;
                    if (noRekening == N.getNoRekening()){
                        ketemu = true;
                    } else{
                        out.writeObject(N);
                        curR = in.readObject();
                    }
                }
            } catch (EOFException e){
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Baca Jumlah Setoran
            // Set Ulang Saldo Dari Nasabah Yang Tadi Sudah Ditemukan Sesuai Dengan No Rekening Parameter
            // Copy Ulang Sisa Data Nasabah ke Temporary
            if (ketemu == true){
                System.out.println("---------- Tarik Tunai ----------");
                System.out.printf("%-24s : ","Masukan Jumlah Tarikan");
                jumlahTarikan = inputUser.nextInt();
                if (jumlahTarikan > N.getSaldo()){
                    System.out.println("Gagal ! Jumlah Tarikan Terlalu Besar");
                    out.writeObject(N);

                } else {
                    total = N.getSaldo() - jumlahTarikan;
                    N.setSaldo(total);
                    out.writeObject(N);
                    berhasil = true;
                    System.out.printf("%-24s : %d\n","Total Saldo Saat Ini", N.getSaldo());
                }
                System.out.println("---------------------------------\n");

                try {
                    curR = in.readObject();
                    while (true){
                        N = (Nasabah) curR;
                        out.writeObject(N);
                        curR = in.readObject();
                    }
                } catch (EOFException e){
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            in.close();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Copy Ulang Semua Data Dari Temporary Telah Selesai Dimanipulasi ke File Nasabah
        try {
            in = new ObjectInputStream(new FileInputStream(fNasabahTemporary));
            Object curR = in.readObject();

            out = new ObjectOutputStream(new FileOutputStream
                    (fNasabah));

            total = 0;

            try {
                while (true){
                    N = (Nasabah) curR;
                    out.writeObject(N);
                    curR = in.readObject();
                }
            } catch (EOFException e){
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            in.close();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Tulis Riwayat Mutasi
        if (berhasil == true){
            String tanggal = dtf.format(now);
            Transaksi T = new Transaksi(noRekening, tanggal, "Tarik Tunai", jumlahTarikan);
            tulisTransaksi(T);
        }
    }

    void tampilSaldo(int noRekening){
        ObjectInputStream in = null;
        Nasabah N = new Nasabah();

        // Deklarasikan Lokasi File
        File fNasabah = new File(directory+"nasabah\\nasabah.dat");

        // Baca File Transaksi
        try {
            in = new ObjectInputStream(new FileInputStream(fNasabah));
            try {
                Object curR = in.readObject();
                while (true) {
                    N = (Nasabah) curR;
                    if (N.getNoRekening() == noRekening){
                        System.out.println("----------- Cetak Saldo ---------");
                        System.out.printf("%-13s : %s\n", "No Rekening", N.getNoRekening());
                        System.out.printf("%-13s : %s\n", "Saldo", N.getSaldo());
                        System.out.println("---------------------------------\n");
                    }
                    curR = in.readObject();
                }

            } catch (EOFException | ClassNotFoundException e){
            }
        } catch (IOException e) {
            System.out.println("File Transaksi Belum Ada");
        }
    }

    void tulisTransaksi(Transaksi t) {
        Transaksi T = new Transaksi();
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        // Definisikan Lokasi File
        File fTransaksi = new File(directory+"atm\\Transaksi.dat");
        File fTransaksiTemporary = new File(directory+"atm\\transaksTemporary.dat");

        // Cek Apakah File Sudah Ada Atau Belum
        // Jika Belum Maka File Akan Dibuat
        if(!(fTransaksi.isFile() && fTransaksi.canRead())){
            try {
                out = new ObjectOutputStream(new FileOutputStream(fTransaksi));
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Copy Seluruh Isi File Transaksi Ke Temporary
        try {
            in = new ObjectInputStream(new FileInputStream(fTransaksi));
            out = new ObjectOutputStream(new FileOutputStream(fTransaksiTemporary));
            try {
                Object curR = in.readObject();
                while (true){
                    T = (Transaksi) curR;
                    out.writeObject(T);
                    curR = in.readObject();
                }
            } catch (EOFException | ClassNotFoundException e){
            }
            in.close();

        // Tambahkan Record Baru Ke File Temporary
            try {
                out.writeObject(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Copy Ulang Temporary Ke File Transaksi
        try {
            in = new ObjectInputStream(new FileInputStream(fTransaksiTemporary));
            out = new ObjectOutputStream(new FileOutputStream(fTransaksi));
            try {
                Object curR = in.readObject();
                while (true){
                    T = (Transaksi) curR;
                    out.writeObject(T);
                    curR = in.readObject();
                }
            } catch (EOFException e){
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void mutasiRekening(int noRekening){
        ObjectInputStream in = null;
        Transaksi T = new Transaksi();

        // Deklarasikan Lokasi File
        File fTransaksi = new File(directory+"atm\\Transaksi.dat");

        // Baca File Transaksi
        try {
            in = new ObjectInputStream(new FileInputStream(fTransaksi));
            try {
                Object curR = in.readObject();
                System.out.println("----------------- Mutasi Rekening -----------------");
                System.out.printf("%-15s %-25s %-15s\n", "Tanggal", "Jenis Transaksi", "Nominal");
                while (true) {
                    T = (Transaksi) curR;
                    if (T.getNoRekening() == noRekening){
                        System.out.printf("%-15s %-25s %-15d\n", T.getTanggalTransaksi(), T.getTipeTransaksi(), T.getNominalTransaksi());
                    }
                    curR = in.readObject();
                }

            } catch (EOFException | ClassNotFoundException e){
            }
            System.out.println("---------------------------------------------------\n");
        } catch (IOException e) {
            System.out.println("File Transaksi Belum Ada");
        }
    }

    public static void main(String[] args) {
        FileATM FA = new FileATM();

        // Batas Max Percobaan Login 3 x
        int i = 0;
        int currentNoRek = 0;
        while (i < 3 && currentNoRek == 0){
            currentNoRek = FA.Login();
            i++;
        }


        if (currentNoRek != 0) {
            int pilihan = FA.mainMenuATM();
            while (pilihan != 0) {
                switch (pilihan) {
                    case 1:
                        FA.setorTunai(currentNoRek);
                        break;
                    case 2:
                        FA.tarikTunai(currentNoRek);
                        break;
                    case 3:
                        FA.tampilSaldo(currentNoRek);
                        break;
                    case 4:
                        FA.mutasiRekening(currentNoRek);
                        break;
                    default:
                        System.out.println("Pilihan Anda Tidak Ada di Menu");
                        break;
                }
                pilihan = FA.mainMenuATM();
            }
        }
    }
}
