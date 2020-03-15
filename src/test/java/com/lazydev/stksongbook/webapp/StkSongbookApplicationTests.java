package com.lazydev.stksongbook.webapp;

import com.lazydev.stksongbook.webapp.api.restcontroller.*;
import junit.framework.TestSuite;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StkSongbookApplicationTests {

	@Test
	public void contextLoads() {
		final TestSuite suite = new TestSuite("All Tests");

		suite.addTest(new TestSuite(AuthorRestControllerTest.class));
		suite.addTest(new TestSuite(CategoryRestControllerTest.class));
		suite.addTest(new TestSuite(PlaylistRestControllerTest.class));
		suite.addTest(new TestSuite(SongRestControllerTest.class));
		suite.addTest(new TestSuite(TagRestControllerTest.class));
		suite.addTest(new TestSuite(UserRestControllerTest.class));
		suite.addTest(new TestSuite(UserRoleRestControllerTest.class));

		junit.textui.TestRunner.run(suite);
	}

}
