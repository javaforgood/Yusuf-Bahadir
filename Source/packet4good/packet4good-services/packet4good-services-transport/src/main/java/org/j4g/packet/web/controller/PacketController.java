package org.j4g.packet.web.controller;

import org.j4g.packet.service.Needs;
import org.j4g.packet.service.PacketNeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacketController {

	@Autowired
	private PacketNeedsService packetNeedsService;

	@RequestMapping("/packet")
	public ResponseEntity<PagedResources<Resource<Needs>>> list(Pageable pageable, PagedResourcesAssembler<Needs> pagedResourcesAssembler) {
		Page<Needs> needs = packetNeedsService.getPacketNeeds(pageable);
		return new ResponseEntity<>(pagedResourcesAssembler.toResource(needs), HttpStatus.OK);
	}
}
