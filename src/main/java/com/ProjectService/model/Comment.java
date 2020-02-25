package com.ProjectService.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	LocalDateTime creationTime;
	String content;
	int rating;
	@OneToOne
	Usero author;

	
	
	public Comment() {
		super();
	}

	public Comment(String content, int rating, Usero author) {
		super();
		this.creationTime = LocalDateTime.now();
		this.content = content;
		this.rating = rating;
		this.author = author;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", creationTime=" + creationTime + ", content=" + content + ", rating=" + rating + ", author=" + author + "]";
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

	public void setContent(String content) {
		this.content = content;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Usero getAuthor() {
		return author;
	}

	public void setAuthor(Usero author) {
		this.author = author;
	}

}
