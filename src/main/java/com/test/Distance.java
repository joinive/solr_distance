package com.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class Distance {

	private double dist;

	private static final String sorlServerUrl = "http://127.0.0.1:8080/solr/clue/";

	// 查询
	public void query() throws Exception {
		// 创建HttpSolrServer
        HttpSolrClient solrServer = new HttpSolrClient.Builder(sorlServerUrl).withConnectionTimeout(10000).withSocketTimeout(60000).build();  
		// 创建SolrQuery对象
		SolrQuery query = new SolrQuery();
		// 查询条件
		query.set("q", "*:*");
		query.set("fq", "{!geofilt}"); // 距离过滤器
		query.set("d", "10"); // 距离 单位公里
		query.set("pt", "37.79977,112.591271"); // 参考点坐标
		query.set("sfield", "location"); // 坐标字段
		query.set("start", "0"); // 起始点
		query.set("rows", "10"); // 分页
		query.set("sort", "geodist() asc"); // 排序规则
		query.set("fl", "*,dist:geodist()");// 返回的值*是所有dist及后面的为距离
		// 执行查询并且返回结果
		QueryResponse reponse = solrServer.query(query);
		// 获取匹配返回的结果
		SolrDocumentList list = reponse.getResults();
		// 匹配结果总数
		long count = list.getNumFound();
		System.out.println("匹配结果总数:" + count);
		for (SolrDocument doc : list) {
			System.out.println(doc.get("dist")); // 取距离的方法
		}
	}

	public static void main(String[] args) {

	}
}
