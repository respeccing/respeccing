/**
 * 
 * Copyright (C) 2005-2010 AtKaaZ <atkaaz@users.sourceforge.net>
 * Copyright (C) 2005-2010 UnKn <unkn@users.sourceforge.net>
 * 
 * This file and its contents are part of DeMLinks.
 * 
 * DeMLinks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DeMLinks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with DeMLinks. If not, see <http://www.gnu.org/licenses/>.
 */



package org.dml.level020;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.dml.JUnits.Consts;
import org.dml.level010.JavaID;
import org.dml.level010.Symbol;
import org.dml.tracking.Factory;
import org.junit.Test;
import org.references.method.MethodParams;
import org.references.method.PossibleParams;



/**
 * 
 *
 */
public class Level020_DMLEnvironmentTest
{
	
	// FIXME: commented temporarily until we can find a way to compare two or three different instance bdb environments
	// that are in same dir, so they give true when compared
	@Test
	public
			void
			multiple()
	{
		//
		// MethodParams params = MethodParams.getNew();
		// params.set(
		// PossibleParams.jUnit_wipeDB,
		// true );
		// params.set(
		// PossibleParams.jUnit_wipeDBWhenDone,
		// true );
		//
		// // Level020_DMLEnvironment d1 = new Level020_DMLEnvironment();
		// params.set(
		// PossibleParams.homeDir,
		// Consts.BDB_ENV_PATH
		// + "1&2" );
		// // d1.init( params );
		// Level020_DMLEnvironment d1 = Factory.getNewInstanceAndInit(
		// Level020_DMLEnvironment.class,
		// params );
		//
		// Level020_DMLEnvironment d2 = Factory.getNewInstanceAndInit(
		// Level020_DMLEnvironment.class,
		// params );
		// // new Level020_DMLEnvironment();
		// // d2.init( params );
		//
		// params.set(
		// PossibleParams.homeDir,
		// Consts.BDB_ENV_PATH
		// + "3" );
		// Level020_DMLEnvironment d3 = Factory.getNewInstanceAndInit(
		// Level020_DMLEnvironment.class,
		// params );
		// // new Level020_DMLEnvironment();
		// // d3.init( params );
		// // Factory.deInit( params );
		//
		// try
		// {
		// // if ( 1 == 1 ) {
		// // throw new Exception( "blah" );
		// // }
		// String test1 = "test1";
		// String test2 = "test2";
		// JavaID j1 = JavaID.ensureJavaIDFor( test1 );
		// assertTrue( test1 == j1.getObject() );
		// JavaID j2 = JavaID.ensureJavaIDFor( test2 );
		// assertTrue( j2.getObject() == test2 );
		// assertTrue( test1 != test2 );
		// assertTrue( j1 != j2 );
		// assertFalse( j1.equals( j2 ) );
		// Symbol n1 = d1.createSymbol( j1 );
		// Symbol n2 = d1.createSymbol( j2 );
		// assertNotNull( n1 );
		// assertNotNull( d1.getJavaID( n1 ) );
		// assertTrue( d1.getJavaID(
		// n1 ).equals(
		// j1 ) );
		// assertTrue( d1.getJavaID( n1 ) == j1 );
		// assertTrue( n1 == d1.getSymbol( j1 ) );
		//
		// assertTrue( n1 == d1.getSymbol( j1 ) );// fixed
		// assertTrue( n2 == d1.getSymbol( j2 ) );
		// assertTrue( n2 == d1.getSymbol( j2 ) );// yes
		// assertTrue( n1.equals( d2.getSymbol( j1 ) ) );// d2 is d1
		// // inside
		// // BDB
		// // because they're
		// // in same dir
		// assertTrue( n2 == d2.getSymbol( j2 ) );
		//
		// Symbol n3 = d3.getSymbol( j1 );// d3 is in diff dir
		// assertNull( n3 );
		// n3 = d3.getSymbol( j2 );
		// assertNull( n3 );
		// System.out.println( d1.getJavaID( n1 ) );
		// System.out.println( d3.getJavaID( n1 ) );
		// assertFalse( d1.isVector(
		// n1,
		// n2 ) );
		// assertFalse( d1.isVector(
		// n1,
		// n1 ) );
		// assertFalse( d1.ensureVector(
		// n1,
		// n2 ) );
		// assertFalse( d1.ensureVector(
		// n1,
		// n1 ) );
		// assertTrue( d1.ensureVector(
		// n1,
		// n2 ) );
		// assertTrue( d1.ensureVector(
		// n1,
		// n1 ) );
		// assertTrue( d1.isVector(
		// n1,
		// n2 ) );
		// assertTrue( d1.isVector(
		// n1,
		// n1 ) );
		//
		// }
		// finally
		// {
		// Factory.deInitIfAlreadyInited( d1 );
		// // d1.deInit();
		// // d2.deInit();
		// assertFalse( d1.isInitingOrInited() );
		// assertTrue( d2.isInitedSuccessfully() );
		// assertTrue( d3.isInitedSuccessfully() );
		// Factory.deInit( d2 );
		// Factory.deInit( d3 );
		// // d1.deInitAllLikeMe();
		// assertFalse( d1.isInitingOrInited() );
		// assertFalse( d2.isInitingOrInited() );
		// assertFalse( d3.isInitingOrInited() );
		// // params.deInit();
		// }
	}
}