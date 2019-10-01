package fr.umv.java.inside;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Objects;

public class MainTest {

	public class Person {
		private final String firstName;
		private final String lastName;

		public Person(String firstName, String lastName) {
	    	this.firstName = Objects.requireNonNull(firstName);
	    	this.lastName = Objects.requireNonNull(lastName);
		}
	  	
	  	@JSONProperty("first-name")
		public String getFirstName() {
			return firstName;
		}

		@JSONProperty("last-name")
		public String getLastName() {
			return lastName;
		}

	}

	public class Alien {
	  private final String planet;
	  private final int age;

	  public Alien(String planet, int age) {
	    if (age <= 0) {
	      throw new IllegalArgumentException("Too young...");
	    }
	    this.planet = Objects.requireNonNull(planet);
	    this.age = age;
	  }

	  @JSONProperty
	  public String getPlanet() {
	    return planet;
	  }

	  @JSONProperty
	  public int getAge() {
	    return age;
	  }
	  
	}

	@Test
	void testAlien1() {
		Alien alien = new Alien("Earth", 24);
		assertEquals("{ planet : Earth, age : 24 }", Main.toJSON(alien));
	}

	@Test
	void testAlien2() {
		Alien alien = new Alien("Titan", 1589);
		assertEquals("{ planet : Titan, age : 1589 }", Main.toJSON(alien));
	}

	@Test
	void testAlien3() {
		Alien alien = new Alien("Kévin", 8);
		assertEquals("{ planet : Kévin, age : 8 }", Main.toJSON(alien));
	}

	@Test
	void testAlien4() {
		Alien alien = new Alien("LaPlaneteAuNomLePlusLongDeLUnivers", 82);
		assertEquals("{ planet : LaPlaneteAuNomLePlusLongDeLUnivers, age : 82 }", Main.toJSON(alien));
	}

	@Test
	void testAlien5() {
		Alien alien = new Alien("Mars", 1000000000);
		assertEquals("{ planet : Mars, age : 1000000000 }", Main.toJSON(alien));
	}

	@Test
	void testPerson1() {
		Person person = new Person("", "");
		assertEquals("{ first-name : , last-name :  }", Main.toJSON(person));
	}

	@Test
	void testPerson2() {
		Person person = new Person("Kévin", "Martin");
		assertEquals("{ first-name : Kévin, last-name : Martin }", Main.toJSON(person));
	}

	@Test
	void testPerson3() {
		Person person = new Person("Guillaume", "Mathecowitsch");
		assertEquals("{ first-name : Guillaume, last-name : Mathecowitsch }", Main.toJSON(person));
	}

	@Test
	void testPerson4() {
		Person person = new Person("Julien", "Bessodes");
		assertEquals("{ first-name : Julien, last-name : Bessodes }", Main.toJSON(person));
	}

	@Test
	void testPerson5() {
		Person person = new Person("Nicolas", "Baticle");
		assertEquals("{ first-name : Nicolas, last-name : Baticle }", Main.toJSON(person));
	}

}
