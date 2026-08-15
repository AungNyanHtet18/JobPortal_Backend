package com.dev.anh.job.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dev.anh.job.model.entity.AccountFollow;
import com.dev.anh.job.model.entity.AccountFollow_;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.embeddable.AccountFollowPk;
import com.dev.anh.job.model.output.AccountFollowListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountFollowRepo;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.utils.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountFollowService {

	private final AccountFollowRepo accountFollowRepo;
	private final AccountRepo accountRepo;

	public List<AccountFollowListItem> searchFollow(String username) {
		return accountFollowRepo.search(queryFunc(username));
	}
		
	@Transactional
	public ModificationResult<String> follow(String username, Long followingId) {
		var follower = accountRepo.findOneByEmail(username)
				.orElseThrow(() -> new BusinessException("Follower Account with username: %s is not found".formatted(username)));

		if(follower.getId().equals(followingId)) {
		    throw new BusinessException("You cannot follow yourself.");
		}
		
		var following = accountRepo.findById(followingId)
				.orElseThrow(() -> new BusinessException("Following Account with username: %s is not found".formatted(username)));

		if(accountFollowRepo.existsByIdFollowerIdAndFollowingId(follower.getId(), followingId)) {
			 throw new IllegalStateException("You already follow this account");
		}
		
		var accountFollowPk = new AccountFollowPk(follower.getId(), following.getId());
		var accountFollow = new AccountFollow();
		accountFollow.setId(accountFollowPk);
		
		accountFollowRepo.save(accountFollow);
		return new ModificationResult<String>("You followed this account");
	}

	@Transactional
	public ModificationResult<String> unFollow(String username, Long followingId) {
		var follower = accountRepo.findOneByEmail(username)
				.orElseThrow(() -> new BusinessException("Follower Account with username: %s is not found".formatted(username)));

		var following = accountRepo.findById(followingId)
				.orElseThrow(() -> new BusinessException("Following Account with username: %s is not found".formatted(username)));

		if(!accountFollowRepo.existsByIdFollowerIdAndFollowingId(follower.getId(), followingId)) {
			 throw new EntityNotFoundException("You are not following this account");
		}
		
		accountFollowRepo.deleteByIdFollowerIdAndFollowingId(follower.getId(), following.getId());		
		return new ModificationResult<String>("You unfollowed this account");
	}

	public ModificationResult<Boolean> checkFollowStatus(String username, Long followingId) {
		var follower = accountRepo.findOneByEmail(username)
				.orElseThrow(() -> new BusinessException("Follower Account with username: %s is not found".formatted(username)));
		
		var followStatus = accountFollowRepo.existsByIdFollowerIdAndFollowingId(follower.getId(), followingId);
		return new ModificationResult<Boolean>(followStatus);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<AccountFollowListItem>> queryFunc(String username) {
		 return cb -> { 
			var cq = cb.createQuery(AccountFollowListItem.class);
			var root = cq.from(AccountFollow.class);
			
			var follower = root.join(AccountFollow_.follower, JoinType.INNER);
			AccountFollowListItem.select(cq, cb, root);
			
			var param = new ArrayList<Predicate>();
			param.add(cb.equal(cb.lower(follower.get(Account_.email)), username.toLowerCase(Locale.ROOT))); // Locale.ROOT(To make the lowercase conversion independent of the computer/server's language settings.)
			cq.where(param.toArray(size -> new Predicate[size]));
			
			return cq;
		 };
	}

}
