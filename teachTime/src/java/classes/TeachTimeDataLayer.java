/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.data.DataLayerMysqlImpl;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author david
 */
public class TeachTimeDataLayer extends DataLayerMysqlImpl{
    
    private PreparedStatement uUtente,iUtente,dUser, sUtenteByID, sCategoriaByID, uCategoria, iCategoria, uMateria;
    private PreparedStatement iMateria, sMateriaByID, uRipetizione, iRipetizione, sRipetizioneByID;
    private PreparedStatement sMaterieByCategoria, sMaterieByRipetizione, iRipetizioneHasMateria; 
    private PreparedStatement sRipetizioneByTutor, sRipetizioneByCategoria, sRipetizioneByMateria;
    private PreparedStatement dRipetizione,dRipetizioneHasMateria, sTutorByRipetizione, sMateriaByNome;
    public TeachTimeDataLayer(DataSource datasource) throws SQLException, NamingException {
        super(datasource);
    }
    
    
    @Override
    public void init() throws DataLayerException {
        try {
            super.init();

            //precompiliamo tutte le query utilizzate

            uUtente = connection.prepareStatement("UPDATE utente SET citta=?,telefono=?,titolo_di_studio=?,img_profilo=? WHERE ID=?");
            iUtente = connection.prepareStatement("INSERT INTO utente (nome,cognome,email,pwd,citta,telefono,data_di_nascita,titolo_di_studio,img_profilo) VALUES(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sUtenteByID = connection.prepareStatement("SELECT * FROM utente WHERE ID=?");
            sCategoriaByID = connection.prepareStatement("SELECT * FROM categoria WHERE ID=?");
            uCategoria = connection.prepareStatement("UPDATE categoria SET nome=? WHERE ID=?");
            iCategoria = connection.prepareStatement("INSERT INTO categoria (nome) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            uMateria = connection.prepareStatement("UPDATE materia SET nome=?, categoria_ID=? WHERE ID=?");
            iMateria = connection.prepareStatement("INSERT INTO materia (nome, categoria_ID) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            sMateriaByNome = connection.prepareStatement("SELECT * FROM materia WHERE nome=?");
            sMateriaByID = connection.prepareStatement("SELECT * FROM materia WHERE ID=?");
            uRipetizione = connection.prepareStatement("UPDATE ripetizione SET luogo_incontro=?, costo_per_ora=?,descrizione=?,citta=? WHERE ID=?");
            iRipetizione = connection.prepareStatement("INSERT INTO ripetizione (luogo_incontro,costo_per_ora,descrizione,citta,tutor_ID) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sRipetizioneByID = connection.prepareStatement("SELECT * FROM ripetizione WHERE ID=?");
            sMaterieByCategoria = connection.prepareStatement("SELECT a.ID FROM materia AS a WHERE a.categoria_ID=?");
            sMaterieByRipetizione = connection.prepareStatement("SELECT rha.materia_ID FROM ripetizione_has_materia AS rha WHERE ripetizione_ID=?");
            iRipetizioneHasMateria = connection.prepareStatement("INSERT INTO ripetizione_has_materia (ripetizione_ID,materia_ID) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            sRipetizioneByTutor = connection.prepareStatement("SELECT ripetizione.ID FROM ripetizione WHERE tutor_ID=?");
            sRipetizioneByCategoria = connection.prepareStatement("SELECT r.ID FROM ((SELECT materia.ID FROM materia WHERE materia.categoria_ID=?) AS m INNER JOIN ripetizione_has_materia AS rha ON (m.ID = rha.materia_ID) INNER JOIN ripetizione AS r ON (rha.ripetizione_ID = r.ID)) WHERE r.citta=?"); 
            
            sRipetizioneByMateria = connection.prepareStatement("SELECT r.ID FROM ((SELECT ripetizione_has_materia.ripetizione_ID FROM ripetizione_has_materia WHERE ripetizione_has_materia.materia_ID=?) AS rha INNER JOIN ripetizione AS r ON (rha.ripetizione_ID = r.ID)) WHERE r.citta=?");
            dRipetizione = connection.prepareStatement("DELETE FROM ripetizione WHERE ID=?");
            dRipetizioneHasMateria = connection.prepareStatement("DELETE FROM ripetizione_has_materia WHERE ripetizione_ID=?");
            sTutorByRipetizione = connection.prepareStatement("SELECT ripetizione.tutor_ID FROM ripetizione WHERE ID=?");
        } catch (SQLException ex) {
            throw new DataLayerException("Error initializing newspaper data layer", ex);
        } 
    }
       
   public Repetition createRepetition() {
        return new Repetition(this);
    }

    public Repetition createRepetition(ResultSet rs) throws DataLayerException {
        try {
            Repetition a = new Repetition(this);
            a.setKey(rs.getInt("ID"));
            a.setLuogoIncontro(rs.getString("luogo_incontro"));
            a.setCosto(rs.getInt("costo_per_ora"));
            a.setDescr(rs.getString("descrizione"));
            a.setCittà(rs.getString("citta"));
            a.setTutor_key(rs.getInt("tutor_ID"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create repetition object form ResultSet", ex);
        }
    }
    
    public Category createCategory(){
        return new Category(this);
    }
    
    public Category createCategory(ResultSet rs) throws DataLayerException{
        try{
            Category a = new Category(this);
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("nome"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create category object form ResultSet", ex);
            }
    }
    
    public Subject createSubject(){
        return new Subject(this);
    }
    
    public Subject createSubject(ResultSet rs) throws  DataLayerException{
        try{
            Subject a = new Subject(this);
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("nome"));
            a.setCategoria_key(rs.getInt("categoria_ID"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create subject object from ResultSet",ex);
        }
    }
    
     public Prenotation createPrenotation(){
        return new Prenotation(this);
    }
    
    public Prenotation createPrenotation(ResultSet rs) throws DataLayerException{
        try{
            Prenotation a = new Prenotation(this);
            a.setStudente_key(rs.getInt("utente_ID"));
            a.setRipetizione_key(rs.getInt("ripetizone_ID"));
            //manca il set tutor key
            a.setDescr(rs.getString("descrizione"));
            a.setStato(rs.getInt("stato"));
            a.setCosto(rs.getInt("costo"));
            GregorianCalendar data = new GregorianCalendar();
            java.sql.Date datasql;
            datasql = rs.getDate("data");
            data.setTime(datasql);
            a.setData(data); 
            a.setOra(rs.getTime("ora"));
            a.setMateria_key(rs.getInt("materia_ID"));
            a.setVoto(rs.getInt("voto"));
            a.setRecensione(rs.getString("recensione"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create prenotation object from ResultSet",ex);
        }
    }
    
    
    
    public User createUser(){
        return new User(this);
    }
    
    
     public User createUser(ResultSet rs) throws  DataLayerException{
        try{
            User a = new User(this);
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("nome"));
            a.setCognome(rs.getString("cognome"));
            a.setEmail(rs.getString("email"));
            a.setPwd(rs.getString("pwd"));
            a.setCittà(rs.getString("citta"));
            a.setEmail(rs.getString("email"));
            a.setTelefono(rs.getString("telefono"));
            GregorianCalendar dataNascita = new GregorianCalendar();
            java.sql.Date date;
            date = rs.getDate("data_di_nascita");
            dataNascita.setTime(date);
            a.setDataDiNascita(dataNascita);
            a.setTitoloDiStudi(rs.getString("titolo_di_studio"));
            a.setImgProfilo(rs.getString("img_profilo"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create user object from ResultSet",ex);
        }
    }
     
     
   
     public User getUtente(int utente_key) throws DataLayerException {
        try {
            sUtenteByID.setInt(1, utente_key); //setta primo parametro query a project_key
            try (ResultSet rs = sUtenteByID.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return createUser(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load utente by ID", ex);
        }
        return null;
    }
     
    public Category getCategoria(int categoria_key) throws DataLayerException {
        try {
            sCategoriaByID.setInt(1, categoria_key); //setta primo parametro query a project_key
            try (ResultSet rs = sCategoriaByID.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return createCategory(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load categoria by ID", ex);
        }
        return null;
    }
    
     public Subject getMateria(int materia_key) throws DataLayerException {
        try {
            sMateriaByID.setInt(1, materia_key); //setta primo parametro query a project_key
            try (ResultSet rs = sMateriaByID.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return createSubject(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load materia by ID", ex);
        }
        return null;
    }
     
    public int getTutorByRipetizione(int studente_key) throws DataLayerException{
        try {
            sTutorByRipetizione.setInt(1, studente_key); //setta primo parametro query a project_key
            try (ResultSet rs = sTutorByRipetizione.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return rs.getInt("tutor_ID");
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load materia by ID", ex);
        }
        return 0;
    }

     
     
     
    public List<Subject> getMaterieByCategoria(int categoria_key) throws DataLayerException{
        List<Subject> result = new ArrayList<>();
        try{
            sMaterieByCategoria.setInt(1, categoria_key);
            try(ResultSet rs = sMaterieByCategoria.executeQuery()) {
                while(rs.next()){
                 result.add((Subject) getMateria(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load subjects", ex);
        }
        return result; //restituisce in result tutti gli oggetti Project esistenti
    }
    
    
    public List<Subject> getMaterieByRipetizione(int ripetizione_key) throws DataLayerException{
        List<Subject> result = new ArrayList<>();
        try{
            sMaterieByRipetizione.setInt(1,ripetizione_key);
            try(ResultSet rs = sMaterieByRipetizione.executeQuery()) {
                while(rs.next()){
                 result.add((Subject) getMateria(rs.getInt("materia_ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load subjects", ex);
        }
        return result;
    }
    
    public Repetition getRipetizione(int ripetizione_key) throws DataLayerException {
        try {
            sRipetizioneByID.setInt(1, ripetizione_key); //setta primo parametro query a project_key
            try (ResultSet rs = sRipetizioneByID.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return createRepetition(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by ID", ex);
        }
        return null;
    }
    
    public List<Repetition> getRipetizioniByTutor(int tutor_key) throws DataLayerException{
        List<Repetition> result = new ArrayList<>();
        try {
            sRipetizioneByTutor.setInt(1, tutor_key); //setta primo parametro query a project_key
            try (ResultSet rs = sRipetizioneByTutor.executeQuery()) {
                while(rs.next()){
                 result.add((Repetition) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by tutor_ID", ex);
        }
        return result;
    }
    
    public List<Repetition> getRipetizioniByCategoria(String città, int categoria) throws DataLayerException{
        List<Repetition> result = new ArrayList<>();
        try {
            sRipetizioneByCategoria.setInt(1, categoria);
            sRipetizioneByCategoria.setString(2, città);

            try (ResultSet rs = sRipetizioneByCategoria.executeQuery()) {
                while(rs.next()){
                 result.add((Repetition) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by filter", ex);
        }
        return result;
    }
    
    public List<Repetition> getRipetizioniByMateria(String città, int materia) throws DataLayerException{
        List<Repetition> result = new ArrayList<>();
        try {
            sRipetizioneByMateria.setInt(1, materia);
            sRipetizioneByMateria.setString(2, città);

            try (ResultSet rs = sRipetizioneByMateria.executeQuery()) {
                while(rs.next()){
                 result.add((Repetition) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by filter", ex);
        }
        return result;
    }
    
    
     public void storeUser(User utente) throws DataLayerException {
        int key = utente.getKey();
        try {
            if (key > 0) {
                if (!utente.isDirty()) {
                    return;
                }
                uUtente.setString(1, utente.getCittà());
                uUtente.setString(2, utente.getTelefono());    
                uUtente.setString(3, utente.getTitoloDiStudi());
                uUtente.setString(4, utente.getImgProfilo());
                uUtente.setInt(5, key);
                uUtente.executeUpdate();
            } else { //insert
                iUtente.setString(1, utente.getNome());
                iUtente.setString(2, utente.getCognome());
                iUtente.setString(3, utente.getEmail());
                iUtente.setString(4, utente.getPwd());
                iUtente.setString(5, utente.getCittà());
                iUtente.setString(6, utente.getTelefono());
                Date sqldate = new Date(utente.getDataDiNascita().getTimeInMillis());
                iUtente.setDate(7, sqldate);       
                iUtente.setString(8, utente.getTitoloDiStudi());
                iUtente.setString(9, utente.getImgProfilo());
                
                if (iUtente.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    try (ResultSet keys = iUtente.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            if (key > 0) {
                utente.copyFrom(getUtente(key));
            }
            utente.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store utente", ex);
        }
    }
    
     
      public void storeCategoria(Category categoria) throws DataLayerException {
        int key = categoria.getKey();
        try {
            if (key > 0) {
                if (!categoria.isDirty()) {
                    return;
                }
                uCategoria.setString(1, categoria.getNome());
                uCategoria.setInt(2, key);
                uCategoria.executeUpdate();
            } else { //insert
                iCategoria.setString(1, categoria.getNome());
                
                
                if (iCategoria.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    try (ResultSet keys = iCategoria.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            if (key > 0) {
                categoria.copyFrom(getCategoria(key));
            }
            categoria.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store categoria", ex);
        }
    }
      
      
     public void storeMateria(Subject materia) throws DataLayerException {
        int key = materia.getKey();
        try {
            if (key > 0) {
                if (!materia.isDirty()) {
                    return;
                }
                uMateria.setString(1, materia.getNome());
                uMateria.setInt(2, materia.getCategoria_key());
                uMateria.setInt(3, key);
                uMateria.executeUpdate();
            } else { //insert
                iMateria.setString(1, materia.getNome());
                iMateria.setInt(2, materia.getCategoria_key());
                
                
                if (iMateria.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    try (ResultSet keys = iMateria.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            if (key > 0) {
                materia.copyFrom(getMateria(key));
            }
            materia.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store materia", ex);
        }
    }
     
     public void storeRepetition(Repetition ripetizione) throws DataLayerException {
        int key = ripetizione.getKey();
        try {
            if (key > 0) {
                if (!ripetizione.isDirty()) {
                    return;
                }
                uRipetizione.setString(1, ripetizione.getLuogoIncontro());
                uRipetizione.setInt(2, ripetizione.getCosto());
                uRipetizione.setString(3, ripetizione.getDescr());
                uRipetizione.setString(4, ripetizione.getCittà());
                uRipetizione.setInt(5, key);
                uRipetizione.executeUpdate();   
                dRipetizioneHasMateria.setInt(1, key);
                dRipetizioneHasMateria.executeUpdate();
            } else { //insert
                iRipetizione.setString(1, ripetizione.getLuogoIncontro());
                iRipetizione.setInt(2, ripetizione.getCosto());
                iRipetizione.setString(3, ripetizione.getDescr());
                iRipetizione.setString(4, ripetizione.getCittà());
                iRipetizione.setInt(5, ripetizione.getTutor_key());
                
                
                if (iRipetizione.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    try (ResultSet keys = iRipetizione.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            if (key > 0) {
                ripetizione.copyFrom(getRipetizione(key));
            }
            List<Subject> materie = ripetizione.getMaterie();
            for(Subject m : materie){
                sMateriaByNome.setString(1, m.getNome());
                try(ResultSet rs = sMateriaByNome.executeQuery()){
                    if(!rs.next()){
                        iMateria.setString(1, m.getNome());
                        iMateria.setInt(2, m.getCategoria_key());
                        iMateria.executeUpdate();
                    }
                }
                iRipetizioneHasMateria.setInt(1, key);
                iRipetizioneHasMateria.setInt(2, m.getKey());
                iRipetizioneHasMateria.executeUpdate();
            }
               
            ripetizione.setDirty(false);

            
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store ripetizione", ex);
        }
    }
     
    public void deleteRipetizione(int ripetizione_key) throws DataLayerException{
        try{
            dRipetizione.setInt(1, ripetizione_key);
            dRipetizione.executeUpdate();
            }catch (SQLException ex) {
            throw new DataLayerException("Unable to delete rioetizione", ex);
        }
    }

    
}
