/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.j4g.packet.service;

import java.util.ArrayList;
import java.util.List;

import org.j4g.packet.domain.Need;
import org.j4g.packet.domain.Status;
import org.j4g.packet.repository.NeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 
 * @author yusuf.bahadir
 *
 */
@Service
public class PacketNeedsService {

	private static final Logger logger = LoggerFactory
			.getLogger(PacketNeedsService.class); 
 
	private final NeedRepository needRepository;
	
	@Autowired
	public PacketNeedsService(NeedRepository needRepository) {
		this.needRepository = needRepository;
	}
  
	public Page<Needs> getPacketNeeds(Pageable pageable) {
		logger.info("Getting packet needs...");
		Page<Need> needs = this.needRepository.findByStatusOrderByArrivedBestTimeAsc(Status.PREPARING, pageable);
		List<Needs> packetNeeds = new ArrayList<Needs>();
		
		needs.forEach(n -> {
			Needs need = new Needs(n.getName(), n.getAddress(), n.getArrivedBestTime());
			n.getPacketNeeds().forEach(p -> {
				need.convertAndAddNeed(p);
			});
			packetNeeds.add(need);
		});
		return new PageImpl<>(packetNeeds, pageable, needs.getTotalElements());
	}

}
