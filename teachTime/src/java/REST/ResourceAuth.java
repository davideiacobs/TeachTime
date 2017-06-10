package REST;

import classes.Session;
import classes.TeachTimeDataLayer;
import classes.User;
import classes.Utility;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
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
public class ResourceAuth {
    
    @Resource(name = "jdbc/teachtime")
    private DataSource ds;
    
    //testato
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAuth(User u) throws SQLException, NamingException, DataLayerException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        User utente = datalayer.getUtenteByMail(u.getEmail());
        if(utente != null && utente.getPwd().equals(u.getPwd())){
            Session session = new Session(); 
            session.setUtente(utente);
            session.setToken(Utility.generateToken());
            datalayer.storeSessione(session);
            return Response.ok(session.getToken()).build();
        } else {
            return Response.serverError().build();
  
        }
    }
     
    //testato
    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAuthDestroy(String token) throws SQLException, NamingException, DataLayerException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        datalayer.deleteSessione(token);
        
        return Response.noContent().build();   
    }
    
    //testato
    @Path("{SID: [0-9]+}/users")
    public ResourceUser toResourceUser() throws SQLException, NamingException, DataLayerException {
        //passaggio alla risorsa bookings che gestisce le prenotazioni
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        return new ResourceUser(datalayer);
    }
    
    @Path("{SID: [0-9]+}/privateLessons")
    public ResourcePrivateLesson toResourcePrivateLesson() throws DataLayerException, SQLException, NamingException{
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        return new ResourcePrivateLesson(datalayer);
    }
    
}
