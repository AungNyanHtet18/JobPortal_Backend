package com.dev.anh.job.model.entity;

import java.util.List;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Job extends AbstractEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="job_id")
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="position_name")
	private Career career;
	
	@ManyToOne(optional = false)	
	private Company company;
	
	private Integer jobPost;
	private String clientName;
	
	@Column(nullable = false)
	private String location;
	
	@ElementCollection
    @CollectionTable(name = "job_descriptions", joinColumns = @JoinColumn(name = "job_id"))
    @Column(nullable = false,  columnDefinition = "TEXT")
    @OrderColumn(name = "display_order") //display according to order
    private List<String> jobDescriptions;

    @ElementCollection
    @CollectionTable(name = "job_requirements", joinColumns = @JoinColumn(name = "job_id"))
    @Column(nullable =  false , columnDefinition = "TEXT")
    @OrderColumn(name = "display_order")
    private List<String> jobRequirements;
		
	@Column(nullable =  false)
	@Enumerated(EnumType.STRING)
	private JobLevel jobLevel;
	
	@Column(nullable =  false)
	@Enumerated(EnumType.STRING)
	private JobType jobType;
	
	@Column(nullable = false)
	@Positive(message = "Salary must be greater than 0")
	private Double minSalaryRange;
	
	@Column(nullable = false)
	@Positive(message = "Salary must be greater than 0")
	private Double maxSalaryRange;
			
	@OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<JobApply> jobApply;
	
	private boolean deleted;
	
}
