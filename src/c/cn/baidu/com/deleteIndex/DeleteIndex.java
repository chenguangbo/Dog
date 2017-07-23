package c.cn.baidu.com.deleteIndex;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class DeleteIndex {

	@Test
	public void deleteIndex() throws Exception {
		// 1.创建分词器
		Analyzer an = new IKAnalyzer();
		
		// 2.创建索引创建配置对象  并加载  分词器
		IndexWriterConfig indexWriter = new IndexWriterConfig(an);
		
		// 3.获取索引存储位置
		Path path = Paths.get("d:/lucene");
		Directory directory = FSDirectory.open(path);
		
		// 4.创建索引写入对象      并加载索引保存位置  加载索引写入配置文件进而加载分词器
		IndexWriter wrt = new IndexWriter(directory, indexWriter);
		wrt.deleteAll();
//		wrt.deleteDocuments(new Term("id","4"));
		// 5.关闭写入流
		wrt.close();
		
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(dateFormat.format(d) +"修改完毕!");
		
		
	}

}
