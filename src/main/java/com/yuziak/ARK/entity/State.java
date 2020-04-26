package com.yuziak.ARK.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "states")
public class State {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@CreatedDate
	@Column(name = "time")
	private Date time;
	
	@Column(name = "filling")
	private int filling;
	
	@Column(name = "temperature")
	private int temperature;
	
	@ManyToOne(fetch =FetchType.LAZY, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonBackReference
	@JoinColumn(name="container_id", unique = false, nullable = false, updatable = true)
	private Container container;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getFilling() {
		return filling;
	}

	public void setFilling(int filling) {
		this.filling = filling;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}
	
}
