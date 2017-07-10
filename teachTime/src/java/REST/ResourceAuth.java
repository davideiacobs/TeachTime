package REST;

import classes.Session;
import classes.User;
import classes.Utility;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author iacobs
 */
@Path("auth")
public class ResourceAuth extends TeachTimeDataLayerSupplier{
    
    public ResourceAuth() throws SQLException, NamingException, DataLayerException{
        
        super();
    }
    
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAuth(User u) throws SQLException, NamingException, DataLayerException{
        //recupero utente by mail
        User utente = datalayer.getUtenteByMail(u.getEmail());
        if(utente!=null){
            //se l'utente esiste verifico se è in sessione
            String isLogged = datalayer.getTokenByUtente(utente.getKey());
            if(isLogged !=null && !isLogged.equals("")){
                //se è in sessione ritorno errore
                return Response.ok(isLogged).build(); 
            }
            if(utente.getPwd().equals(u.getPwd())){
                //verifico se la pwd inserita è corretta. Se lo è autentico l'utente
                Session session = new Session(); 
                session.setUtente(utente);
                session.setToken(Utility.generateToken());
                //memorizzo la sessione nel db
                datalayer.storeSessione(session);
                datalayer.destroy();
                return Response.ok(session.getToken()).build();
            } else {
                datalayer.destroy();
                return Response.serverError().build();
            }     
        }else{
            datalayer.destroy();
            return Response.serverError().build();
        }
        
    }
    
    @POST
    @Path("logout")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postAuthDestroy(String token) throws SQLException, NamingException, DataLayerException{
        //cancello la sessione corrispondente al token contenuto nel payload
        int res = datalayer.deleteSessione(token);
        if(res==1){
            //cancellazione avvenuta con successo
            datalayer.destroy();
            return Response.noContent().build();   
        }
        datalayer.destroy();
        return Response.serverError().build();
    }
    

    @Path("{SID: [0-9]+}/users")
    public ResourceUser toResourceUser() throws SQLException, NamingException, DataLayerException {
        //passaggio alla risorsa user
        return new ResourceUser();
    }
    
    @Path("{SID: [0-9]+}/privateLessons")
    public ResourcePrivateLesson toResourcePrivateLesson() throws DataLayerException, SQLException, NamingException{
        //passaggio alla risorsa privateLesson
        return new ResourcePrivateLesson();
    }
    
}
