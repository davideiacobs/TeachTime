/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author iacobs
 */
public class Session {
    @JsonIgnore
    private int id;
    private String token;
    private int utente_key;
    @JsonIgnore
    private User utente;
    protected TeachTimeDataLayer ownerdatalayer;
    @JsonIgnore
    private boolean dirty;
    
    public Session(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        id = 0;
        token = "";
        utente = null;
        utente_key = 0;
        dirty = false;
    }
    
    public Session(){
        this.ownerdatalayer = null;
        id = 0;
        token = "";
        utente = null;
        utente_key = 0;
        dirty = false;
    }

    /**
     * @return the id
     */
    @JsonIgnore
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @JsonIgnore
    public void setId(int id) {
        this.id = id;
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
     * @return the utente_key
     */
    public int getUtente_key() {
        return utente_key;
    }

    /**
     * @param utente_key the utente_key to set
     */
    public void setUtente_key(int utente_key) {
        this.utente_key = utente_key;
        this.utente = null;
        this.dirty = true;
    }

    /**
     * @return the utente
     */
    @JsonIgnore
    public User getUtente() throws DataLayerException {
        if(utente_key>0 && utente == null){
            utente = ownerdatalayer.getUtente(utente_key);
        }
        return utente;
    }

    /**
     * @param utente the utente to set
     */
    @JsonIgnore
    public void setUtente(User utente) {
        this.utente_key = utente.getKey();
        this.utente = utente;
        this.dirty = true;
    }

    /**
     * @return the dirty
     */
    @JsonIgnore
    public boolean isDirty() {
        return dirty;
    }

    /**
     * @param dirty the dirty to set
     */
    @JsonIgnore
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
     public void copyFrom(Session s){
        id = s.getId();
        token = s.getToken();
        utente_key = s.getUtente_key();
        this.dirty = true;
    }
    
}
