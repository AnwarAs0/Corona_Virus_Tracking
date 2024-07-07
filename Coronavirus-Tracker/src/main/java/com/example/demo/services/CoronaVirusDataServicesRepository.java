package com.example.demo.services;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.LocationStates;

public interface CoronaVirusDataServicesRepository extends JpaRepository<LocationStates, Integer> {
    
	@Query(value = "SELECT * FROM LOCATION_STATES ORDER BY LATEST_TOTAL_DEATHS DESC LIMIT :count", nativeQuery = true)
    List<LocationStates> findTopCountriesByLatestTotalDeaths(@Param("count") int count);

}

