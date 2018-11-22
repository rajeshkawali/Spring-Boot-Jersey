package com.concretepage.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.concretepage.entity.Article;

public class JerseyClient {
	public void getArticleDetails() {
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target("http://localhost:8080/spring-app/article");
		WebTarget details = base.path("details");
		List<Article> list = details.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Article>>() {});
		
	    list.stream().forEach(article -> 
	        System.out.println(article.getArticleId()+", "+ article.getTitle()+", "+ article.getCategory()));
	    
	    client.close();
	}
	public void getArticleById(int articleId) {
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target("http://localhost:8080/spring-app/article");
		WebTarget articleById = base.path("{id}").resolveTemplate("id", articleId);
		Article article = articleById.request(MediaType.APPLICATION_JSON)
				.get(Article.class);
		
        System.out.println(article.getArticleId()+", "+ article.getTitle()+", "+ article.getCategory());
        
	    client.close();
	}
	public void addArticle(Article article) {
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target("http://localhost:8080/spring-app/article");
		WebTarget add = base.path("add");
		Response response = add.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(article));
		
		System.out.println("Response Http Status: "+ response.getStatus());
        System.out.println(response.getLocation());
        
	    client.close();
	}
	public void updateArticle(Article article) {
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target("http://localhost:8080/spring-app/article");
		WebTarget update = base.path("update");
		Response response = update.request(MediaType.APPLICATION_JSON)
				.put(Entity.json(article));
		
		System.out.println("Response Http Status: "+ response.getStatus());
		Article resArticle = response.readEntity(Article.class);
		System.out.println(resArticle.getArticleId()+", "+ resArticle.getTitle()+", "+ resArticle.getCategory());
        
	    client.close();
	}
	public void deleteArticle(int articleId) {
		Client client = ClientBuilder.newClient();
		WebTarget base = client.target("http://localhost:8080/spring-app/article");
		WebTarget deleteById = base.path("{id}").resolveTemplate("id", articleId);
		Response response = deleteById.request(MediaType.APPLICATION_JSON)
				.delete();
		
		System.out.println("Response Http Status: "+ response.getStatus());
		if(response.getStatus() == 204) {
			System.out.println("Data deleted successfully.");
		}
        
	    client.close();
	}	
	public static void main(String[] args) {
		JerseyClient jerseyClient = new JerseyClient();
	    jerseyClient.getArticleDetails();
		//jerseyClient.getArticleById(102);
		
		Article article = new Article();
		article.setTitle("Spring REST Security using Hibernate2");
		article.setCategory("Spring"); 
		//jerseyClient.addArticle(article);
		
		article.setArticleId(105);
		//jerseyClient.updateArticle(article);
		
		//jerseyClient.deleteArticle(105);
	}
}
