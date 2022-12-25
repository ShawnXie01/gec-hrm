package com.situ2001.hrm.dao;

import java.util.List;
import com.situ2001.hrm.pojo.Document;

public interface DocumentDao {
    List<Document> documentList(int page, int limit, Document entity);
    int count();
    int add(Document document);
}
