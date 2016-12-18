package tests;

import org.junit.Test;

import model.MoveDirection;

/**
 * Author:   Richie Ren
 * File:     EnumTest.java
 * Purpose:  This is a unit test for Account.
 */

public class EnumTest {

	/**
	 * Method:  testEnums() 
	 * Purpose: It simply calls for the value of the enum.
	 */
	
	@Test
	public void testEnums() {
		MoveDirection.valueOf("EAST");
		MoveDirection.valueOf("NORTH");
		MoveDirection.valueOf("WEST");
		MoveDirection.valueOf("SOUTH");
	}

}
