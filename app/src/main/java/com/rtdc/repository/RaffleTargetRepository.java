package com.rtdc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtdc.model.Raffle;
import com.rtdc.model.RaffleTarget;
import com.rtdc.model.RaffleTargetId;

@Repository
public interface RaffleTargetRepository extends JpaRepository<RaffleTarget, RaffleTargetId>{
	
	List<RaffleTarget> findByRaffle(Raffle raffle);

	int countByRaffle(Raffle raffle);

	List<RaffleTarget> findByRaffleAndWinnerYn(Raffle raffle, String winnerYn);
}
