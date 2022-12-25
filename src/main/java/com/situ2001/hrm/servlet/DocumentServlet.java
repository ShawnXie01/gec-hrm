package com.situ2001.hrm.servlet;

import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/documentList.action", "/getAllDocument.action", "/upload.action"})
public class DocumentServlet extends HttpServlet {
    private String initAndGetAction(HttpServletRequest req, HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        var uri = req.getRequestURI();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var action = initAndGetAction(req, resp);
        if (action.equals("upload.action")) {
            upload(req, resp);
        }
    }

    // TODO make it work
    private void upload(HttpServletRequest req, HttpServletResponse resp) {
        boolean isMultiPart = ServletFileUpload.isMultipartContent(req);

        if (isMultiPart) {
            var realPath = req.getSession().getServletContext().getRealPath("/files");
            var dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            try {
                var items = upload.parseRequest(req);
                for (var item : items) {
                    if (item.isFormField()) {
                        var name1 = item.getFieldName();
                        var value = item.getString("UTF-8");
                    } else {
                        var urlString = System.currentTimeMillis() + item.getName().substring(item.getName().lastIndexOf("."));
                        item.write(new File(dir, urlString));
                        var map = new HashMap<String, Object>();
                        map.put("url", urlString);
                        resp.getWriter().print(new Gson().toJson(map));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
