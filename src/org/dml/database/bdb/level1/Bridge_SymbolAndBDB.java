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



package org.dml.database.bdb.level1;



import org.dml.level010.Symbol;
import org.dml.tools.RunTime;
import org.dml.tools.TwoWayHashMap;



/**
 * this maps Symbol and its representation in BDB (as a long)<br>
 * Symbol is in java only, and it's just a new instance in java with no other special meaning<br>
 */
public final class Bridge_SymbolAndBDB {
	
	// this is used to prevent new-ing too many times if there's already a
	// new-ed one from a previous call
	private static final TwoWayHashMap<Long, Symbol>	all_Symbols_from_BDBStorage	= new TwoWayHashMap<Long, Symbol>();
	
	/**
	 * the only one calling this should be the BDB subsystem<br>
	 * not the user<br>
	 * with a few exceptions for JUnit tests<br>
	 * 
	 * @param longBDB
	 *            given by the BDB dbase knowing that it's unique
	 * @return
	 */
	public static Symbol newSymbolFrom( long longBDB ) {

		RunTime.assumedNotNull( longBDB );
		Symbol curr = all_Symbols_from_BDBStorage.getData( longBDB );
		if ( null == curr ) {
			// create new
			curr = new Symbol();// longBDBSymbol );
			if ( all_Symbols_from_BDBStorage.ensure( longBDB, curr ) ) {
				RunTime.bug( "a value already existed?!! wicked! it means that the above .get() is bugged?!" );
			}
		}
		return curr;
	}
	
	protected static final void junitClearAll() {

		RunTime.assumedNotNull( all_Symbols_from_BDBStorage );
		if ( null != all_Symbols_from_BDBStorage ) {
			all_Symbols_from_BDBStorage.clear();
		}
	}
	
	/**
	 * @return
	 */
	public static long getLongFrom( Symbol symbol ) {

		RunTime.assumedNotNull( symbol );
		return all_Symbols_from_BDBStorage.getKey( symbol );
	}
}