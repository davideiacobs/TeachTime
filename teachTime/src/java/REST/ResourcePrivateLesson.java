/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;


import classes.Booking;
import classes.Category;
import classes.PrivateLesson;
import classes.Subject;
import classes.User;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import mailer.Mailer;

/**
 *
 * @author iacobs
 */
@Path("privateLessons")
public class ResourcePrivateLesson extends TeachTimeDataLayerSupplier {
    
    public ResourcePrivateLesson() throws SQLException, NamingException, DataLayerException{
        
        super();
    }

     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response postPrivateLesson(@PathParam("SID") String session_id, @Context UriInfo c, PrivateLesson ripetizione) throws SQLException, NamingException, DataLayerException {
        //inserimento ripetizione nel sistema
        
        //recupero l'utente relativo alla sessione
        int user_key = datalayer.getSessionByToken(session_id).getUtente_key();
        //setto la categoria delle materie
        if(ripetizione.getMaterie()!=null){
            for(Subject m : ripetizione.getMaterie()){
                m.setCategoria_key(ripetizione.getCategoria_key());
            }
            ripetizione.setTutor_key(user_key);
            ripetizione.setDirty(false);
            datalayer.storeRipetizione(ripetizione);
            datalayer.destroy();
            URI u = URI.create(c.getBaseUri().toString()+"privateLessons/"+String.valueOf(ripetizione.getKey()));

            return Response.created(u).build();
        }
        datalayer.destroy();
        return Response.serverError().build();
        
    }  
   
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivateLessons(@PathParam("SID") String session_id, @QueryParam("city") String città, @QueryParam("category") String categoria
            , @QueryParam("subject") String materia, @QueryParam("tutor_key") String tutor_key) throws SQLException, NamingException, DataLayerException {
        //filtro ripetizioni -> 2 casi (loggato e non)
        List<PrivateLesson> result = null;
        int user_key;
        if(session_id!=null){
            //LOGGATO
            user_key = datalayer.getSessionByToken(session_id).getUtente_key();
            if((!"".equals(città) && città != null) || (!"".equals(tutor_key) && tutor_key != null)){
                if(materia != null && !"".equals(materia)){
                    //filtro per città, categoria e materia
                    result = datalayer.getRipetizioniByMateriaLogged(città, Integer.parseInt(materia),user_key);
                }else{
                    if(categoria != null && !"".equals(categoria)){
                        result = datalayer.getRipetizioniByCategoriaLogged(città, Integer.parseInt(categoria),user_key);   
                    }else{
                        //filtro per città
                        result = datalayer.getRipetizioniByCittàLogged(città, user_key); 
                    }
                }
            }else{
                result = datalayer.getRipetizioniByTutor(Integer.parseInt(tutor_key));
            }
        }else{
            //NON LOGGATO
            //filtro per tutor_ID, per città, per città e categoria o per città categoria e materia.
            if((!"".equals(città) && città != null) || (!"".equals(tutor_key) && tutor_key != null)){
                if(materia != null && !"".equals(materia)){
                    //filtro per città, categoria e materia
                    result = datalayer.getRipetizioniByMateria(città, Integer.parseInt(materia));
                }else{
                    if(categoria != null && !"".equals(categoria)){
                        result = datalayer.getRipetizioniByCategoria(città, Integer.parseInt(categoria));   
                    }else{
                        //filtro per città
                        result = datalayer.getRipetizioniByCittà(città); 
                    }
                }
            }else{
                result = datalayer.getRipetizioniByTutor(Integer.parseInt(tutor_key));
            }           
        } 
        if(result!=null){
            for(PrivateLesson r : result){ 
                r.setTutor(datalayer.getUtente(r.getTutor_key()));
                List<Subject> materie = r.getMaterie();
                r.setMaterie(materie);
                r.setCategoria_key(materie.get(0).getCategoria_key());
                Category c = datalayer.getCategoria(r.getCategoria_key());
                r.setCategoria(c);
            }
            datalayer.destroy();
            return Response.ok(result).build();            
        }else {
            datalayer.destroy();
            return Response.serverError().build();
        }
        
    }  

    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivateLessonByKey(@PathParam("id") int n) throws SQLException, NamingException, DataLayerException{
        //recupero ripetizione per id
        
        PrivateLesson r = datalayer.getRipetizione(n);
        r.setTutor(datalayer.getUtente(r.getTutor_key()));
        List<Subject> materie = r.getMaterie();
        r.setMaterie(materie);
        r.setCategoria_key(materie.get(0).getCategoria_key());
        datalayer.destroy();
        if(materie!=null){
            return Response.ok(r).build();
        }
        return Response.serverError().build();
    }
    
    @GET
    @Path("my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyPrivateLessons(@PathParam("SID") String session_id) throws SQLException, NamingException, DataLayerException{
        //recupero ripetizione per id
        int user_key = datalayer.getSessionByToken(session_id).getUtente_key();
        List<PrivateLesson> r = datalayer.getRipetizioniByTutor(user_key);
        datalayer.destroy();
        if(r != null){
            return Response.ok(r).build();
        }
        return Response.serverError().build();
    }
    
    

    @DELETE
    @Path("{id: [0-9]+}")
    public Response deletePrivateLesson(@Context UriInfo c, @PathParam("SID") String token, @PathParam("id") int ripetizione_key) throws SQLException, NamingException, DataLayerException {
        //cancellazione della ripetizione id dal sistema
        if(datalayer.getTokenByUtente(datalayer.getRipetizione(ripetizione_key).getTutor_key()).equals(token)){
            datalayer.deleteRipetizione(ripetizione_key);
            datalayer.destroy();
            return Response.noContent().build();
        }
        datalayer.destroy();
        return Response.serverError().build();
       
    }
    
    @PUT
    @Path("{id: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putPrivateLesson(@PathParam("SID") String token, @PathParam("id") int ripetizione_key, PrivateLesson r) throws SQLException, NamingException, DataLayerException {
        /******NB******
        si DEVONO indicare le materie anche se non ne sono state aggiunte altre
        e si deve indicare la categoria_key 
        ***************/

        //aggiornamento info relative alla ripetizione id
        //recuperiamo la chiave del tutor e verifichiamo se il token di sessione corrisponde
        PrivateLesson c = datalayer.getRipetizione(ripetizione_key);
        int tutor_key = c.getTutor_key();
        if(datalayer.getTokenByUtente(tutor_key).equals(token)){
            r.setDirty(true);
            r.setKey(ripetizione_key);
            r.setTutor_key(tutor_key);            
            datalayer.storeRipetizione(r);
            datalayer.destroy();
            return Response.noContent().build();
        }
        datalayer.destroy();
        return Response.serverError().build();
    }
    
    @Path("{privateLesson_id: [0-9]+}/bookings/{id: [0-9]+}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response putBooking(@PathParam("SID") String token, @PathParam("privateLesson_id") int ripetizione_key, @PathParam("id") int key, @QueryParam("state") String state) throws DataLayerException{
        //permette di accettare o rifiutare 
        int user_key = datalayer.getSessionByToken(token).getUtente_key();
        if(user_key != 0){
            Booking prenotazione = datalayer.getPrenotazione(key);
            prenotazione.setRipetizione_key(ripetizione_key);
            if(state.equals("accetta")){
              prenotazione.setStato(1);
              prenotazione.setRecensione("");
              prenotazione.setVoto(-1);
              prenotazione.setDirty(true);
              datalayer.storePrenotazione(prenotazione);
              
              PrivateLesson b = datalayer.getRipetizione(prenotazione.getRipetizione_key());
              User studente = datalayer.getUtente(prenotazione.getStudente_key());
              User tutor = datalayer.getUtente(b.getTutor_key());
              Subject m = datalayer.getMateria(prenotazione.getMateria_key());
              String obj = "Prenotazione Accettata";
              String txt = "Salve "+studente.getNome()+"!\nLa tua richiesta di prenotazione è stata accettata. Dettaglio:\n"
                            + "materia: "+m.getNome()+";\n"
                            + "per il giorno: "+prenotazione.getData()+";\n"
                            + "città: "+b.getCittà()+";\n"
                            + "luogo di incontro: "+b.getLuogoIncontro()+";\n"
                            + "eventuali informazioni generali: "+prenotazione.getDescr()+".\n\n"
                            + "Di seguito forniamo i dati necessari a contattare il Tutor: \n"
                            + "nome: "+tutor.getNome()+";\n"
                            + "email: "+tutor.getEmail()+";\n"
                            + "telefono: "+tutor.getTelefono()+". \n\n Ti preghiamo di contattarlo per organizzare l'incontro.\n\n"                            
                            + "\nSaluti,\nTeachTime";
               Mailer m1 = new Mailer(studente.getEmail(),obj,txt);
               m1.sendEmail();
              
              datalayer.destroy();
              return Response.ok().build();
            }else{
                datalayer.destroy();
                return Response.serverError().build();
            }
        }else{
            datalayer.destroy();
            return Response.serverError().build();
        }
    }
    
    
    
    @Path("{privateLesson_id: [0-9]+}/bookings")
    public ResourceBooking toResourceBooking() throws SQLException, NamingException, DataLayerException {
        //passaggio alla risorsa bookings che gestisce le prenotazioni
        return new ResourceBooking();
    }
    
}
