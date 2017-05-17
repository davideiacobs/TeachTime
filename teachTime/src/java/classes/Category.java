package classes;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
public class Category {
    private int key;
    private String nome;
    protected TeachTimeDataLayer ownerdatalayer;
    private boolean dirty;
    private List<Subject> materie;
    
    public Category(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        key = 0;
        nome = "";   
        dirty = false;
        materie = null;
    }
    
    public Category(){
        this.ownerdatalayer = null;
        key = 0;
        nome = "";   
        dirty = false;
        materie = null;
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
     * @return the materie
     */
   /* public List<Subject> getMaterie() throws DataLayerException {
        if(materie == null){
           materie = ownerdatalayer.getMaterieByCategoria(this.key);
        }
        return materie;
    }*/

    /**
     * @param materie the materie to set
     */
    public void setMaterie(List<Subject> materie) {
        this.materie = materie;
        this.dirty = true;
    }
    
    public void copyFrom(Category materia){
        key = materia.getKey();
        nome = materia.getNome();
        this.dirty = true;
    }
}
