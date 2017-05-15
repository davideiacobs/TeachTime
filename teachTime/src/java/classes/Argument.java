/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import it.univaq.f4i.iw.framework.data.DataLayerException;

/**
 *
 * @author david
 */
public class Argument {
    private int key;
    private String nome;
    private Subject materia;
    private int materia_key;
    protected TeachTimeDataLayer ownerdatalayer;
    private boolean dirty;
    
    public Argument(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        key = 0;
        nome = "";
        materia = null;
        materia_key = 0;
        dirty = false;
    }
    
    public Argument(){
        this.ownerdatalayer = null;
        key = 0;
        nome = "";
        materia = null;
        materia_key = 0;
        dirty = false;
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
     * @return the materia
     */
    public Subject getMateria() throws DataLayerException{
        if(materia == null && materia_key > 0){
            materia = this.ownerdatalayer.getMateria(materia_key);
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
    
     public void copyFrom(Argument argomento){
        key = argomento.getKey();
        nome = argomento.getNome();
        materia_key = argomento.getMateria_key();
        this.dirty = true;
    }
}
