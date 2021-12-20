package Tubes.atm;

public class Transaksi implements java.io.Serializable{
    int noRekening;
    String tanggalTransaksi;
    String tipeTransaksi;
    int nominalTransaksi;

    public Transaksi(){
    }

    public Transaksi(int noRekening, String tanggalTransaksi, String tipeTransaksi, int nominalTransaksi){
        this.noRekening = noRekening;
        this.tanggalTransaksi = tanggalTransaksi;
        this.tipeTransaksi = tipeTransaksi;
        this.nominalTransaksi = nominalTransaksi;
    }

    public int getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(int noRekening) {
        this.noRekening = noRekening;
    }

    public String getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(String tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public String getTipeTransaksi() {
        return tipeTransaksi;
    }

    public void setTipeTransaksi(String tipeTransaksi) {
        this.tipeTransaksi = tipeTransaksi;
    }

    public int getNominalTransaksi() {
        return nominalTransaksi;
    }

    public void setNominalTransaksi(int nominalTransaksi) {
        this.nominalTransaksi = nominalTransaksi;
    }
}
