package com.dev.anh.job.model.entity;

import java.util.List;
import java.util.Set;
import com.dev.anh.job.model.consts.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Applicant extends AbstractEntity{

	@Id
	@Column(name = "applicant_id")
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "applicant_id")
	private Account account;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;
		
	@Column(columnDefinition = "TEXT")
	private String professionalSummary;
		
	@Column(nullable = false)
	@Size(min = 10, max = 100, message = "Contact details must be between 10 and 200")
	private String contactDetail;
	
	@Column(nullable = false)
	@Size(min = 10, max = 200, message = "Address must be between 10 and 200")
	private String address;
		
	@OneToMany(mappedBy = "applicant")
	private List<JobApply> jobApply;
	
	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Experience> experiences;
	
	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SocialLink> links;
	
	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Education> educations;
	
	@ManyToMany
	@JoinTable(
		name="applicant_skills",
		joinColumns = @JoinColumn(name = "applicant_id"),
		inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private Set<Skill> skills;
	
	@ManyToMany
	@JoinTable(
		name="applicant_languages",
		joinColumns = @JoinColumn(name = "applicant_id"),
		inverseJoinColumns = @JoinColumn(name = "language_id"))
	private Set<Language> languages;
	
	@ManyToMany
	@JoinTable(
			name="applicant_career_roles",
			joinColumns = @JoinColumn(name = "applicant_id"),
			inverseJoinColumns = @JoinColumn(name = "career_role_id"))
	private Set<CareerRole> careerRoles;
	
	private String profilePhoto;
	
	private String resume;
	
	private String cvForm;
	
	private boolean deleted = false;
}
