/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author david
 */
public class Booking {
    private int key;
    private int studente_key;
    private int ripetizione_key;
    private int stato;
    private int costo;
    private User studente;
    private PrivateLesson ripetizione;
    private String descr;
    private Date data;
    private Subject materia;
    private int materia_key;
    private int voto;
    private String recensione;
    @JsonIgnore
    private boolean dirty;
    protected TeachTimeDataLayer ownerdatalayer;
    
    
    public Booking(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        key = 0;
        studente_key = 0;
        studente = null;
        descr = "";
        data = null;
        stato = 0;
        costo=0;
        materia = null;
        materia_key = 0;
        ripetizione = null;
        ripetizione_key = 0;
        voto = -1;
        recensione = "";
        dirty = false;
    }
    
     public Booking(){
        this.ownerdatalayer = null;
        key = 0;
        studente_key = 0;
        studente = null;
        descr = "";
        data = null;
        stato = 0;
        costo=0;
        materia = null;
        materia_key = 0;
        ripetizione = null;
        ripetizione_key = 0;
        voto = -1;
        recensione = "";
        dirty = false;
    }

    
    
    public int getRipetizione_key() {
        return ripetizione_key;
    }

    
    public void setRipetizione_key(int ripetizione_key){
        this.ripetizione_key = ripetizione_key;
        this.ripetizione = null;
        this.dirty = true;
    }
    /**
     * @return the studente_key
     */
    public int getStudente_key() {
        return studente_key;
    }

    /**
     * @param studente_key the studente_key to set
     */
    public void setStudente_key(int studente_key) {
        this.studente_key = studente_key;
        this.studente = null;
        this.dirty = true;

    }

    
    public PrivateLesson getRipetizione() throws DataLayerException{
        if(ripetizione == null && ripetizione_key > 0) {
            ripetizione = this.ownerdatalayer.getRipetizione(ripetizione_key);
        }
        return ripetizione;
    }
    /**
     * @param tutor the tutor to set
     */
    
    
    public void setRipetizione(PrivateLesson ripetizione) {
        this.ripetizione = ripetizione;
        this.ripetizione_key = ripetizione.getKey();
        this.dirty = true;
    }
    /**
     * @return the studente
     */
    public User getStudente() throws DataLayerException {
        if(studente == null && studente_key > 0){
            studente = ownerdatalayer.getUtente(studente_key);
        }

        return studente;
    }

    /**
     * @param studente the studente to set
     */
    public void setStudente(User studente) {
        this.studente = studente;
        this.studente_key = studente.getKey();
        this.dirty = true;
    }

    /**
     * @return the descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * @param descr the descr to set
     */
    public void setDescr(String descr) {
        this.descr = descr;
        this.dirty = true;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
        this.dirty = true;
    }


    /**
     * @return the materia
     */
    public Subject getMateria() throws DataLayerException {
        if(materia == null && materia_key > 0){
            materia = ownerdatalayer.getMateria(materia_key);
        }
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(Subject materia) {
        this.materia = materia;
        this.materia_key = materia.getKey();
        this.dirty = true;
    }

    /**
     * @return the materia_key
     */
    public int getMateria_key() {
        return materia_key;
    }

    /**
     * @param materia_key the materia_key to set
     */
    public void setMateria_key(int materia_key) {
        this.materia_key = materia_key;
        this.materia = null;
        this.dirty = true;
    }

    /**
     * @return the voto
     */
    public int getVoto() {
        return voto;
    }

    /**
     * @param voto the voto to set
     */
    public void setVoto(int voto) {
        this.voto = voto;
        this.dirty = true;
    }

    /**
     * @return the recensione
     */
    public String getRecensione() {
        return recensione;
    }

    /**
     * @param recensione the recensione to set
     */
    public void setRecensione(String recensione) {
        this.recensione = recensione;
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

    /**
     * @return the stato
     */
    public int getStato() {
        return stato;
    }

    /**
     * @param stato the stato to set
     */
    public void setStato(int stato) {
        this.stato = stato;
    }

    /**
     * @return the costo
     */
    public int getCosto() throws DataLayerException {
        if(ripetizione_key > 0 && ownerdatalayer != null){
            return ownerdatalayer.getRipetizione(ripetizione_key).getCosto();
        }else{
            return -1;
        }
    }
    
    /**
     * @param costo the costo to set
     */
    public void setCosto(int costo)  {
        this.costo = costo;
        this.dirty = true;
    }
    
    public void copyFrom(Booking prenotazione) throws DataLayerException{
        ripetizione_key = prenotazione.getRipetizione_key();
        studente_key = prenotazione.getStudente_key();
        costo = prenotazione.getCosto();
        descr = prenotazione.getDescr();
        stato = prenotazione.getStato();
        data = prenotazione.getData();
        materia_key = prenotazione.getMateria_key();
   
        this.dirty = true;
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
        this.dirty = true;
    }
    
}
