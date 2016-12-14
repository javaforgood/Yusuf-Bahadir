package org.j4g.packet.repository;
 

import org.j4g.packet.domain.Need;
import org.j4g.packet.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NeedRepository extends PagingAndSortingRepository<Need, Long> {
  
	Page<Need> findByStatusOrderByArrivedBestTimeAsc(Status status, Pageable pageable);
}
