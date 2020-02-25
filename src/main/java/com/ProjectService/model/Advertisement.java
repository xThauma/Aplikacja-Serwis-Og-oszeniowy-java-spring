package com.ProjectService.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Advertisement {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	LocalDateTime creationTime;
	String title;
	@Lob
	String content;
	@OneToOne
	Usero author;
	@OneToOne
	Category category;
	boolean isAccepted;
	int nolikes;
	@OneToMany
	List<Comment> comments;
	
	
	public Advertisement(String title, String content) {
		super();
		this.creationTime = LocalDateTime.now();
		this.title = title;
		this.content = content;
		this.nolikes = 0;
		this.isAccepted = false;
		this.comments = new ArrayList<>();
	}
	
	public Advertisement(String title, String content, Usero author) {
		super();
		this.creationTime = LocalDateTime.now();
		this.title = title;
		this.content = content;
		this.author = author;
		this.nolikes = 0;
		this.isAccepted = false;
		this.comments = new ArrayList<>();
	}

	public Advertisement(String title, String content, Usero author, int nolikes) {
		super();
		this.creationTime = LocalDateTime.now();
		this.title = title;
		this.content = content;
		this.author = author;
		this.isAccepted = false;
		this.nolikes = nolikes;
		this.comments = new ArrayList<>();
	}
	
	
	
	
	
	public Advertisement(String title, String content, Usero author, Category category) {
		super();
		this.creationTime = LocalDateTime.now();
		this.title = title;
		this.content = content;
		this.author = author;
		this.category = category;
		this.isAccepted = false;
		this.comments = new ArrayList<>();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void addComment(Comment c) {
		comments.add(c);
	}

	public List<Comment> getComments() {
		return comments;
	}



	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}



	public Advertisement() {
		super();
	}

	

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	@Override
	public String toString() {
		return "Advertisement [id=" + id + ", creationTime=" + creationTime + ", title=" + title + ", content=" + content + ", author=" + author + ", category=" + category + ", nolikes=" + nolikes
				+ ", comments=" + comments + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String text) {
		this.content = text;
	}

	public Usero getAuthor() {
		return author;
	}

	public void setAuthor(Usero author) {
		this.author = author;
	}

	public int getNolikes() {
		return nolikes;
	}

	public void setNolikes(int nolikes) {
		this.nolikes = nolikes;
	}

}
