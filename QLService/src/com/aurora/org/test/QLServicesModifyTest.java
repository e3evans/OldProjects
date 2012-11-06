package com.aurora.org.test;

import org.junit.Test;

import com.aurora.org.rest.services.QLModify;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class QLServicesModifyTest {
	
	@Test
	public void checkModify(){
		QLModify qlModify = mock(QLModify.class);
		when(qlModify.test()).thenReturn("modify");
		String result = qlModify.test();
		assertEquals("modify",result);
	}

}
