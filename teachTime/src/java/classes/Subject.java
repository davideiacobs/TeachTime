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
public class Subject {
    private int key;
    private String nome;
    protected TeachTimeDataLayer ownerdatalayer;
    private boolean dirty;
    private List<Argument> argomenti;
    
    public Subject(TeachTimeDataLayer datalayer){
        this.ownerdatalayer = datalayer;
        key = 0;
        nome = "";   
        dirty = false;
        argomenti = null;
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
     * @return the argomenti
     */
   /* public List<Argument> getArgomenti() throws DataLayerException {
        if(argomenti == null){
           argomenti = ownerdatalayer.getArgomentiByMateria(this.key);
        }
        return argomenti;
    }*/

    /**
     * @param argomenti the argomenti to set
     */
    public void setArgomenti(List<Argument> argomenti) {
        this.argomenti = argomenti;
        this.dirty = true;
    }
    
    public void copyFrom(Subject materia){
        key = materia.getKey();
        nome = materia.getNome();
        this.dirty = true;
    }
}
