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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.j4g.packet.domain.PacketNeed;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Needs {

	private final String name;

	private final String address;

	private String desc;

	private LocalDateTime arrivedBestTime;

	private List<PacketNeeds> packets = new ArrayList<PacketNeeds>();

	@JsonCreator
	public Needs(@JsonProperty("name") String name, @JsonProperty("address") String address, LocalDateTime arrivedBestTime) {
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(address, "Model must not be null");
		this.name = name;
		this.address = address;
		this.arrivedBestTime = arrivedBestTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public LocalDateTime getArrivedBestTime() {
		return arrivedBestTime;
	}

	public void setArrivedBestTime(LocalDateTime arrivedBestTime) {
		this.arrivedBestTime = arrivedBestTime;
	}

	public void convertAndAddNeed(PacketNeed pn) {
		PacketNeeds.Packets packets = new PacketNeeds.Packets(pn.getPacket().getSize().toString(), pn.getPacket().getType().toString());
		pn.getPacket().getDetails().forEach(pd -> {			
			PacketNeeds.Packets.PacketDetails packetDetails = 
					new PacketNeeds.Packets.PacketDetails(pd.getCount(), new 
							PacketNeeds.Packets.PacketDetails.Products(pd.getProduct().getNo(), pd.getProduct().getName(), pd.getProduct().getBrand(), pd.getProduct().getDesc()));
			packets.addPacketDetails(packetDetails);
		});
		PacketNeeds packetNeeds = new PacketNeeds(pn.getCount(), packets);
		this.packets.add(packetNeeds);
	}
	
	public static class PacketNeeds implements Serializable {
 
		private static final long serialVersionUID = 1L;
		private final int count;
		private Packets packets;

		public PacketNeeds(int count, Packets packets) {
			this.count = count;
			this.packets = packets;
		}

		public int getCount() {
			return count;
		}

		public Packets getPackets() {
			return packets;
		}

		public static class Packets {

			private final String type;
			private final String size;
			private List<PacketDetails> details = new ArrayList<PacketDetails>();

			public Packets(String type, String size) {
				this.type = type;
				this.size = size;
			}

			public void addPacketDetails(PacketDetails pd) {
				this.details.add(pd);
			}

			public String getType() {
				return type;
			}

			public String getSize() {
				return size;
			}

			public List<PacketDetails> getDetails() {
				return details;
			}

			public static class PacketDetails {

				private final int count;
				private final Products products;

				public PacketDetails(int count, Products products) {
					this.products = products;
					this.count = count;
				}

				public int getCount() {
					return count;
				}

				public Products getProducts() {
					return products;
				}

				public static class Products {
					private final String name;
					private final String desc;
					private final String no;
					private final String brand;

					public Products(String no, String name, String brand, String desc) {
						this.no = no;
						this.name = name;
						this.brand = brand;
						this.desc = desc;
					}

					public String getName() {
						return name;
					}

					public String getDesc() {
						return desc;
					}

					public String getNo() {
						return no;
					}

					public String getBrand() {
						return brand;
					}
				}
			}
		}
	}
}
