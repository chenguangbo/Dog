package a.cn.baidu.com.createIndex;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.baidu.dao.BookDao;
import cn.baidu.dao.BookDaoImpl;
import co.baidu.po.Book;

public class CreateIndex {

	@Test
	public void createIndex() throws Exception {
		// 1.获取原始数据
		BookDao bookDao = new BookDaoImpl();
		List<Book> listBook = bookDao.queryListBook();

		// 2.创建文档Document对象
		List<Document> docList = new ArrayList<Document>();
		for (Book book : listBook) {
			Document doc = new Document();
			doc.add(new TextField("id",book.getId() + "",Store.YES));
			doc.add(new TextField("name", book.getName(),Store.YES));
			doc.add(new TextField("price", book.getPrice(),Store.YES));
			doc.add(new TextField("pic", book.getPic(),Store.YES));
			doc.add(new TextField("description",book.getDescription(),Store.YES));
			docList.add(doc);
			
		}
		// 3.创建分词器
		Analyzer an = new IKAnalyzer();
		
		// 4.创建索引创建配置对象  并加载  分词器
		IndexWriterConfig indexWriter = new IndexWriterConfig(an);
		
		// 5.获取索引存储位置
		Path path = Paths.get("d:/lucene");
		Directory directory = FSDirectory.open(path);
		
		// 6.创建索引写入对象      并加载索引保存位置  加载索引写入配置文件进而加载分词器
		IndexWriter wrt = new IndexWriter(directory, indexWriter);
		
		// 7.将数据写入索引库
		
		for (Document doc : docList) {
			wrt.addDocument(doc);
		}
		
		// 8.关闭写入流
		wrt.close();
		
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(dateFormat.format(d) +"创建索引完成!");
		
		
	}

}
