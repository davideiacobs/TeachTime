/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import it.univaq.f4i.iw.framework.data.DataLayerException;

/**
 *
 * @author iacobs
 */
public class Auth {
    
    private User utente;
    private int utente_key;
    private String mail;
    private String pwd;
    private String token;
    protected TeachTimeDataLayer ownerdatalayer;
    private boolean dirty;
    
    public Auth(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        utente = null;
        utente_key = 0;
        mail = "";
        pwd = "";
        token = "";
        dirty = false;
    }
    
    public Auth(){
        this.ownerdatalayer = null;
        utente = null;
        mail = "";
        pwd = "";
        token = "";
        utente_key = 0;        
        dirty = false;
    }

    /**
     * @return the utente
     */
    public User getUtente() throws DataLayerException {
        if(utente == null && utente_key > 0) {
            utente = this.ownerdatalayer.getUtente(utente_key);
        }
        return utente;
    }

    /**
     * @param utente the utente to set
     */
    public void setUtente(User utente) {
        this.utente_key = utente.getKey();
        this.utente = utente;
        this.dirty = true;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
        this.dirty = true;
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
        this.dirty = true;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
        this.dirty = true;
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
    
}
