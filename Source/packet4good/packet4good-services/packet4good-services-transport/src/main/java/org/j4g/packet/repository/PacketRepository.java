package org.j4g.packet.repository;

import org.j4g.packet.domain.Packet;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PacketRepository extends PagingAndSortingRepository<Packet, Long> {
  
}
