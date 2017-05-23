/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     
    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAuthDestroy(Session sessione) throws SQLException, NamingException, DataLayerException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        datalayer.deleteSessione(sessione.getToken());
        
        return Response.noContent().build();
 
        
        
    }
}
