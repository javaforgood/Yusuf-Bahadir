package org.j4g.packet.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.j4g.packet.config.TestConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.javafaker.Faker;

/**
 * Data JPA tests for {@link PacketDetail}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestConfig.class)
@Transactional
public class ProductEntityTests {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void createWhenNoIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("No must not be null");
		new Product(null, new Faker().shakespeare().hamletQuote());
	}
	
	@Test
	public void createWhenNameIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Name must not be null");
		new Product(new Faker().idNumber().valid(), null);
	}
	
	@Test
	public void saveShouldPersistData() throws Exception { 
		String no = new Faker().idNumber().valid();
		String name = new Faker().shakespeare().romeoAndJulietQuote();
		Product shampoo = this.entityManager.persistFlushFind(new Product(no, name)); 
		assertNotNull(shampoo.getNo());
		assertNotNull(shampoo.getName());  
		assertThat(shampoo.getNo()).isEqualTo(no);
		assertThat(shampoo.getName()).isEqualTo(name);
	}
	
	@Test
	public void updateShouldMergeData() throws Exception {
		String no = new Faker().idNumber().valid();
		String name = new Faker().shakespeare().romeoAndJulietQuote();
		Product shampoo = this.entityManager.persistFlushFind(new Product(no, name)); 
		assertNotNull(shampoo.getNo());
		assertNotNull(shampoo.getName());  
		assertThat(shampoo.getNo()).isEqualTo(no);
		assertThat(shampoo.getName()).isEqualTo(name);
		
		name = new Faker().shakespeare().romeoAndJulietQuote();
		shampoo.setName(name);
		shampoo = this.entityManager.merge(shampoo);
		shampoo = this.entityManager.find(Product.class, shampoo.getNo());
		
		assertThat(shampoo.getName()).isEqualTo(name);  
		
	}
	
	@Test
	public void deleteShouldRemoveData() throws Exception {
		String no = new Faker().idNumber().valid();
		String name = new Faker().shakespeare().romeoAndJulietQuote();
		Product shampoo = this.entityManager.persistFlushFind(new Product(no, name)); 
		assertNotNull(shampoo.getNo());
		assertNotNull(shampoo.getName());  
		assertThat(shampoo.getNo()).isEqualTo(no);
		assertThat(shampoo.getName()).isEqualTo(name);
		
	 
		this.entityManager.remove(shampoo);
		shampoo = this.entityManager.find(Product.class, shampoo.getNo());
		
		assertNull(shampoo);			
	} 
	
	@Test
	public void saveWhenNameIsSameShouldThrownException() throws Exception {
		this.thrown.expect(PersistenceException.class); 
		String name = new Faker().shakespeare().romeoAndJulietQuote();
		Product shampoo1 = new Product(new Faker().idNumber().valid(), name);
		Product shampoo2 = new Product(new Faker().idNumber().valid(), name);
		this.entityManager.persistFlushFind(shampoo1); 
		this.entityManager.persistFlushFind(shampoo2);  
	}
}
