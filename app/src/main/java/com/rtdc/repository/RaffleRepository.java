package com.rtdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtdc.model.Raffle;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Long>{

	Raffle findByRaffleId(Long raffleId);

}
