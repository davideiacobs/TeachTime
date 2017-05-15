/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Time;
import java.util.GregorianCalendar;

/**
 *
 * @author david
 */
public class Prenotation {
    private int tutor_key;
    private int studente_key;
    private int ripetizione_key;
    private int stato;
    private User tutor;
    private User studente;
    private Repetition ripetizione;
    private String descr;
    private GregorianCalendar data;
    private Time ora;
    private Argument argomento;
    private int argomento_key;
    private int voto;
    private String recensione;
    private boolean dirty;
    protected TeachTimeDataLayer ownerdatalayer;
    
    
    public Prenotation(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        tutor_key = 0;
        studente_key = 0;
        tutor = null;
        studente = null;
        descr = "";
        data = null;
        stato = -1;
        ora = null;
        argomento = null;
        argomento_key = 0;
        ripetizione = null;
        ripetizione_key = 0;
        voto = 0;
        recensione = "";
        dirty = false;
    }

    /**
     * @return the tutor_key
     */
    public int getTutor_key() {
        return tutor_key;
    }
    
    public int getRipetizione_key() {
        return ripetizione_key;
    }

    /**
     * @param tutor_key the tutor_key to set
     */
    public void setTutor_key(int tutor_key) {
        this.tutor_key = tutor_key;
        this.tutor = null;
        this.dirty = true;
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

    /**
     * @return the tutor
     */
    public User getTutor() {
        if(tutor == null && tutor_key > 0){
           tutor = this.ownerdatalayer.getTutor(tutor_key);
        }
        return tutor;
    }
    
    public Repetition getRipetizione(){
        if(ripetizione == null && ripetizione_key > 0) {
            ripetizione = this.ownerdatalayer.getRipetizione(ripetizione_key);
        }
    }
    /**
     * @param tutor the tutor to set
     */
    public void setTutor(User tutor) {
        this.tutor = tutor;
        this.tutor_key = tutor.getKey();
        this.dirty = true;
    }
    
    public void setRipetizione(Repetition ripetizione) {
        this.ripetizione = ripetizione;
        this.ripetizione_key = ripetizione.getKey();
        this.dirty = true;
    }
    /**
     * @return the studente
     */
    public User getStudente() {
        if(studente == null && studente_key > 0){
            studente = ownerdatalayer.getStudente(studente_key);
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
    public GregorianCalendar getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(GregorianCalendar data) {
        this.data = data;
        this.dirty = true;
    }

    /**
     * @return the ora
     */
    public Time getOra() {
        return ora;
    }

    /**
     * @param ora the ora to set
     */
    public void setOra(Time ora) {
        this.ora = ora;
        this.dirty = true;
    }

    /**
     * @return the argomento
     */
    public Argument getArgomento() {
        if(argomento == null && argomento_key > 0){
            argomento = ownerdatalayer.getArgomento(argomento_key);
        }
        return argomento;
    }

    /**
     * @param argomento the argomento to set
     */
    public void setArgomento(Argument argomento) {
        this.argomento = argomento;
        this.argomento_key = argomento.getKey();
        this.dirty = true;
    }

    /**
     * @return the argomento_key
     */
    public int getArgomento_key() {
        return argomento_key;
    }

    /**
     * @param argomento_key the argomento_key to set
     */
    public void setArgomento_key(int argomento_key) {
        this.argomento_key = argomento_key;
        this.argomento = null;
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
    public boolean isDirty() {
        return dirty;
    }

    /**
     * @param dirty the dirty to set
     */
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
    
}
