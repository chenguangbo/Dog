package b.cn.baidu.com.search;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Search {

	@Test
	public void search() throws Exception {

		// 1.创建读取路径
		Path path = Paths.get("d:/lucene");

		// 2.打开硬盘索引库
		Directory dir = FSDirectory.open(path);
		
		// 3.读取数据
		IndexReader reader = DirectoryReader.open(dir);
		
		// 4.创建索引搜索对象
		IndexSearcher is = new IndexSearcher(reader);
		
		// 5.创建分词器
		Analyzer an = new IKAnalyzer();
		
		// 6.创建查询条件对象  指定默认搜索的域    并加载分词器
		QueryParser query = new QueryParser("description", an);
 		
		// 7.指定查询条件
		Query quer = query.parse("name:java");
		
		// 8.执行查询获取 前100条数据
		TopDocs topDocs = is.search(quer, 100);
		System.err.println("符合查询条件的总条数:   " + topDocs.totalHits);
		
		// 9.获取查询结果中的数据
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docID = scoreDoc.doc;//获取当前文档ID
			Document doc = is.doc(docID); //获取当前的document文档对象
			System.out.println(doc.get("id"));
			System.out.println(doc.get("name"));
			System.out.println(doc.get("price"));
			System.out.println(doc.get("pic"));
			System.out.println(doc.get("description"));
			System.out.println("---------------------------------------------------------");
		}
		System.out.println("查询完成");
	}

}
