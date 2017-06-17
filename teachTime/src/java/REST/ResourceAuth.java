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
    
    //testato
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAuth( User u) throws SQLException, NamingException, DataLayerException{
       
        User utente = datalayer.getUtenteByMail(u.getEmail());
        if(utente!=null){
            String isLogged = datalayer.getTokenByUtente(utente.getKey());
            if(isLogged !=null && !isLogged.equals("")){
                return Response.serverError().build(); 
            }
            if(utente.getPwd().equals(u.getPwd())){
                Session session = new Session(); 
                session.setUtente(utente);
                session.setToken(Utility.generateToken());
                datalayer.storeSessione(session);
                return Response.ok(session.getToken()).build();
            } else {
                return Response.serverError().build();
            }     
        }else{
            return Response.serverError().build();
        }
    }
    
    //testato
    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAuthDestroy(String token) throws SQLException, NamingException, DataLayerException{
        datalayer.deleteSessione(token);
        return Response.noContent().build();   
    }
    
    //testato
    @Path("{SID: [0-9]+}/users")
    public ResourceUser toResourceUser() throws SQLException, NamingException, DataLayerException {
        
        return new ResourceUser();
    }
    
    @Path("{SID: [0-9]+}/privateLessons")
    public ResourcePrivateLesson toResourcePrivateLesson() throws DataLayerException, SQLException, NamingException{
        
        return new ResourcePrivateLesson();
    }
    
}
