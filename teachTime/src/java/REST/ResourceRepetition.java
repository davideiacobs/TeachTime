/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Repetition;
import classes.TeachTimeDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author iacobs
 */
@Path("repetitions")
public class ResourceRepetition {
    
    @Resource(name = "jdbc/teachtime")
    private DataSource ds;
     
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response postRepetition(@Context UriInfo c, Repetition ripetizione) throws SQLException, NamingException, DataLayerException {
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        datalayer.storeRepetition(ripetizione);
        
        URI u = c.getAbsolutePath();
        
        return Response.created(u).build();
    }  
}
