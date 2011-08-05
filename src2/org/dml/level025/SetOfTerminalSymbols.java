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



package org.dml.level025;



import org.dml.level010.*;
import org.dml.level020.*;
import org.dml.tools.*;
import org.references.*;



/**
 * won't allow pointing to self<br>
 * self->x,y,z as terminals<br>
 * probably a bad idea to make a set of initials<br>
 */
public class SetOfTerminalSymbols {
	
	private static final TwoKeyHashMap<Level025_DMLEnvironment, Symbol, SetOfTerminalSymbols>	allSetOfSymbolsInstances	=
																																new TwoKeyHashMap<Level025_DMLEnvironment, Symbol, SetOfTerminalSymbols>();
	protected final Level025_DMLEnvironment														env;
	protected final Symbol																		selfAsSymbol;
	
	
	/**
	 * private constructor
	 */
	protected SetOfTerminalSymbols( final Level025_DMLEnvironment passedEnv, final Symbol passedSelf ) {
		
		RunTime.assumedNotNull( passedEnv, passedSelf );
		RunTime.assumedTrue( passedEnv.isInitingOrInited() );
		
		env = passedEnv;
		selfAsSymbol = passedSelf;
	}
	
	
	// TODO: new, existing, ensure
	public static SetOfTerminalSymbols getAsSet( final Level025_DMLEnvironment passedEnv, final Symbol passedSelf ) {
		
		RunTime.assumedNotNull( passedEnv, passedSelf );
		RunTime.assumedTrue( passedEnv.isInitingOrInited() );
		
		SetOfTerminalSymbols existingSOS = getSOSInstance( passedEnv, passedSelf );
		if ( null == existingSOS ) {
			existingSOS = new SetOfTerminalSymbols( passedEnv, passedSelf );
			existingSOS.assumedValid();
			registerSOSInstance( passedEnv, passedSelf, existingSOS );
		}
		existingSOS.assumedValid();
		RunTime.assumedTrue( passedEnv == existingSOS.env );
		RunTime.assumedTrue( passedSelf == existingSOS.selfAsSymbol );
		return existingSOS;
	}
	
	
	/**
	 * 
	 */
	public void assumedValid() {
		
		RunTime.assumedNotNull( env, selfAsSymbol );
		RunTime.assumedTrue( env.isInitingOrInited() );// ye we get here while still in start()
	}
	
	
	private final static void registerSOSInstance( final Level025_DMLEnvironment env, final Symbol passedSelf,
													final SetOfTerminalSymbols newOne ) {
		
		RunTime.assumedNotNull( env, passedSelf, newOne );
		RunTime.assumedFalse( allSetOfSymbolsInstances.ensure( env, passedSelf, newOne ) );
	}
	
	
	private final static SetOfTerminalSymbols getSOSInstance( final Level025_DMLEnvironment env, final Symbol passedSelf ) {
		
		RunTime.assumedNotNull( env, passedSelf );
		return allSetOfSymbolsInstances.get( env, passedSelf );
	}
	
	
	public Symbol getAsSymbol() {
		
		assumedValid();
		return selfAsSymbol;
	}
	
	
	/**
	 * @param element
	 * @return false if it didn't already exist
	 */
	public boolean addToSet( final Symbol element ) {
		
		RunTime.assumedNotNull( element );
		if ( selfAsSymbol == element ) {
			RunTime.badCall();
		}
		return env.ensureVector( selfAsSymbol, element );
	}
	
	
	/**
	 * @param which
	 *            should be a child of domain
	 * @return true if self->which
	 */
	public boolean hasSymbol( final Symbol which ) {
		
		RunTime.assumedNotNull( which );
		RunTime.assumedFalse( selfAsSymbol == which );
		return env.isVector( selfAsSymbol, which );
	}
	
	
	public long size() {
		
		RunTime.assumedNotNull( selfAsSymbol );
		// cache won't do
		return env.countTerminals( selfAsSymbol );
	}
	
	
	/**
	 * @param element
	 * @return true if existed
	 */
	public boolean remove( final Symbol element ) {
		
		RunTime.assumedNotNull( element );
		RunTime.assumedFalse( selfAsSymbol == element );
		return env.removeVector( selfAsSymbol, element );
	}
	
	
	/**
	 * @param side
	 *            only FIRST is allowed yet
	 * @return
	 */
	public Symbol getSide( final Position side ) {
		
		Symbol ret = null;
		
		// is needed to get new fresh iterator due to possible changes in the database, those won't be reflected if we
		// keep an iterator open all the time, right?
		SymbolIterator iter = env.getIterator_on_Terminals_of( selfAsSymbol );
		try {
			switch ( side ) {
			case FIRST:
				iter.goFirst();
				break;
			default:
				RunTime.badCall( "unsupported position" );
			}
			
			ret = iter.now();
		} finally {
			try {
				iter.close();
			} finally {
				iter = null;
			}
		}
		
		return ret;
	}
	
	
	/**
	 * @param side
	 * @param ofThis
	 * @return null if none
	 */
	public Symbol getSideOf( final Position side, final Symbol ofThis ) {
		
		RunTime.assumedNotNull( side, ofThis );
		Symbol ret = null;
		
		SymbolIterator iter = env.getIterator_on_Terminals_of( selfAsSymbol );
		try {
			iter.goTo( ofThis );
			if ( iter.now() != null ) {
				
				switch ( side ) {
				case BEFORE:
					iter.goPrev();
					break;
				case AFTER:
					iter.goNext();
					break;
				default:
					RunTime.badCall( "unsupported position" );
				}
				
				ret = iter.now();
			}
		} finally {
			try {
				iter.close();
			} finally {
				iter = null;
			}
		}
		
		return ret;
	}
}