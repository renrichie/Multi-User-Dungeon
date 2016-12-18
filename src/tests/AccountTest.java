package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Account;

/**
 * Author:   Richie Ren
 * File:     AccountTest.java
 * Purpose:  This is a unit test for Account.
 */

public class AccountTest {


	/**
	 * Method:  testGetUsername() 
	 * Purpose: It tests to see if the object properly returns the account's username.
	 */
	
	@Test
	public void testGetUsername() {
		Account a = new Account("Test", "123");
		assertTrue(a.getUsername().equals("Test"));
	}
	
	/**
	 * Method:  testGetPassword() 
	 * Purpose: It tests to see if the object properly returns the account's password.
	 */
	
	@Test
	public void testGetPassword() {
		Account a = new Account("Test", "123");
		assertTrue(a.verifyPassword("123"));
	}
	
	/**
	 * Method:  testSetAndGetCharacter() 
	 * Purpose: It tests to see if the object properly returns the account's character.
	 */
	
	@Test
	public void testSetAndGetCharacter() {
		Account a = new Account("Test", "123");
		assertTrue(a.getCharacter() != null);
		
		a.setCharacter(null);
		assertTrue(a.getCharacter() == null);
	}
	
	/**
	 * Method:  testUserVerification() 
	 * Purpose: It tests to see if the account recognizes which client is accessing it.
	 */
	
	@Test
	public void testUserVerification() {
		Account a = new Account("MyTest", "12345");
		assertFalse(a.isVerified(Account.NO_VERIFY));
		
		int uv, olduv = -1;
		for (int i = 0; i > -100000; i--)
		{
			uv = a.resetUserVerification();
			assertFalse(a.isVerified(olduv));
			assertTrue(a.isVerified(uv));
			a.clearUserVerification();
			assertFalse(a.isVerified(uv));
			assertFalse(a.isVerified(Account.NO_VERIFY));
			olduv = uv;
		}
		
	}
}
