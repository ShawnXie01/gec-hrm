package com.situ2001.hrm.dao.impl;

import com.situ2001.hrm.dao.DocumentDao;
import com.situ2001.hrm.dao.UserDao;
import com.situ2001.hrm.pojo.Document;
import com.situ2001.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DocumentDaoImpl extends JDBCUtils<Document> implements DocumentDao {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public List<Document> documentList(int page, int limit, Document entity) {
        String sql = "select * from document_inf where 1=1";
        if (entity.getTitle() != null && entity.getTitle().equals("")) {
            sql += " and title like '%" + entity.getTitle() + "%'";
        }
        sql += " limit " + (page - 1) * limit + "," + limit + "";
        return query(sql);
    }

    @Override
    public int count() {
        return query("select * from document_inf").size();
    }

    @Override
    public int add(Document document) {
        return update("insert into document_inf values(null,?,?,?,X'',?,sysdate(),?)", document.getTitle(), document.getFileName(), document.getFileType(), document.getRemark(), document.getUserId());
    }

    @Override
    public int update(Document document) {
        var id = document.getId();
        var title = document.getTitle();
        var remark = document.getRemark();
        var filename = document.getFileName();
        return update("update document_inf set title=?,remark=?,filename=? where id=?", title, remark, filename, id);
    }

    @Override
    public Document getBean(ResultSet rs) {
        try {
            Document document = new Document();
            document.setId(rs.getInt("id"));
            document.setTitle(rs.getString("title"));
            document.setRemark(rs.getString("remark"));
            document.setFileName(rs.getString("fileName"));
            document.setFileType(rs.getString("fileType"));
            document.setUserId(rs.getInt("user_id"));
            document.setUserName(userDao.findById(rs.getInt("user_id")).getLoginname());
            return document;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
