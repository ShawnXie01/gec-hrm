package com.situ2001.hrm.dao.impl;

import com.situ2001.hrm.dao.DocumentDao;
import com.situ2001.hrm.dao.UserDao;
import com.situ2001.hrm.pojo.Document;
import com.situ2001.hrm.util.FormatStringAsDate;
import com.situ2001.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DocumentDaoImpl extends JDBCUtils<Document> implements DocumentDao {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public List<Document> documentList(int page, int limit, Document entity) {
        String sql = "select * from document_inf where 1=1";
        if (entity.getTitle() != null && !entity.getTitle().equals("")) {
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
            document.setCreateDate(FormatStringAsDate.formart(rs.getString("create_date")));
            return document;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
