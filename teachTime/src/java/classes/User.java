/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.GregorianCalendar;

/**
 *
 * @author david
 */
public class User {
    private int key;
    private String nome;
    private String cognome;
    private String email;
    private String pwd;
    private String città;
    private String telefono;
    private GregorianCalendar dataDiNascita;
    private String titoloDiStudi;
    private String imgProfilo;
    protected TeachTimeDataLayer ownerdatalayer;
    private boolean dirty;
    
    public User(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        key = 0;
        nome = "";
        cognome = "";
        email = "";
        pwd = "";
        città = "";
        telefono = "";
        dataDiNascita = null;
        titoloDiStudi = "";
        imgProfilo = "";
        dirty = false;
    }
    
    
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome the cognome to set
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the città
     */
    public String getCittà() {
        return città;
    }

    /**
     * @param città the città to set
     */
    public void setCittà(String città) {
        this.città = città;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the dataDiNascita
     */
    public GregorianCalendar getDataDiNascita() {
        return dataDiNascita;
    }

    /**
     * @param dataDiNascita the dataDiNascita to set
     */
    public void setDataDiNascita(GregorianCalendar dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    /**
     * @return the titoloDiStudi
     */
    public String getTitoloDiStudi() {
        return titoloDiStudi;
    }

    /**
     * @param titoloDiStudi the titoloDiStudi to set
     */
    public void setTitoloDiStudi(String titoloDiStudi) {
        this.titoloDiStudi = titoloDiStudi;
    }

    /**
     * @return the imgProfilo
     */
    public String getImgProfilo() {
        return imgProfilo;
    }

    /**
     * @param imgProfilo the imgProfilo to set
     */
    public void setImgProfilo(String imgProfilo) {
        this.imgProfilo = imgProfilo;
    }

    /**
     * @return the key
     */
    public int getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * @return the dirty
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * @param dirty the dirty to set
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
    public void copyFrom(User utente) throws DataLayerException {
        key = utente.getKey();
        nome = utente.getNome();
        cognome = utente.getCognome();
        email = utente.getEmail();
        pwd = utente.getPwd();
        città = utente.getCittà();
        telefono = utente.getTelefono();
        titoloDiStudi = utente.getTitoloDiStudi();
        dataDiNascita = utente.getDataDiNascita();
        imgProfilo = utente.getImgProfilo();
        this.dirty = true;
    }
    
}
