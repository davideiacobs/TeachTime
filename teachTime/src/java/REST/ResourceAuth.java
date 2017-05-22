/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Auth;
import classes.TeachTimeDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
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
    public Response getAuth(Auth auth) throws DataLayerException, SQLException, NamingException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        auth.setUtente(datalayer.getUteneByMail(auth.getMail()));
        if(auth.getUtente().getPwd().equals(auth.getPwd())){
            //login
        }else{
            //error
        }
        return Response.ok().build();
    }
}
