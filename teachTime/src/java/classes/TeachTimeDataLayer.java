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
    
    private PreparedStatement uUtente,iUtente,dUser, sUtenteByID, sMateriaByID, uMateria, iMateria, uArgomento;
    private PreparedStatement iArgomento, sArgomentoByID, uRipetizione, iRipetizione, sRipetizioneByID;
    private PreparedStatement sArgomentiByMateria, sArgomentiByRipetizione, iRipetizioneHasArgomento; 
    private PreparedStatement sRipetizioneByTutor, sRipetizioneByCittàMateria, sRipetizioneByFilter;
    private PreparedStatement dRipetizione,dRipetizioneHasArgomento;
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
            sMateriaByID = connection.prepareStatement("SELECT * FROM materia WHERE ID=?");
            uMateria = connection.prepareStatement("UPDATE materia SET nome=? WHERE ID=?");
            iMateria = connection.prepareStatement("INSERT INTO materia (nome) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            uArgomento = connection.prepareStatement("UPDATE argomento SET nome=?, materia_ID=? WHERE ID=?");
            iArgomento = connection.prepareStatement("INSERT INTO argomento (nome, materia_ID) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            sArgomentoByID = connection.prepareStatement("SELECT * FROM argomento WHERE ID=?");
            uRipetizione = connection.prepareStatement("UPDATE ripetizione SET luogo_incontro=?, costo_per_ora=?,descrizione=?,citta=? WHERE ID=?");
            iRipetizione = connection.prepareStatement("INSERT INTO ripetizione (luogo_incontro,costo_per_ora,descrizione,citta,utente_ID,materia_ID) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sRipetizioneByID = connection.prepareStatement("SELECT * FROM ripetizione WHERE ID=?");
            sArgomentiByMateria = connection.prepareStatement("SELECT a.ID FROM argomento AS a WHERE a.materia_ID=?");
            sArgomentiByRipetizione = connection.prepareStatement("SELECT rha.argomento_ID FROM ripetizione_has_argomento AS rha WHERE ripetizione_ID=?");
            iRipetizioneHasArgomento = connection.prepareStatement("INSERT INTO ripetizione_has_argomento (ripetizione_ID,ripetizione_materia_ID,argomento_ID) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sRipetizioneByTutor = connection.prepareStatement("SELECT ripetizione.ID FROM ripetizione WHERE utente_ID=?");
            sRipetizioneByCittàMateria = connection.prepareStatement("SELECT r.ID FROM ripetizione AS r WHERE citta=? AND materia_ID=?");
            sRipetizioneByFilter = connection.prepareStatement("SELECT r.ID FROM ((SELECT ripetizione_has_argomento.ripetizione_ID FROM ripetizione_has_argomento WHERE ripetizione_has_argomento.argomento_ID=? AND ripetizione_has_argomento.ripetizione_materia_ID=?) AS rha INNER JOIN ripetizione AS r ON (rha.ripetizione_ID = r.ID)) WHERE r.citta=?");
            dRipetizione = connection.prepareStatement("DELETE FROM ripetizione WHERE ID=?");
            dRipetizioneHasArgomento = connection.prepareStatement("DELETE FROM ripetizione_has_argomento WHERE ripetizione_ID=?");
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
            a.setMateria_key(rs.getInt("materia_ID"));
            a.setTutor_key(rs.getInt("utente_ID"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create repetition object form ResultSet", ex);
        }
    }
    
    public Subject createSubject(){
        return new Subject(this);
    }
    
    public Subject createSubject(ResultSet rs) throws DataLayerException{
        try{
            Subject a = new Subject(this);
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("nome"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create subject object form ResultSet", ex);
            }
    }
    
    public Argument createArgument(){
        return new Argument(this);
    }
    
    public Argument createArgument(ResultSet rs) throws  DataLayerException{
        try{
            Argument a = new Argument(this);
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("nome"));
            a.setMateria_key(rs.getInt("materia_ID"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create argument object from ResultSet",ex);
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
            GregorianCalendar data = new GregorianCalendar();
            java.sql.Date datasql;
            datasql = rs.getDate("data");
            data.setTime(datasql);
            a.setData(data); 
            a.setOra(rs.getTime("ora"));
            a.setArgomento_key(rs.getInt("argomento_ID"));
            a.setVoto(rs.getInt("voto"));
            a.setRecensione(rs.getString("recensione"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create argument object from ResultSet",ex);
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
            throw new DataLayerException("Unable to create argument object from ResultSet",ex);
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
    
     public Argument getArgomento(int argomento_key) throws DataLayerException {
        try {
            sArgomentoByID.setInt(1, argomento_key); //setta primo parametro query a project_key
            try (ResultSet rs = sArgomentoByID.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return createArgument(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load materia by ID", ex);
        }
        return null;
    }
     
    public List<Argument> getArgomentiByMateria(int materia_key) throws DataLayerException{
        List<Argument> result = new ArrayList<>();
        try{
            sArgomentiByMateria.setInt(1, materia_key);
            try(ResultSet rs = sArgomentiByMateria.executeQuery()) {
                while(rs.next()){
                 result.add((Argument) getArgomento(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load projects", ex);
        }
        return result; //restituisce in result tutti gli oggetti Project esistenti
    }
    
    
    public List<Argument> getArgomentiByRipetizione(int ripetizione_key) throws DataLayerException{
        List<Argument> result = new ArrayList<>();
        try{
            sArgomentiByRipetizione.setInt(1,ripetizione_key);
            try(ResultSet rs = sArgomentiByRipetizione.executeQuery()) {
                while(rs.next()){
                 result.add((Argument) getArgomento(rs.getInt("argomento_ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load projects", ex);
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
    
    public List<Repetition> getRipetizioniByFilter(String città, int materia) throws DataLayerException{
        List<Repetition> result = new ArrayList<>();
        try {
            sRipetizioneByCittàMateria.setString(1, città);
            sRipetizioneByCittàMateria.setInt(2, materia);

            try (ResultSet rs = sRipetizioneByCittàMateria.executeQuery()) {
                while(rs.next()){
                 result.add((Repetition) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by filter", ex);
        }
        return result;
    }
    
    public List<Repetition> getRipetizioniByFilter(String città, int materia, int argomento) throws DataLayerException{
        List<Repetition> result = new ArrayList<>();
        try {
            sRipetizioneByFilter.setInt(1, argomento);
            sRipetizioneByFilter.setInt(2, materia);            
            sRipetizioneByFilter.setString(3, città);

            try (ResultSet rs = sRipetizioneByFilter.executeQuery()) {
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
    
     
      public void storeSubject(Subject materia) throws DataLayerException {
        int key = materia.getKey();
        try {
            if (key > 0) {
                if (!materia.isDirty()) {
                    return;
                }
                uMateria.setString(1, materia.getNome());
                uMateria.setInt(2, key);
                uMateria.executeUpdate();
            } else { //insert
                iMateria.setString(1, materia.getNome());
                
                
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
            throw new DataLayerException("Unable to store utente", ex);
        }
    }
      
      
     public void storeArgument(Argument argomento) throws DataLayerException {
        int key = argomento.getKey();
        try {
            if (key > 0) {
                if (!argomento.isDirty()) {
                    return;
                }
                uArgomento.setString(1, argomento.getNome());
                uArgomento.setInt(2, argomento.getMateria_key());
                uArgomento.setInt(3, key);
                uArgomento.executeUpdate();
            } else { //insert
                iArgomento.setString(1, argomento.getNome());
                iArgomento.setInt(2, argomento.getMateria_key());
                
                
                if (iArgomento.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    try (ResultSet keys = iArgomento.getGeneratedKeys()) {
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
                argomento.copyFrom(getArgomento(key));
            }
            argomento.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store utente", ex);
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
                dRipetizioneHasArgomento.setInt(1, key);
                dRipetizioneHasArgomento.executeUpdate();
                List<Argument> argomenti = ripetizione.getArgomenti();
                for(Argument a : argomenti){
                    iRipetizioneHasArgomento.setInt(1, key);
                    iRipetizioneHasArgomento.setInt(2, ripetizione.getMateria_key());
                    iRipetizioneHasArgomento.setInt(3, a.getKey());
                    iRipetizioneHasArgomento.executeUpdate();
                }
            } else { //insert
                iRipetizione.setString(1, ripetizione.getLuogoIncontro());
                iRipetizione.setInt(2, ripetizione.getCosto());
                iRipetizione.setString(3, ripetizione.getDescr());
                iRipetizione.setString(4, ripetizione.getCittà());
                iRipetizione.setInt(5, ripetizione.getTutor_key());
                iRipetizione.setInt(6, ripetizione.getMateria_key());
               
                
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
            if(!ripetizione.isDirty()){
                List<Argument> list = ripetizione.getArgomenti();
                if(list.size() > 0 && !ripetizione.isDirty()){
                    for(Argument a : list){
                        iRipetizioneHasArgomento.setInt(1,key);
                        iRipetizioneHasArgomento.setInt(2,a.getMateria_key());
                        iRipetizioneHasArgomento.setInt(3,a.getKey());
                        if(iRipetizioneHasArgomento.executeUpdate()==1){
                         int x;
                        }
                    }    
               
                }
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
            throw new DataLayerException("Unable to store article", ex);
        }
    }
}
