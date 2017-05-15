/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.List;

/**
 *
 * @author david
 */
public class Repetition {
    private int key;
    private String luogoIncontro;
    private int costo;
    private String descr;
    private String città;
    private User tutor;
    private int tutor_key;
    private Subject materia;
    private int materia_key;
    protected TeachTimeDataLayer ownerdatalayer;
    private boolean dirty;
    private List<Argument> argomenti;
    
    public Repetition(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        key = 0;
        luogoIncontro = "";
        costo = 0;
        descr = "";
        città = "";
        tutor = null;
        tutor_key = 0;
        materia = null;
        materia_key = 0;       
        dirty = false;
        argomenti = null;
    }
    
     public Repetition(){
        this.ownerdatalayer = null;
        key = 0;
        luogoIncontro = "";
        costo = 0;
        descr = "";
        città = "";
        tutor = null;
        tutor_key = 0;
        materia = null;
        materia_key = 0;       
        dirty = false;
        argomenti = null;
    }

    /**
     * @return the ID
     */
    public int getKey() {
        return key;
    }

    /**
     * @param ID the ID to set
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * @return the luogoIncontro
     */
    public String getLuogoIncontro() {
        return luogoIncontro;
    }

    /**
     * @param luogoIncontro the luogoIncontro to set
     */
    public void setLuogoIncontro(String luogoIncontro) {
        this.luogoIncontro = luogoIncontro;
        this.dirty = true;
    }

    /**
     * @return the costo
     */
    public int getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(int costo) {
        this.costo = costo;
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
        this.dirty = true;
    }

    /**
     * @return the tutor_ID
     */
    public int getTutor_key() {
        return tutor_key;
    }

    /**
     * @param tutor_ID the tutor_ID to set
     */
   public void setTutor_key(int tutor_key){
        this.tutor_key = tutor_key;
        this.tutor = null;
        this.dirty = true;
    }

    /**
     * @return the materia_ID
     */
    public int getMateria_key() {
        return materia_key;
    }

    /**
     * @param materia_ID the materia_ID to set
     */
    public void setMateria_key(int materia_ID) {
        this.materia_key = materia_ID;
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
    
    public void setTutor(User tutor){
        this.tutor = tutor;
        this.tutor_key = tutor.getKey();
        this.dirty = true;
    }
    
    public void setSubject(Subject materia){
        this.materia = materia;
        this.materia_key = materia.getKey();
        this.dirty = true;
    }
    
    /*public User getTutor() throws DataLayerException{
        if (tutor == null && tutor_key > 0) {
            tutor = ownerdatalayer.getTutor(tutor_key);
        }
      
        return tutor;
    }
    
    public Subject getSubject() throws DataLayerException{
        if(materia == null && materia_key > 0){
            materia = ownerdatalayer.getSubject(materia_key);
        }
        return materia;
    }

    /**
     * @return the argomenti
     */
    /*public List<Argument> getArgomenti() {
        if(argomenti == null){
            argomenti = ownerdatalayer.getArgomentiByRipetizione(this.key);
        }
        return argomenti;
    }*/
    
    /**
     * @param argomenti the argomenti to set
     */
    public void setArgomenti(List<Argument> argomenti) {
        this.argomenti = argomenti;
    }
    
    public void copyFrom(Repetition ripetizione){
        key = ripetizione.getKey();
        luogoIncontro = ripetizione.getLuogoIncontro();
        città = ripetizione.getCittà();
        costo = ripetizione.getCosto();
        descr = ripetizione.getDescr();
        materia_key = ripetizione.getMateria_key();
        tutor_key = ripetizione.getTutor_key();
        this.dirty = true;
    }
    
}
