package h.cn.baidu.com.MultiFieldQueryParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;
//import org.apache.lucene.search.NumericRangeQuery;

public class TestMultiFieldQueryParser {

	@Test
	public void booleanQuery() throws Exception {

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

		// 6.创建查询条件对象 指定默认搜索的域 并加载分词器
		
		String[] fields = {"name","description"};
		MultiFieldQueryParser mult = new MultiFieldQueryParser(fields , an);

		Query query = mult.parse("Lucene");
		
		TopDocs topDocs = is.search(query, 100);
		
		
		// 9.获取查询结果中的数据
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docID = scoreDoc.doc;// 获取当前文档ID
			Document doc = is.doc(docID); // 获取当前的document文档对象
			System.out.println(doc.get("id"));
			System.out.println(doc.get("name"));
			System.out.println(doc.get("price"));
			System.out.println(doc.get("pic"));
			System.out.println(doc.get("description"));
			System.out.println("---------------------------------------------------------");
		}
		System.out.println("查询完成");

		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.out.println(dateFormat.format(d) + "修改完毕!");

	}

}
