package Tubes.nasabah;

public class Nasabah implements java.io.Serializable{
    //Record dari nasabah
    int noRekening;
    int pin;
    String nama;
    String jenisKelamin;
    String tanggalLahir;
    String pekerjaan;
    String alamat;
    int saldo;

    //Konstruktur tanpa parameter
    public Nasabah(){
    }

    //Konstruktor dengan parameter;
    //Set nilai semua record
    Nasabah(int noRekening, int pin, String nama, String jenisKelamin, String tanggalLahir, String pekerjaan, String alamat, int saldo){
        this.noRekening = noRekening;
        this.pin = pin;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.pekerjaan = pekerjaan;
        this.alamat = alamat;
        this.saldo = saldo;
    }

    //Setter
    //Untuk mengubah nilai dari masing2 record
    public void setNoRekening(int noRekening){
        this.noRekening = noRekening;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public void setJenisKelamin(String jenisKelamin){
        this.jenisKelamin = jenisKelamin;
    }

    public void setTanggalLahir(String tanggalLahir){
        this.tanggalLahir = tanggalLahir;
    }

    public void setPekerjaan(String pekerjaan){
        this.pekerjaan = pekerjaan;
    }

    public void setAlamat(String alamat){
        this.alamat = alamat;
    }

    public void setSaldo(int saldo){
        this.saldo = saldo;
    }

    public void setPin(int pin){
        this.pin = pin;
    }

    //Getter
    //Mengembalikan nilai dari masing2 record
    public int getNoRekening(){
        return noRekening;
    }

    public String getNama(){
        return nama;
    }

    public String getJenisKelamin(){
        return jenisKelamin;
    }

    public String getTanggalLahir(){
        return tanggalLahir;
    }

    public String getPekerjaan(){
        return pekerjaan;
    }

    public String getAlamat(){
        return alamat;
    }

    public int getSaldo(){
        return saldo;
    }

    public int getPin(){
        return pin;
    }
}