package c.cn.baidu.com.update;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Update {

	@Test
	public void update() throws Exception {

		// 1.创建分词器
		Analyzer an = new IKAnalyzer();
		
		// 2.创建索引创建配置对象  并加载  分词器
		IndexWriterConfig indexWriter = new IndexWriterConfig(an);
		
		// 3.获取索引存储位置
		Path path = Paths.get("d:/lucene");
		Directory directory = FSDirectory.open(path);
		
		// 4.创建索引写入对象      并加载索引保存位置  加载索引写入配置文件进而加载分词器
		IndexWriter wrt = new IndexWriter(directory, indexWriter);
		
		// document必须写入全部数据   否则只有刚修改的数据             在索引库中是先将索引库删除,再重新创建索引库
		Document doc = new Document();
		doc.add(new StringField("name","哈哈哈",Store.YES));
		wrt.updateDocument(new Term("id","2"), doc);
		
		wrt.commit();
		// 5.关闭写入流
		wrt.close();
		
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(dateFormat.format(d) +"修改完毕!");
		
		
	}

}
