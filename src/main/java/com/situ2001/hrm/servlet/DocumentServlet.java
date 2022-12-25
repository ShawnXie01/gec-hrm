package com.situ2001.hrm.servlet;

import com.google.gson.Gson;
import com.situ2001.hrm.dao.DocumentDao;
import com.situ2001.hrm.dao.impl.DocumentDaoImpl;
import com.situ2001.hrm.pojo.Document;
import com.situ2001.hrm.pojo.R;
import com.situ2001.hrm.pojo.User;
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
import java.util.List;

@WebServlet(urlPatterns = {"/documentList.action", "/getAllDocument.action", "/upload.action", "/addDocument.action", "/updDocument.action", "/delDocument.action", "/delDocuments.action"})
public class DocumentServlet extends HttpServlet {
    public static final long serialVersionUID = 1L;
    private DocumentDao documentDao = new DocumentDaoImpl();

    private String initAndGetAction(HttpServletRequest req, HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        var uri = req.getRequestURI();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var action = initAndGetAction(req, resp);
        if (action.equals("documentList.action")) {
            documentList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var action = initAndGetAction(req, resp);
        if (action.equals("upload.action")) {
            upload(req, resp);
        } else if (action.equals("addDocument.action")) {
            addDocument(req, resp);
        } else if (action.equals("updDocument.action")) {
            updDocument(req, resp);
        } else if (action.equals("delDocument.action")) {
            delDocument(req, resp);
        } else if (action.equals("delDocuments.action")) {
            delDocuments(req, resp);
        }
    }

    private void deleteFileIfExist(String filepath) {
        var file = new File(filepath);
        if (file.exists()) {
            file.delete();
        }
    }

    private void delDocument(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var id = Integer.parseInt(req.getParameter("id"));
        var documents = documentDao.findById(req.getParameter("id"));
        if (documentDao.delete(id) > 0) {
            var baseDirPath = req.getSession().getServletContext().getRealPath("/files");
            for (var document : documents) {
                deleteFileIfExist(baseDirPath + "/" + document.getFileName());
            }
            resp.getWriter().print(1);
        } else {
            resp.getWriter().print(0);
        }
    }

    private void delDocuments(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var idsString = req.getParameter("ids");
        var documents = documentDao.findById(idsString);
        if (documentDao.deleteMany(idsString) > 0) {
            var baseDirPath = req.getSession().getServletContext().getRealPath("/files");
            for (var document : documents) {
                deleteFileIfExist(baseDirPath + "/" + document.getFileName());
            }
            resp.getWriter().print(1);
        } else {
            resp.getWriter().print(0);
        }
    }

    private void addDocument(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var out = resp.getWriter();
        // get data from form
        var content = req.getParameter("content");
        var title = req.getParameter("title");
        var filename = req.getParameter("filename");
        var filetype = filename.substring(filename.lastIndexOf(".") + 1);
        // get user data
        var session = req.getSession();
        User user = (User) session.getAttribute("user");
        var userId = user.getId();
        // push them into an object
        var document = new Document();
        document.setTitle(title);
        document.setRemark(content);
        document.setFileName(filename);
        document.setFileType(filetype);
        document.setUserId(userId);
        // then invoke dao with this document object
        var result = documentDao.add(document);
        if (result > 0) {
            out.print(1);
        } else {
            out.print(0);
        }
    }

    private void updDocument(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var out = resp.getWriter();
        // get data from form
        var id = Integer.parseInt(req.getParameter("id"));
        var remark = req.getParameter("remark");
        var title = req.getParameter("title");
        var filename = req.getParameter("filename"); // TODO should I change the name of file if name is changed?
        var filetype = filename.substring(filename.lastIndexOf(".") + 1);
        // put them into an object
        var document = new Document();
        document.setId(id);
        document.setTitle(title);
        document.setRemark(remark);
        document.setFileName(filename);
        // then invoke dao with this document object
        var result = documentDao.update(document);
        if (result > 0) {
            out.print(1);
        } else {
            out.print(0);
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

    private void documentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var out = response.getWriter();
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String title = request.getParameter("title");
        Document document = new Document();
        document.setTitle(title);

        List<Document> documents = documentDao.documentList(page, limit, document);
        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", documents);
        r.put("count", documentDao.count());
        r.put("code", 0);
        out.print(new Gson().toJson(r));
    }
}
