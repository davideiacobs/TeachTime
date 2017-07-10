/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Category;
import classes.Subject;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author iacobs
 */
@Path("categories")
public class ResourceSubject extends TeachTimeDataLayerSupplier{
    
    public ResourceSubject()throws SQLException, NamingException, DataLayerException{
        super();
    }
    
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() throws DataLayerException{
        
        //recuepero lista materie per id categoria
        List<Category> list = datalayer.getCategorie();
        datalayer.destroy();
        if(list!=null){
            return Response.ok(list).build();
        }
        return Response.serverError().build();
    }
    
    
    @GET
    @Path("{id: [0-9]+}/subjects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArgList(@PathParam("id") int n) throws SQLException, NamingException, DataLayerException{
        
        //recuepero lista materie per id categoria
        List<Subject> list = datalayer.getMaterieByCategoria(n);
        datalayer.destroy();
        if(list!=null){
            return Response.ok(list).build();
        }
        return Response.serverError().build();
    }
    
  }