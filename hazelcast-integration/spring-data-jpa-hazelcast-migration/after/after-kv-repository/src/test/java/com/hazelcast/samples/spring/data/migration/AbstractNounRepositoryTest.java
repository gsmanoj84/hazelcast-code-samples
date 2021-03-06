package com.hazelcast.samples.spring.data.migration;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <P>Test CRUD operations against a Spring repository for {@link Noun}
 * </P>
 * <P><U><B>MIGRATION PATH</B></U></P>
 * <OL>
 * <LI>Duplicate this class from JPA repository, or move to a central module.
 * </LI>
 * </OL>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Object.class})
public abstract class AbstractNounRepositoryTest {

	protected static Noun cat;

	private Logger log;
	private CrudRepository<Noun, Integer> nounRepository;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cat = new Noun();
		cat.setId(1);
		cat.setEnglish("cat");
		cat.setFrench("chat");
		cat.setSpanish("gato");
	}

	public void setUp(CrudRepository<Noun, Integer> arg0, Logger arg1) {
		this.nounRepository = arg0;
		this.log = arg1;
	}

	@Test
	public void curd() {
		assertThat("Empty before", this.nounRepository.count(), equalTo(0L));
		
		this.nounRepository.save(cat);
		
		assertThat("Not empty during", this.nounRepository.count(), equalTo(1L));

		Noun cat2 = this.nounRepository.findOne(cat.getId());
		this.log.info("curd(), read {}", cat2);
		
		assertThat(cat2, not(nullValue()));
		assertThat("System.identityHashCode", System.identityHashCode(cat2), not(equalTo(System.identityHashCode(cat))));
		assertThat(cat2, equalTo(cat));
		
		this.nounRepository.delete(cat.getId());
		
		assertThat("Empty after", this.nounRepository.count(), equalTo(0L));
	}
	
}
