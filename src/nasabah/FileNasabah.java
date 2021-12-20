package nasabah;

import java.util.Scanner;
import java.io.*;

public class FileNasabah {
    Scanner inputUser = new Scanner(System.in);
    BufferedReader inputUserBuffer = new BufferedReader(new InputStreamReader(System.in));

    // Arahkan Directory
    String directory = "D:\\IdeaProjects\\ATM\\src\\";

    public void TambahNasabah(){
        Nasabah N = new Nasabah();

        int noRekening = 0;
        int pin = 0;
        String nama = "";
        String jenisKelamin = "";
        String tanggalLahir = "";
        String pekerjaan = "";
        String alamat = "";
        int saldo = 0;

        int hitung = 0;

        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        // Definisikan Lokasi File
        File fNasabah = new File(directory+"nasabah\\nasabah.dat");
        File fNasabahTemporary = new File(directory+"nasabah\\nasabahTemporary.dat");

        // Cek Apakah File Sudah Ada Atau Belum
        // Jika Belum Maka File Akan Dibuat
        if(!(fNasabah.isFile() && fNasabah.canRead())){
            try {
                out = new ObjectOutputStream(new FileOutputStream(fNasabah));
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Copy Isi Dari File Nasabah ke Temporary
        try {
            in = new ObjectInputStream(new FileInputStream(fNasabah));
            out = new ObjectOutputStream(new FileOutputStream(fNasabahTemporary));

            try {
                Object curR = in.readObject();
                while (true){
                    N = (Nasabah) curR;
                    out.writeObject(N);
                    curR = in.readObject();
                }
            } catch (EOFException e){
            } catch (IOException e) {
                e.printStackTrace();
            }

            in.close();

            // Baca Input User Berapa Nasabah Yang Ingin Ditambah
            System.out.println("---------- Tambah Nasabah ----------");
            System.out.print("Jumlah Nasabah Yang Akan Ditambah : ");
            int jumlahNasabah = inputUser.nextInt();


            // Baca Input User Untuk Data Nasabah Baru Yang Akan Ditambah
            // Setelah Dibaca, Data Baru Akan Ditulis Juga Ke Temporary
            try{
                while (hitung < jumlahNasabah){
                    System.out.print("No Rekening : ");
                    noRekening = inputUser.nextInt();
                    System.out.print("PIN : ");
                    pin = inputUser.nextInt();
                    try {
                        System.out.print("Nama : ");
                        nama = inputUserBuffer.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        System.out.print("Jenis Kelamin : ");
                        jenisKelamin = inputUserBuffer.readLine();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        System.out.print("Tanggal Lahir : ");
                        tanggalLahir = inputUserBuffer.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        System.out.print("Pekerjaan : ");
                        pekerjaan = inputUserBuffer.readLine();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                        System.out.print("Saldo : ");
                        saldo = inputUser.nextInt();
                    try {
                        System.out.print("Alamat : ");
                        alamat = inputUserBuffer.readLine();
                        N = new Nasabah(noRekening, pin, nama, jenisKelamin, tanggalLahir, pekerjaan, alamat, saldo);
                        out.writeObject(N);
                        hitung++;
                        System.out.println("");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                System.out.println("------------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }

            out.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        //Copy Ulang Dari Temporary Ke Nasabah
        try {
            in = new ObjectInputStream(new FileInputStream(fNasabahTemporary));
            Object curR = in.readObject();

            out = new ObjectOutputStream(new FileOutputStream(fNasabah));

            try {
                while (true){
                    N = (Nasabah) curR;
                    out.writeObject(N);
                    curR = in.readObject();
                }
            } catch (EOFException e){
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            in.close();
            out.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void HapusNasabah(){
        Nasabah N = new Nasabah();
        boolean ketemu = false;
        int noRekening = 0;

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        // Definisikan Lokasi File
        File fNasabah = new File(directory+"nasabah\\nasabah.dat");
        File fNasabahTemporary = new File(directory+"nasabah\\nasabahTemporary.dat");

        try {
            in = new ObjectInputStream(new FileInputStream(fNasabah));
            Object curR = in.readObject();

            out = new ObjectOutputStream(new FileOutputStream(fNasabahTemporary));

            try {
                System.out.println("---------- Hapus Nasabah ----------");
                System.out.print("Masukan No Rekening Yang Akan Dihapus : ");
                noRekening = inputUser.nextInt();
                while (true){
                    N = (Nasabah) curR;
                    if (N.getNoRekening() == noRekening){
                        System.out.println("Data Berhasil Dihapus");
                        ketemu = true;
                    } else{
                        out.writeObject(N);
                    }
                    curR = in.readObject();
                }

            } catch (EOFException e){
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (ketemu == false){
                System.out.println("Data Nasabah Tidak Ditemukan");
                System.out.println("Tidak Ada Data Yang Dihapus");
            }
            System.out.println("-----------------------------------");


            in.close();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            in = new ObjectInputStream(new FileInputStream(fNasabahTemporary));
            Object curR = in.readObject();

            out = new ObjectOutputStream(new FileOutputStream(fNasabah));


            try {
                while (true){
                    N = (Nasabah) curR;
                    out.writeObject(N);
                    curR = in.readObject();
                }
            } catch (EOFException e){
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            in.close();
            out.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void UpdateNasabah(){
        Nasabah N = new Nasabah();

        boolean ketemu = false;
        int noRekening = 0;
        int pin = 0;
        String nama = "";
        String jenisKelamin = "";
        String tanggalLahir = "";
        String pekerjaan = "";
        String alamat = "";
        int saldo = 0;

        int total = 0;

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        // Definisikan Lokasi File
        File fNasabah = new File(directory+"nasabah\\nasabah.dat");
        File fNasabahTemporary = new File(directory+"nasabah\\nasabahTemporary.dat");

        System.out.println("------------------ Edit Nasabah ------------------");

        try {
            in = new ObjectInputStream(new FileInputStream(fNasabah));
            Object curR = in.readObject();

            out = new ObjectOutputStream(new FileOutputStream(fNasabahTemporary));

            try {
                System.out.print("Masukan No Rekening Yang Datanya Ingin Anda Ubah : ");
                noRekening = inputUser.nextInt();

                while (ketemu == false && true){
                    N = (Nasabah) curR;
                    if (noRekening == N.getNoRekening()){
                        ketemu = true;
                    } else{
                        out.writeObject(N);
                        curR = in.readObject();
                    }
                    total++;
                }

            } catch (EOFException e){
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (ketemu == true){
                System.out.println("Nasabah Ditemukan");
                System.out.printf("%-15s %-8s %-20s %-15s %-20s %-15s %-15s %-15s\n", "No Rekening", "PIN", "Nama", "Jenis Kelamin", "Tanggal Lahir", "Pekerjaan", "Alamat", "Saldo");
                System.out.printf("%-15d %-8d %-20s %-15s %-20s %-15s %-15s %-15d\n", N.getNoRekening(), N.getPin(), N.getNama(), N.getJenisKelamin(), N.getTanggalLahir(), N.getPekerjaan(), N.getAlamat(), N.getSaldo());

                System.out.println();
                System.out.println("Masukan Data Yang Ingin Diubah : ");
                System.out.print("PIN  : ");
                pin = inputUser.nextInt();
                N.setPin(pin);
                try {
                    System.out.print("Nama : ");
                    nama = inputUserBuffer.readLine();
                    N.setNama(nama);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.print("Jenis Kelamin : ");
                    jenisKelamin = inputUserBuffer.readLine();
                    N.setJenisKelamin(jenisKelamin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    System.out.print("Tanggal Lahir : ");
                    tanggalLahir = inputUserBuffer.readLine();
                    N.setTanggalLahir(tanggalLahir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.print("Pekerjaan : ");
                    pekerjaan = inputUserBuffer.readLine();
                    N.setPekerjaan(pekerjaan);
                }catch (IOException e){
                    e.printStackTrace();
                }
                System.out.print("Saldo : ");
                saldo = inputUser.nextInt();
                N.setSaldo(saldo);
                try {
                    System.out.print("Alamat : ");
                    alamat = inputUserBuffer.readLine();
                    N.setAlamat(alamat);
                }catch (IOException e){
                    e.printStackTrace();
                }

                out.writeObject(N);

                try {
                    curR = in.readObject();
                    while (true){
                        N = (Nasabah) curR;
                        out.writeObject(N);
                        total++;
                        curR = in.readObject();
                    }
                } catch (EOFException e){
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else{
                System.out.println("No Rekening Yang Dicari Tidak Ditemukan");
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
        System.out.println("----------------------------------");


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
                    total++;
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
    }

    public void CetakNasabah() {
        Nasabah N = new Nasabah();
        int total = 0;
        ObjectInputStream in = null;

        // Deklarasikan Lokasi File
        File fNasabah = new File(directory+"nasabah\\nasabah.dat");

        try {
            in = new ObjectInputStream(new FileInputStream(fNasabah));
            Object curR = in.readObject();

            try {
                System.out.println("-------------------------------------------------------- Data Nasabah -------------------------------------------------------");
                System.out.printf("%-15s %-8s %-20s %-15s %-20s %-15s %-15s %-15s\n", "No Rekening", "PIN", "Nama", "Jenis Kelamin", "Tanggal Lahir", "Pekerjaan", "Alamat", "Saldo");
                while (true) {
                    N = (Nasabah) curR;
                    System.out.printf("%-15d %-8d %-20s %-15s %-20s %-15s %-15s %-15d\n", N.getNoRekening(), N.getPin(), N.getNama(), N.getJenisKelamin(), N.getTanggalLahir(), N.getPekerjaan(), N.getAlamat(), N.getSaldo());
                    total++;
                    curR = in.readObject();
                }
            } catch (EOFException e) {
                System.out.println("");
                System.out.println("Total Nasabah : "+total);
            } catch (ClassNotFoundException e) {
                System.out.println("Class tidak ditemukan");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
            in.close();
        } catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }

    int mainMenuNasabah(){
        System.out.println("Data Nasabah Bank ABC");
        System.out.println("1. Tambah Data Nasabah");
        System.out.println("2. Tampilkan Data Nasabah");
        System.out.println("3. Hapus Data Nasabah");
        System.out.println("4. Ubah Data Nasabah");
        System.out.println("0. Exit");
        System.out.print("Masukan Pilihan : ");
        int pilihan = inputUser.nextInt();
        System.out.println("");
        return pilihan;
    }

    public static void main(String[] args) {
        FileNasabah FN = new FileNasabah();
        int pilihan = FN.mainMenuNasabah();
        while (pilihan != 0){
            switch (pilihan){
                case 1 :
                    FN.TambahNasabah();
                    break;
                case 2 :
                    FN.CetakNasabah();
                    break;
                case 3 :
                    FN.HapusNasabah();
                    break;
                case 4 :
                    FN.UpdateNasabah();
                    break;
                default :
                    System.out.println("Pilihan Anda Tidak Ada di Menu");
                    break;
            }
            pilihan = FN.mainMenuNasabah();
        }
    }
}
