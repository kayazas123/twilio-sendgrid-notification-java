package com.hardik.ballista.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = -6694650931487076274L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private UUID id;

	@Column(name = "full_name", length = 50)
	private String fullName;

	@Column(name = "email_id", nullable = false, unique = true, length = 50)
	private String emailId;

	@Column(name = "contact_number", nullable = false, unique = true, length = 15)
	private String contactNumber;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@PrePersist
	void setUp() {
		this.id = UUID.randomUUID();
		this.createdAt = LocalDateTime.now();
	}
}
