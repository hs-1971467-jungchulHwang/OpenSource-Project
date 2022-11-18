# Solr 기초적인 검색 방법

### query
public void ~~~() {

	신규문서_색인();

	SolrQuery query = new SolrQuery();
	query.set("q","name:바지"); // 이런식으로 치면 바지 검색 

	QueryResponse response = null;
	try {
		response = solrClient.query(query);
	} catch (SolrServerException | I0Exception e) {
		e.printStackTrace();
	}

	SolrDocumentList docList = response.getResults(); // 바지 검색 결과로 부터 리턴 받은 값을 매핑함.

	Assert.assertEquals(expected: 3, docList.size());
}

예를 들어 결과로 반바지 , 청바지 , 와이드 팬츠 등등 이런식으로 문서가 검색되어진다.
### rows
public void ~~~() {

	신규문서_색인();

	SolrQuery query = new SolrQuery();
	query.setQuery("q","name:바지"); //query.set("q","name:바지");
	query.setRows(2); // 2개의 데이터만 조회 한다는 의미!	

	QueryResponse response = null;
	try {
		response = solrClient.query(query);
	} catch (SolrServerException | I0Exception e) {
		e.printStackTrace();
	}

	//SolrDocumentList docList = response.getResults();
	List<Pants> coffees = response.getBeans(Pants.class); //getBeans(클래스)메서드를 사용하여 Pants라는 이름의 클래스를 정의한 후 Pants 객체 리스트로 검색 결과를 매핑

	Assert.assertEquals(expected: 2, Pants.size());
}

Row값을 2로 설정했기에 2개의 데이터를 조회할 것임.

### sort

public void ~~~() {

	신규문서_색인();

	SolrQuery query = new SolrQuery();
	query.setQuery("q","name:바지"); //query.set("q","name:바지");
	query.setRows(2); 
	query.setSort(field: "price", SolrQuery.ORDER.desc); // 가격이 가장 비싼 순으로 조회
	
	QueryResponse response = null;
	try {
		response = solrClient.query(query);
	} catch (SolrServerException | I0Exception e) {
		e.printStackTrace();
	}

	//SolrDocumentList docList = response.getResults();
	List<Pants> coffees = response.getBeans(Pants.class); //getBeans(클래스)메서드를 사용하여 Pants라는 이름의 클래스를 정의한 후 Pants 객체 리스트로 검색 결과를 매핑

	Assert.assertEquals(expected: 2, Pants.size());
	Assert.assertEquals(expected: "구찌 바지", Pants.get(0).getName()); // 예를 들어 비싼 바지 브랜드가 조회됨
}

### 끝으로
이러한 Solr 에 검색 루씬 개념을 통해 사용자DB와 날씨DB로 최적의 결과 값을 찾을 수 있다.