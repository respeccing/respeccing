/**
 * 
 * Copyright (c) 2005-2011, AtKaaZ
 * All rights reserved.
 * this file is part of DemLinks
 * File created on Aug 5, 2011 1:04:06 PM
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * * Neither the name of 'DemLinks' nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.dml.storage.berkeleydb.native_via_jni;

import org.q.*;

import com.sleepycat.db.*;



/**
 *
 */
public abstract class BDBUtil
{
	
	public static int getSize( final Database db, final Environment env, final StatsConfig statsConfig1 ) {
		assert Q.nn( db );
		assert Q.nn( env );
		// assert Q.nn( env);
		if ( null != statsConfig1 ) {
			if ( statsConfig1.getFast() ) {
				Q
					.warn( "sould probably not use getFast() it will not report the size right if the current transaction that added some items is still open, "
						+ "and did not yet test if it does when txn is indeed closed; "
						+ "but for sure without fast enabled it reports right!" );
			}
		}
		
		DatabaseStats dbStats;
		try {
			dbStats = db.getStats( BDBTransaction.getCurrentTransaction( env ), statsConfig1 );
		} catch ( final DatabaseException e ) {
			throw Q.rethrow( e );
		}
		
		final int numKeys;
		final int numData;
		if ( dbStats.getClass() == HashStats.class ) {
			final HashStats hs = (HashStats)dbStats;
			numKeys = hs.getNumKeys();
			numData = hs.getNumData();
		} else {
			if ( dbStats.getClass() == BtreeStats.class ) {
				final BtreeStats bs = (BtreeStats)dbStats;
				numKeys = bs.getNumKeys();
				numData = bs.getNumData();
			} else {
				throw Q.ni();
			}
		}
		
		assert numKeys == numData;// no dups remember?
		return numKeys;
	}
}