/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;

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
    @JsonIgnore
    private Category categoria;
    @JsonIgnore
    private int categoria_key;
    protected TeachTimeDataLayer ownerdatalayer;
    private boolean dirty;
    private List<Subject> materie;
    
    public Repetition(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        key = 0;
        luogoIncontro = "";
        costo = 0;
        descr = "";
        città = "";
        tutor = null;
        tutor_key = 0;
        categoria = null;
        categoria_key = 0;       
        dirty = false;
        materie = null;
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
        categoria = null;
        categoria_key = 0;       
        dirty = false;
        materie = null;
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
     * @return the categoria_ID
     */
   @JsonIgnore
    public int getCategoria_key() {
        return categoria_key;
    }

    /**
     * @param categoria_ID the categoria_ID to set
     */
    @JsonIgnore
    public void setCategoria_key(int categoria_ID) {
        this.categoria_key = categoria_ID;
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
    
    public void setTutor(User tutor){
        this.tutor = tutor;
        this.tutor_key = tutor.getKey();
        this.dirty = true;
    }
    
    @JsonIgnore
    public void setCategoria(Category categoria){
        this.categoria = categoria;
        this.categoria_key = categoria.getKey();
        this.dirty = true;
    }
    
    @JsonIgnore
    public Category getCategoria() throws DataLayerException{
        if (categoria == null && categoria_key > 0) {
            categoria = ownerdatalayer.getCategoria(categoria_key);
        }
        return categoria;
    }
    
    public User getTutor() throws DataLayerException{
        if (tutor == null && tutor_key > 0) {
            tutor = ownerdatalayer.getUtente(tutor_key);
        }
      
        return tutor;
    }
    
    
    

    /**
     * @return the materie
     */
    public List<Subject> getMaterie() throws DataLayerException {
        if(materie == null){
            materie = ownerdatalayer.getMaterieByRipetizione(this.key);
        }
        return materie;
    }
    
    /**
     * @param materie the materie to set
     */
    public void setMaterie(List<Subject> materie) {
        this.materie = materie;
    }
    
    public void copyFrom(Repetition ripetizione){
        key = ripetizione.getKey();
        luogoIncontro = ripetizione.getLuogoIncontro();
        città = ripetizione.getCittà();
        costo = ripetizione.getCosto();
        descr = ripetizione.getDescr();
        tutor_key = ripetizione.getTutor_key();
        this.dirty = true;
    }
    
}
