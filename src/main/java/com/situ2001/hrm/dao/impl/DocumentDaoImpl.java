package com.situ2001.hrm.dao.impl;

import com.situ2001.hrm.dao.DocumentDao;
import com.situ2001.hrm.pojo.Document;
import com.situ2001.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentDaoImpl extends JDBCUtils<Document> implements DocumentDao {

    @Override
    public Document getBean(ResultSet rs) {
        var document = new Document();
        try {
            document.setId(rs.getInt("id"));
            document.setTitle(rs.getString("title"));
            document.setRemark(rs.getString("remark"));
            document.setFilename(rs.getString("filename"));
            document.setFiletype(rs.getString("filetype"));
            document.setCreateDate(rs.getDate("CREATE_DATE"));
            document.setUserId(rs.getInt("USER_ID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
