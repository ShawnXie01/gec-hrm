package com.situ2001.hrm.servlet;

import com.google.gson.Gson;
import com.situ2001.hrm.dao.DocumentDao;
import com.situ2001.hrm.dao.impl.DocumentDaoImp;
import com.situ2001.hrm.pojo.Document;
import com.situ2001.hrm.pojo.R;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet({"/documentList.action"})
public class DocumentServlet extends HttpServlet {
    public static final long serialVersionUID = 1L;
    private DocumentDao documentDao = new DocumentDaoImp();

    private String initAndGetAction(HttpServletRequest req, HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        var uri = req.getRequestURI();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = initAndGetAction(request, response);
        if (action.equals("documentList.action")) {
            documentList(request, response);
        }
    }

    private void documentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var out = response.getWriter();
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String title = request.getParameter("title");
        Document document = new Document();
        document.setTitle(title);

        List<Document> documents = documentDao.documentList(page,limit,document);
        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", documents);
        r.put("count", documentDao.count());
        r.put("code", 0);
        out.print(new Gson().toJson(r));
    }
}
