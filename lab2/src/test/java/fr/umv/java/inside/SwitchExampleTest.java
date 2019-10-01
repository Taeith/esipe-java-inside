
package fr.umv.java.inside;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SwitchExampleTest {

	@Test
	void testDog() {
		assertEquals(1, SwitchExample.switchExample("dog"));
	}

	@Test
	void testCat() {
		assertEquals(2, SwitchExample.switchExample("cat"));
	}

	@Test
	void testHorse() {
		assertEquals(4, SwitchExample.switchExample("horse"));
	}

	@Test
	void testDog2() {
		assertEquals(1, SwitchExample.switchExample2("dog"));
	}

	@Test
	void testCat2() {
		assertEquals(2, SwitchExample.switchExample2("cat"));
	}

	@Test
	void testHorse2() {
		assertEquals(4, SwitchExample.switchExample2("horse"));
	}

}

