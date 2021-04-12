import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class wordTest {

	@Test
	void test() throws Exception {
		WordOccuranceGUI test = new WordOccuranceGUI();
		File test1 = new File("test1.txt");
		String output = test.wordOccurances(test1);
		assertEquals("1. yes=3\n 2. no=2\n 3. hi=1\n 4. maybe=1\n 5. bye=1\n ", output);
		
		WordOccuranceGUI secondTest = new WordOccuranceGUI();
		File test2 = new File("test2.txt");
		String output2 = secondTest.wordOccurances(test2);
		assertEquals("1. a=2\n 2. test=2\n 3. is=2\n 4. which=1\n 5. this=1\n ", output2);
		
		WordOccuranceGUI thirdTest = new WordOccuranceGUI();
		File test3 = new File("test3.txt");
		String output3 = thirdTest.wordOccurances(test3);
		assertEquals("1. cool=2\n 2. this=2\n 3. is=2\n 4. test=1\n 5. my=1\n ", output3);
		
	}

}
