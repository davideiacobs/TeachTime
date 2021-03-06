/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author david
 */
public class UploadFoto extends HttpServlet {

    
     private void upload_foto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        
        Part foto_to_upload = request.getPart("foto-profilo");
        
        //recuperiamo nome img
        String name = request.getParameter("name");
        
        
        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();
        
        out = new FileOutputStream(new File(getServletContext().getInitParameter("foto.directory")+File.separator+name));
        filecontent = foto_to_upload.getInputStream();
        int read = 0;
        final byte[] bytes = new byte[1024];

        while ((read = filecontent.read(bytes)) != -1) {
           out.write(bytes, 0, read);
        }
        
        out.close();
        filecontent.close();
        if (writer != null) {
            writer.close();
        }
        response.setContentType("text/plain"); 
        response.setCharacterEncoding("UTF-8"); 
        try (PrintWriter out2 = response.getWriter()) {
            out2.println(name);
        }
        
        
     
     }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        try {
            upload_foto(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            //action_error(request, response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
