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
    
    private PreparedStatement uUtente,iUtente, sUtenteByID, sCategoriaByID, uCategoria, iCategoria, uMateria;
    private PreparedStatement iMateria, sMateriaByID, uRipetizione, iRipetizione, sRipetizioneByID;
    private PreparedStatement sMaterieByCategoria, sMaterieByRipetizione, iRipetizioneHasMateria; 
    private PreparedStatement sRipetizioneByTutor, sRipetizioneByCategoria, sRipetizioneByMateria;
    private PreparedStatement dRipetizione,dRipetizioneHasMateria, sTutorByRipetizione, sMateriaByNome;
    private PreparedStatement sPrenotazioneBySuperkey, uPrenotazione, iPrenotazione, sRipetizioneByCittà, sPrenotazioneByKey;
    private PreparedStatement sPrenotazioneByUtente, sFeedbacksByTutor, sVoto, sUtenteByMail, iSessione;
    private PreparedStatement sSessioneById, dSessione, sSessioneByUtente, sSessioneByToken;
    private PreparedStatement sRipetizioniByCittàLogged, sRipetizioniByMateriaLogged, sRipetizioniByCategoriaLogged;
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
            sUtenteByID = connection.prepareStatement("SELECT ID,nome,cognome,email,citta,telefono,data_di_nascita,titolo_di_studio,img_profilo FROM utente WHERE ID=?");
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
            sRipetizioneByTutor = connection.prepareStatement("SELECT ripetizione.* FROM ripetizione WHERE tutor_ID=?");
            sRipetizioneByCategoria = connection.prepareStatement("SELECT r.* FROM ((SELECT materia.ID FROM materia WHERE materia.categoria_ID=?) AS m INNER JOIN ripetizione_has_materia AS rha ON (m.ID = rha.materia_ID) INNER JOIN ripetizione AS r ON (rha.ripetizione_ID = r.ID)) WHERE r.citta=?");      
            sRipetizioniByCategoriaLogged = connection.prepareStatement("SELECT r.* FROM ((SELECT materia.ID FROM materia WHERE materia.categoria_ID=?) AS m INNER JOIN ripetizione_has_materia AS rha ON (m.ID = rha.materia_ID) INNER JOIN ripetizione AS r ON (rha.ripetizione_ID = r.ID)) WHERE r.citta=? AND r.tutor_ID<>?");      
            
            
            sRipetizioneByMateria = connection.prepareStatement("SELECT r.* FROM ((SELECT ripetizione_has_materia.ripetizione_ID FROM ripetizione_has_materia WHERE ripetizione_has_materia.materia_ID=?) AS rha INNER JOIN ripetizione AS r ON (rha.ripetizione_ID = r.ID)) WHERE r.citta=?");
            sRipetizioniByMateriaLogged = connection.prepareStatement("SELECT r.* FROM ((SELECT ripetizione_has_materia.ripetizione_ID FROM ripetizione_has_materia WHERE ripetizione_has_materia.materia_ID=?) AS rha INNER JOIN ripetizione AS r ON (rha.ripetizione_ID = r.ID)) WHERE r.citta=? AND r.tutor_ID<>?");
            
            
            dRipetizione = connection.prepareStatement("DELETE FROM ripetizione WHERE ID=?");
            dRipetizioneHasMateria = connection.prepareStatement("DELETE FROM ripetizione_has_materia WHERE ripetizione_ID=?");
            sTutorByRipetizione = connection.prepareStatement("SELECT ripetizione.tutor_ID FROM ripetizione WHERE ID=?");
            
            sPrenotazioneByKey = connection.prepareStatement("SELECT * FROM prenotazione WHERE ID=?");
            sPrenotazioneBySuperkey = connection.prepareStatement("SELECT * FROM prenotazione WHERE ripetizione_ID=? AND studente_ID=? AND materia_ID=? AND data=?");
            //uPrenotazione = connection.prepareStatement("UPDATE prenotazione SET stato=?, voto=?, recensione=? WHERE ripetizione_ID=? AND studente_ID=? AND materia_ID=?");
            uPrenotazione = connection.prepareStatement("UPDATE prenotazione SET stato=?, voto=?, recensione=? WHERE ID=?");
            iPrenotazione = connection.prepareStatement("INSERT INTO prenotazione (ripetizione_ID, studente_ID, costo, descrizione, stato, data, materia_ID, materia_categoria_ID) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sRipetizioneByCittà = connection.prepareStatement("SELECT ripetizione.* FROM ripetizione WHERE citta=?");
            sRipetizioniByCittàLogged = connection.prepareStatement("SELECT ripetizione.* FROM ripetizione WHERE citta=? AND tutor_ID<>?");
          
            
            sPrenotazioneByUtente = connection.prepareStatement("SELECT prenotazione.* FROM prenotazione WHERE studente_ID=? AND stato=1 AND data <= CURDATE()");
            
            sFeedbacksByTutor = connection.prepareStatement("SELECT p.* FROM prenotazione AS p INNER JOIN ripetizione AS r ON (p.ripetizione_ID=r.ID) WHERE r.tutor_ID=? AND p.stato=2");
            sVoto = connection.prepareStatement("SELECT AVG(p.voto) AS media FROM prenotazione AS p INNER JOIN ripetizione AS r ON (p.ripetizione_ID = r.ID) WHERE r.tutor_ID=? AND p.voto>0");
            sUtenteByMail = connection.prepareStatement("SELECT utente.* FROM utente WHERE email=?");
            iSessione = connection.prepareStatement("INSERT INTO sessione (token, utente_ID) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
            sSessioneById = connection.prepareStatement("SELECT * FROM sessione WHERE ID=?");
            dSessione = connection.prepareStatement("DELETE FROM sessione WHERE token=?");
            sSessioneByUtente = connection.prepareStatement("SELECT sessione.token FROM sessione INNER JOIN utente ON(sessione.utente_ID = utente.ID) WHERE utente.ID=?");
            sSessioneByToken = connection.prepareStatement("SELECT sessione.* FROM sessione WHERE token=?");
        } catch (SQLException ex) {
            throw new DataLayerException("Error initializing newspaper data layer", ex);
        } 
    }
       
   public PrivateLesson createRipetizione() {
        return new PrivateLesson(this);
    }

    public PrivateLesson createRipetizione(ResultSet rs) throws DataLayerException {
        try {
            PrivateLesson a = new PrivateLesson(this);
            a.setKey(rs.getInt("ID"));
            a.setLuogoIncontro(rs.getString("luogo_incontro"));
            a.setCosto(rs.getInt("costo_per_ora"));
            a.setDescr(rs.getString("descrizione"));
            a.setCittà(rs.getString("citta"));
            a.setTutor_key(rs.getInt("tutor_ID"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create ripetizione object form ResultSet", ex);
        }
    }
    
    public Category createCategoria(){
        return new Category(this);
    }
    
    public Category createCategoria(ResultSet rs) throws DataLayerException{
        try{
            Category a = new Category(this);
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("nome"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create category object form ResultSet", ex);
            }
    }
    
    public Subject createMateria(){
        return new Subject(this);
    }
    
    public Subject createMateria(ResultSet rs) throws  DataLayerException{
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
    
     public Booking createPrenotation(){
        return new Booking(this);
    }
    
    public Booking createPrenotazione(ResultSet rs) throws DataLayerException{
        try{
            Booking a = new Booking(this);
            a.setKey(rs.getInt("ID"));
            a.setStudente_key(rs.getInt("studente_ID"));
            a.setRipetizione_key(rs.getInt("ripetizione_ID"));
            //manca il set tutor key
            a.setDescr(rs.getString("descrizione"));
            a.setStato(rs.getInt("stato"));
            a.setCosto(rs.getInt("costo"));
            GregorianCalendar data = new GregorianCalendar();
            java.sql.Date datasql;
            datasql = rs.getDate("data");
            a.setData(datasql); 
            a.setMateria_key(rs.getInt("materia_ID"));
            a.setVoto(rs.getInt("voto"));
            a.setRecensione(rs.getString("recensione"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create prenotation object from ResultSet",ex);
        }
    }
    
    
    
    public User createUtente(){
        return new User(this);
    }
    
    
     public User createUtente(ResultSet rs, boolean pwd) throws  DataLayerException{
        //pwd indica se la query restituisce la password o meno
        try{
            User a = new User(this);
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("nome"));
            a.setCognome(rs.getString("cognome"));
            a.setEmail(rs.getString("email"));
            if(pwd==true){
                a.setPwd(rs.getString("pwd"));
            }
            a.setCittà(rs.getString("citta"));
            a.setEmail(rs.getString("email"));
            a.setTelefono(rs.getString("telefono"));
            java.sql.Date date;
            date = rs.getDate("data_di_nascita");
            a.setDataDiNascita(date);
            a.setTitoloDiStudi(rs.getString("titolo_di_studio"));
            a.setImgProfilo(rs.getString("img_profilo"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create user object from ResultSet",ex);
        }
    }
    
    public Session createSessione(ResultSet rs) throws DataLayerException {
        try{
            Session a = new Session(this);
            a.setId(rs.getInt("ID"));
            a.setToken(rs.getString("token"));
            a.setUtente_key(rs.getInt("utente_ID"));
            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create session object from ResultSet",ex);
        }
    }
     
   
     public User getUtente(int utente_key) throws DataLayerException {
        boolean pwd = false;
        try {
            sUtenteByID.setInt(1, utente_key); //setta primo parametro query a project_key
            try (ResultSet rs = sUtenteByID.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return createUtente(rs, pwd);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load utente by ID", ex);
        }
        return null;
    }
    
     
    public User getUtenteByMail(String mail) throws DataLayerException{
        boolean pwd = true;
        try {
            sUtenteByMail.setString(1, mail); //setta primo parametro query a project_key
            try (ResultSet rs = sUtenteByMail.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return createUtente(rs, pwd);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load utente by ID", ex);
        }
        return null;
    } 
     
    public Booking getPrenotazione(int ripetizione, int studente, int materia, java.util.Date data) throws DataLayerException{
        try{
            sPrenotazioneBySuperkey.setInt(1, ripetizione);
            sPrenotazioneBySuperkey.setInt(2, studente);
            sPrenotazioneBySuperkey.setInt(3, materia);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(data);
            sPrenotazioneBySuperkey.setString(4, currentTime);
            try(ResultSet rs = sPrenotazioneBySuperkey.executeQuery()){
                if(rs.next()) {
                  return createPrenotazione(rs);
                }
            }
        }catch (SQLException ex) {
            throw new DataLayerException("Unable to load prenotazione by superkey", ex);
        }
        return null;
    }
    
    public Booking getPrenotazione(int prenotazione_key) throws DataLayerException{
        try{
            sPrenotazioneByKey.setInt(1, prenotazione_key);
            try(ResultSet rs = sPrenotazioneByKey.executeQuery()){
                if(rs.next()) {
                  return createPrenotazione(rs);
                }
            }
        }catch (SQLException ex) {
            throw new DataLayerException("Unable to load prenotazione by key", ex);
        }
        return null;
    }
    
    public List<Booking> getPrenotazioneByUtente(int utente_key) throws DataLayerException{
        List<Booking> result = new ArrayList();
        try{
            sPrenotazioneByUtente.setInt(1, utente_key);
            try(ResultSet rs = sPrenotazioneByUtente.executeQuery()){
                while(rs.next()){
                    result.add((Booking) getPrenotazione(rs.getInt("ID")));                
                }
                
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load prenotazioni", ex);
        }
        return result;
    }
     
    public List<Booking> getFeedbacksByTutor(int tutor_key) throws DataLayerException{
        List<Booking> result = new ArrayList();
        try{
            sFeedbacksByTutor.setInt(1, tutor_key);
            try(ResultSet rs = sFeedbacksByTutor.executeQuery()){
                while(rs.next()){
                    result.add((Booking) getPrenotazione(rs.getInt("ID")));
                }
            }
        }catch (SQLException ex) {
            throw new DataLayerException("Unable to load prenotazioni", ex);
        }
        return result;
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
                    return createCategoria(rs);
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
                    return createMateria(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load materia by ID", ex);
        }
        return null;
    }
     
    public Session getSessione(int id) throws DataLayerException {
        try {
            sSessioneById.setInt(1, id); //setta primo parametro query a project_key
            try (ResultSet rs = sSessioneById.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return createSessione(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load materia by ID", ex);
        }
        return null;
    }
    
    public Session getSessionByToken(String token) throws DataLayerException{
        try {
            sSessioneByToken.setString(1, token); //setta primo parametro query a project_key
            try (ResultSet rs = sSessioneByToken.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return createSessione(rs);
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
    
    public PrivateLesson getRipetizione(int ripetizione_key) throws DataLayerException {
        try {
            sRipetizioneByID.setInt(1, ripetizione_key); //setta primo parametro query a project_key
            try (ResultSet rs = sRipetizioneByID.executeQuery()) {
                if (rs.next()) {
                    //notare come utilizziamo il costrutture
                    //"helper" della classe AuthorImpl
                    //per creare rapidamente un'istanza a
                    //partire dal record corrente
                    return createRipetizione(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by ID", ex);
        }
        return null;
    }
    
    public List<PrivateLesson> getRipetizioniByTutor(int tutor_key) throws DataLayerException{
        List<PrivateLesson> result = new ArrayList<>();
        try {
            sRipetizioneByTutor.setInt(1, tutor_key); //setta primo parametro query a project_key
            try (ResultSet rs = sRipetizioneByTutor.executeQuery()) {
                while(rs.next()){
                 result.add((PrivateLesson) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by tutor_ID", ex);
        }
        return result;
    }
    
    
     public List<PrivateLesson> getRipetizioniByCittà(String città) throws DataLayerException{
        List<PrivateLesson> result = new ArrayList<>();
        try {
            sRipetizioneByCittà.setString(1, città); //setta primo parametro query a project_key
            try (ResultSet rs = sRipetizioneByCittà.executeQuery()) {
                while(rs.next()){
                 result.add((PrivateLesson) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by città", ex);
        }
        return result;
    }
    
     public List<PrivateLesson> getRipetizioniByCittàLogged(String città, int user_key) throws DataLayerException{
        List<PrivateLesson> result = new ArrayList<>();
        try {
            sRipetizioniByCittàLogged.setString(1, città); //setta primo parametro query a project_key
            sRipetizioniByCittàLogged.setInt(2, user_key);
            try (ResultSet rs = sRipetizioniByCittàLogged.executeQuery()) {
                while(rs.next()){
                 result.add((PrivateLesson) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by città", ex);
        }
        return result;
    }
    
     
     
    public List<PrivateLesson> getRipetizioniByCategoria(String città, int categoria) throws DataLayerException{
        List<PrivateLesson> result = new ArrayList<>();
        try {
            sRipetizioneByCategoria.setInt(1, categoria);
            sRipetizioneByCategoria.setString(2, città);

            try (ResultSet rs = sRipetizioneByCategoria.executeQuery()) {
                while(rs.next()){
                 result.add((PrivateLesson) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by filter", ex);
        }
        return result;
    }
    
    
    public List<PrivateLesson> getRipetizioniByCategoriaLogged(String città, int categoria, int user_key) throws DataLayerException{
        List<PrivateLesson> result = new ArrayList<>();
        try {
            sRipetizioniByCategoriaLogged.setInt(1, categoria);
            sRipetizioniByCategoriaLogged.setString(2, città);
            sRipetizioniByCategoriaLogged.setInt(3, user_key);

            try (ResultSet rs = sRipetizioniByCategoriaLogged.executeQuery()) {
                while(rs.next()){
                 result.add((PrivateLesson) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by filter", ex);
        }
        return result;
    }
    
    
    
    
    public List<PrivateLesson> getRipetizioniByMateria(String città, int materia) throws DataLayerException{
        List<PrivateLesson> result = new ArrayList<>();
        try {
            sRipetizioneByMateria.setInt(1, materia);
            sRipetizioneByMateria.setString(2, città);

            try (ResultSet rs = sRipetizioneByMateria.executeQuery()) {
                while(rs.next()){
                 result.add((PrivateLesson) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by filter", ex);
        }
        return result;
    }
    
    
    public List<PrivateLesson> getRipetizioniByMateriaLogged(String città, int materia, int user_key) throws DataLayerException{
        List<PrivateLesson> result = new ArrayList<>();
        try {
            sRipetizioniByMateriaLogged.setInt(1, materia);
            sRipetizioniByMateriaLogged.setString(2, città);
            sRipetizioniByMateriaLogged.setInt(3, user_key);
            try (ResultSet rs = sRipetizioniByMateriaLogged.executeQuery()) {
                while(rs.next()){
                 result.add((PrivateLesson) getRipetizione(rs.getInt("ID")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by filter", ex);
        }
        return result;
    }
    

    
    public String getTokenByUtente(int utente_key) throws DataLayerException{
        try{
            sSessioneByUtente.setInt(1, utente_key);
            try(ResultSet rs = sSessioneByUtente.executeQuery()) {
                if(rs.next()){
                    return rs.getString("token");
                }
            }
        }catch (SQLException ex) {
            throw new DataLayerException("Unable to get session token by user_ID", ex);
        }
        return null;
    }
    
    
    public String getVoto(int tutor_key) throws DataLayerException{
        try{
            sVoto.setInt(1,tutor_key);
            try(ResultSet rs = sVoto.executeQuery()){
                if(rs.next()){
                    return rs.getString("media");
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load repetition by filter", ex);
        }
        return null;
    }
    
     public void storeUtente(User utente) throws DataLayerException {
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
                try{
                    sUtenteByMail.setString(1, utente.getEmail());
                    try(ResultSet rs = sUtenteByMail.executeQuery()){
                        if(rs.next()){
                            return;
                        }
                    }
                }catch (SQLException ex) {
                        throw new DataLayerException("Unable to store utente", ex);
                }
                iUtente.setString(1, utente.getNome());
                iUtente.setString(2, utente.getCognome());
                iUtente.setString(3, utente.getEmail());
                iUtente.setString(4, utente.getPwd());
                iUtente.setString(5, utente.getCittà());
                iUtente.setString(6, utente.getTelefono());
                Date sqldate = new Date(utente.getDataDiNascita().getTime());
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
     
     
    public void storeSessione(Session sessione) throws DataLayerException, SQLException {
        int key = sessione.getId();
        if(key>0){
            return;
        }else{
            iSessione.setString(1,sessione.getToken());
            iSessione.setInt(2, sessione.getUtente_key());
            if(iSessione.executeUpdate()==1) {
                try(ResultSet keys = iSessione.getGeneratedKeys()) {
                    if(keys.next()){
                        key = keys.getInt(1);
                    }
                }
            }
        }
        if (key > 0) {
                sessione.copyFrom(getSessione(key));
            }
    } 
     
     
     public void storeRipetizione(PrivateLesson ripetizione) throws DataLayerException {
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
                        iMateria.setInt(2, ripetizione.getCategoria_key());
                        iMateria.executeUpdate();
                        try (ResultSet keys = iMateria.getGeneratedKeys()) {
                            if (keys.next()) {
                                m.setKey(keys.getInt(1));
                            }
                        
                        }
                    }else{
                        m.setKey(rs.getInt("ID"));
                        m.setNome(rs.getString("nome"));
                        m.setCategoria_key(rs.getInt("categoria_ID"));
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
    
    public int deleteSessione(String token) throws DataLayerException {
        try{
            dSessione.setString(1, token);
            if(dSessione.executeUpdate()==1){
                return 1;
            }
        }catch (SQLException ex) {
            throw new DataLayerException("Unable to delete sessione", ex);
        }
        return 0;
    }

     public int storePrenotazione(Booking prenotazione) throws DataLayerException {
        int key = prenotazione.getKey();
        int ripetizione_key = prenotazione.getRipetizione_key();
        int studente_key = prenotazione.getStudente_key();
        int materia_key = prenotazione.getMateria_key();
        int categoria_key = 0;
        if(materia_key > 0){
            categoria_key = getMateria(materia_key).getCategoria_key();
        }
        try {
            if(key>0){
            //sPrenotazioneByKey.setInt(1, key);
            //sPrenotazioneBySuperkey.setInt(1, ripetizione_key);
            //sPrenotazioneBySuperkey.setInt(2, studente_key);
            //sPrenotazioneBySuperkey.setInt(3, materia_key);
            //try(ResultSet rs = sPrenotazioneBySuperkey.executeQuery()){
            /*try(ResultSet rs = sPrenotazioneByKey.executeQuery()){
                if(rs.next()){
                    //la prenotazione esiste già  --> update
                    */
                    if (!prenotazione.isDirty()) {
                        return key;
                    }
              
                    uPrenotazione.setInt(1, prenotazione.getStato());
                    uPrenotazione.setInt(2, prenotazione.getVoto());
                    uPrenotazione.setString(3, prenotazione.getRecensione());
                    //uPrenotazione.setInt(4, ripetizione_key);
                    //uPrenotazione.setInt(5, studente_key);
                    //uPrenotazione.setInt(6, materia_key);
                    uPrenotazione.setInt(4, key);
                    uPrenotazione.executeUpdate();
                }else { //insert
                    sPrenotazioneBySuperkey.setInt(1,ripetizione_key);
                    sPrenotazioneBySuperkey.setInt(2,studente_key);
                    sPrenotazioneBySuperkey.setInt(3,materia_key);
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentTime = sdf.format(prenotazione.getData());
                    sPrenotazioneBySuperkey.setString(4,currentTime);
                    try(ResultSet rs1 = sPrenotazioneBySuperkey.executeQuery()){
                        if(rs1.next()){
                            return 0;
                        }
                    }
                    iPrenotazione.setInt(1, ripetizione_key);
                    iPrenotazione.setInt(2, studente_key);
                    iPrenotazione.setInt(3, getRipetizione(ripetizione_key).getCosto());
                    iPrenotazione.setString(4, prenotazione.getDescr());
                    iPrenotazione.setInt(5, prenotazione.getStato());
                    //java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //String currentTime = sdf.format(prenotazione.getData());
                    iPrenotazione.setString(6, currentTime);
                    iPrenotazione.setInt(7, materia_key);
                    iPrenotazione.setInt(8, categoria_key);
                    if (iPrenotazione.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                        try (ResultSet keys = iPrenotazione.getGeneratedKeys()) {
                            //il valore restituito è un ResultSet con un record
                            //per ciascuna chiave generata (uno solo nel nostro caso)

                            if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            key = keys.getInt(1);
                            
                        }
                    }
                
            }
            if (key > 0) {
                prenotazione.copyFrom(getPrenotazione(key));
            }
            prenotazione.setDirty(false);
            
                
            }}catch (SQLException ex) {
            throw new DataLayerException("Unable to store prenotazione", ex);
        }
        return key;
        }
    
     }
    

