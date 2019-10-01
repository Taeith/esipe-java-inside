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
	  
		public String getFirstName() {
			return firstName;
		}
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

	  public String getPlanet() {
	    return planet;
	  }

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
		Alien alien = new Alien("", 1);
		assertEquals("{ planet : , age : 1 }", Main.toJSON(alien));
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
		assertEquals("{ firstName : , lastName :  }", Main.toJSON(person));
	}

	@Test
	void testPerson2() {
		Person person = new Person("Kévin", "Martin");
		assertEquals("{ firstName : Kévin, lastName : Martin }", Main.toJSON(person));
	}

	@Test
	void testPerson3() {
		Person person = new Person("Guillaume", "Mathecowitsch");
		assertEquals("{ firstName : Guillaume, lastName : Mathecowitsch }", Main.toJSON(person));
	}

	@Test
	void testPerson4() {
		Person person = new Person("Julien", "Bessodes");
		assertEquals("{ firstName : Julien, lastName : Bessodes }", Main.toJSON(person));
	}

	@Test
	void testPerson5() {
		Person person = new Person("Nicolas", "Baticle");
		assertEquals("{ firstName : Nicolas, lastName : Baticle }", Main.toJSON(person));
	}

}
