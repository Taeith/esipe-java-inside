
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.umlv.java.inside.StringSwitchExample;


public class StringSwitchExampleTests {
	
	@Test
	public void test1() {
		assertAll(
			() -> assertEquals(0, StringSwitchExample.stringSwitch("foo")),
			() -> assertEquals(1, StringSwitchExample.stringSwitch("bar")),
			() -> assertEquals(2, StringSwitchExample.stringSwitch("bazz")),
			() -> assertEquals(-1, StringSwitchExample.stringSwitch("café"))
		);
	}
	
	@ParameterizedTest
	@MethodSource("streamOfMethods")
	public void test2(ToIntFunction<String> fonction) {
		assertAll(
			() -> assertEquals(0, fonction.applyAsInt("foo")),
			() -> assertEquals(1, fonction.applyAsInt("bar")),
			() -> assertEquals(2, fonction.applyAsInt("bazz")),
			() -> assertEquals(-1,fonction.applyAsInt("café"))
		);
	}
	
	static Stream<ToIntFunction<String>> streamOfMethods() {
		ArrayList<ToIntFunction<String>> array = new ArrayList<ToIntFunction<String>>();
		array.add(StringSwitchExample::stringSwitch);
		array.add(StringSwitchExample::stringSwitch2);
		array.add(StringSwitchExample::stringSwitch3);
		return array.stream();
	}
	
}
