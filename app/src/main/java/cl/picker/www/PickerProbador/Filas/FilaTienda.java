package cl.picker.www.PickerProbador.Filas;

/**
 * Created by Freddy on 09-12-2015.
 */
public class FilaTienda {
    private String idTienda, codTienda, nombreTienda, ownerTienda, dirTienda, telTienda, webTienda, emailTienda;

    public FilaTienda(){

    }

    public FilaTienda(String idTienda, String codTienda, String nombreTienda, String ownerTienda, String dirTienda, String telTienda, String webTienda, String emailTienda) {
        this.idTienda = idTienda;
        this.codTienda = codTienda;
        this.nombreTienda = nombreTienda;
        this.ownerTienda = ownerTienda;
        this.dirTienda = dirTienda;
        this.telTienda = telTienda;
        this.webTienda = webTienda;
        this.emailTienda = emailTienda;
    }

    public String getEmailTienda() {
        return emailTienda;
    }

    public void setEmailTienda(String emailTienda) {
        this.emailTienda = emailTienda;
    }

    public String getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(String idTienda) {
        this.idTienda = idTienda;
    }

    public String getCodTienda() {
        return codTienda;
    }

    public void setCodTienda(String codTienda) {
        this.codTienda = codTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getOwnerTienda() {
        return ownerTienda;
    }

    public void setOwnerTienda(String ownerTienda) {
        this.ownerTienda = ownerTienda;
    }

    public String getDirTienda() {
        return dirTienda;
    }

    public void setDirTienda(String dirTienda) {
        this.dirTienda = dirTienda;
    }

    public String getTelTienda() {
        return telTienda;
    }

    public void setTelTienda(String telTienda) {
        this.telTienda = telTienda;
    }

    public String getWebTienda() {
        return webTienda;
    }

    public void setWebTienda(String webTienda) {
        this.webTienda = webTienda;
    }

   
}
