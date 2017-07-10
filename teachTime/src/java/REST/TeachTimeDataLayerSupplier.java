/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.TeachTimeDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author david
 */
public class TeachTimeDataLayerSupplier {

    TeachTimeDataLayer datalayer;

    public TeachTimeDataLayerSupplier() throws SQLException, NamingException, DataLayerException{
        
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/teachtime");
        datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
    }
}
