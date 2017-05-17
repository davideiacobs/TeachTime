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
public class Subject {
    private int key;
    private String nome;
    private Category categoria;
    private int categoria_key;
    protected TeachTimeDataLayer ownerdatalayer;
    private boolean dirty;
    
    public Subject(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        key = 0;
        nome = "";
        categoria = null;
        categoria_key = 0;
        dirty = false;
    }
    
    public Subject(){
        this.ownerdatalayer = null;
        key = 0;
        nome = "";
        categoria = null;
        categoria_key = 0;
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
     * @return the categoria
     */
    public Category getMateria() throws DataLayerException{
        if(categoria == null && categoria_key > 0){
            categoria = this.ownerdatalayer.getMateria(categoria_key);
        }

        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Category categoria) {
        this.categoria = categoria;
        this.categoria_key = categoria.getKey();
        this.dirty = true;

    }

    /**
     * @return the categoria_key
     */
    public int getCategoria_key() {
        return categoria_key;
    }

    /**
     * @param categoria_key the categoria_key to set
     */
    public void setCategoria_key(int categoria_key) {
        this.categoria_key = categoria_key;
        this.categoria = null;
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
    
     public void copyFrom(Subject materia){
        key = materia.getKey();
        nome = materia.getNome();
        categoria_key = materia.getCategoria_key();
        this.dirty = true;
    }
}
