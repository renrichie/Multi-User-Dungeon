package server.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.AccountCollection;

/**
 * Author:   Richie Ren
 * File:     AccountCollectionTest.java
 * Purpose:  This is a unit test for AccountCollection.
 */

public class AccountCollectionTest {

	/**
	 * Method:  testAddAccount() 
	 * Purpose: It tests the addAccount() method. It attempts to add the first account a second time, and should fail to do so.
	 */
	
	@Test
	public void testAddAccount() {
		AccountCollection a = new AccountCollection();
		assertTrue(a.addAccount("test", "123"));
		assertTrue(!a.addAccount("test", "123"));
	}
	
	/**
	 * Method:  testVerifyAccount() 
	 * Purpose: It tests the verifyAccount() method. It returns an object if the account exists, null otherwise.
	 */
	
	@Test
	public void testVerifyAccount() {
		AccountCollection a = new AccountCollection();
		assertTrue(a.addAccount("test", "123"));
		assertTrue(a.verifyAccount("fake", "account") == null);
		assertTrue(a.verifyAccount("test", "123") != null);
	}
}
