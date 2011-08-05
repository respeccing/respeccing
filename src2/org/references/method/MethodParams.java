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



package org.references.method;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import org.dml.tools.Initer;
import org.dml.tools.RunTime;
import org.dml.tracking.Factory;
import org.references.Reference;



/**
 * 
 * a list of all parameters passed to a method<br>
 * supposedly formed of objects which are references to real instances like
 * String<br>
 * you may have the same object twice, acting as two different parameters(different ParamName), but
 * this object is in fact a referent to the real instance which is ie. String,
 * 
 * same ParamName cannot have two objects in the same MethodParams list<br>
 * so there's a one to one mapping between a ParamName and it's value<br>
 * although the same ParamName can be used with another value in a different MethodParams<br>
 * 
 * TODO: maybe somehow do type checking or something when get/write and store the type in ParamID ? but also allow like
 * subclass of , or superclass of , to be specified as type
 */
public class MethodParams {
	
	// DO NOT MAKE THIS to extend Initer because there's no point, we can't really deInit it when Initer.deInit() anyway
	
	// ParamName, and it's Value(any subclass of Object or even just Object)
	private final HashMap<ParamID, Reference<Object>>	listOfParamsWithValues	= new HashMap<ParamID, Reference<Object>>();
	
	
	// can't use a Set or HashSet or TwoWayHashSet because we need to parse the
	// list, which could be done with an Iterator but I forgot why can't
	
	// /**
	// * prevent constructor, use {@link #getNew()} instead<br>
	// */
	// private MethodParams() {
	//
	// super();
	// }
	
	/**
	 * this will never be deInit-ed or similar<br>
	 * 
	 * @return new instance of MethodParams
	 */
	public static MethodParams getNew() {
		
		final MethodParams one = new MethodParams();
		// Factory.getNewInstanceAndInitWithoutMethodParams( MethodParams.class );
		// new MethodParams();
		// one.init( null );
		// RunTime.assumedTrue( one.isInited() );
		return one;
	}
	
	
	// @Deprecated
	// public static
	// void
	// doneWith(
	// MethodParams thisOne )
	// {
	//
	// RunTime.assumedNotNull( thisOne );
	// RunTime.assumedTrue( thisOne.isInited() );
	// Factory.deInit( thisOne );
	// // thisOne.deInit();
	// }
	
	
	/**
	 * private constructor
	 */
	private MethodParams() {
		
		super();
	}
	
	
	public int size() {
		
		// RunTime.assumedTrue( this.isInited() );
		return listOfParamsWithValues.size();
	}
	
	
	/**
	 * explicitly get value instead of ref to value<br>
	 * 
	 * @param paramName
	 * @return get the value object not the reference to it
	 * @throws NoSuchElementException
	 *             if there is no mapping between paramName and a value<br>
	 *             the exception is wrapped! use {@link RunTime#isThisWrappedException_of_thisType(Throwable, Class)}
	 */
	public Object getEx( final ParamID paramName ) {
		
		// RunTime.assumedTrue( this.isInited() );
		final Reference<Object> ref = get( paramName );
		if ( null == ref ) {
			RunTime.thro( new NoSuchElementException( "a certain parameter was expected but was not specified by caller" ) );
		}
		return ref.getObject();
	}
	
	
	// private ChainedReference<T> internalGetFirst() {
	//
	// return listOfParams.getRefAt( Position.FIRST );
	// }
	//
	// private ChainedReference<T> internalGetNextOf( ChainedReference<T> afterThis ) {
	//
	// return listOfParams.getRefAt( Position.AFTER, afterThis );
	// }
	
	/**
	 * this method will search for paramName and return a reference to it's
	 * value Object<br>
	 * 
	 * @param paramName
	 * @return reference; null if not found, or the ref pointing to the value,
	 *         the value can be null tho;<br>
	 *         that's why we need a reference because the value pointed to by ref can be null AND also there can be no
	 *         such ref indicated by returning null<br>
	 *         return null if not found; use .getObject() to get the value
	 */
	public Reference<Object> get( final ParamID paramName ) {
		
		// RunTime.assumedTrue( this.isInited() );
		RunTime.assumedNotNull( paramName );
		return listOfParamsWithValues.get( paramName );
		
		// int foundCounter = 0;// should not exceed 1
		// ChainedReference<T> found = null;
		// // parse listOfParams and check each element(the reference of each)
		// // against ParamName list
		// ChainedReference<T> citer = this.internalGetFirst();
		// // listOfParams.getRefAt(
		// // Position.FIRST );
		// while ( null != citer ) {
		// // paramName list can have only 1 reference from a MethodParams
		// if ( paramName.contains( citer ) ) {
		// foundCounter++;
		// found = citer;// don't clone; new Reference<T>( iter );// clone
		// // we could do a break; here but we want to make sure that it's
		// // not found more than 1 times, that would mean Bug
		// // can't have the same ParamName listed twice in the same params
		// // list for same method
		// }
		// // go next
		// citer = this.internalGetNextOf( citer );
		// // listOfParams.getRefAt( Position.AFTER, citer );
		// }
		// RunTime.assumedTrue( foundCounter <= 1 );
		// return found;
	}
	
	
	/**
	 * @param paramName
	 * @param value
	 *            can be null or an object that was already used as a parameter
	 *            one or more times
	 * @return reference to previous value(aka object) or null if none
	 */
	public Reference<Object> set( final ParamID paramName, final Object value ) {
		
		// RunTime.assumedTrue( this.isInited() );
		RunTime.assumedNotNull( paramName );
		
		Reference<Object> ref = get( paramName );
		final Reference<Object> prevValue = ref;
		if ( null == ref ) {
			ref = new Reference<Object>();// FIXME: maybe cleanup if needed on .clear()
			final int bug = listOfParamsWithValues.size();// FIXME: remove
			final Object oldValue = listOfParamsWithValues.put( paramName, ref );
			RunTime.assumedNull( oldValue );
			RunTime.assumedTrue( listOfParamsWithValues.size() == ( 1 + bug ) );
		}
		
		ref.setObject( value );
		return prevValue;
	}
	
	
	/**
	 * easy cast wrapper
	 * 
	 * @param paramName
	 * @return get the value object as a String object
	 */
	public String getExString( final ParamID paramName ) {
		// RunTime.assumedTrue( this.isInited() );
		RunTime.assumedNotNull( paramName );
		// TODO: check if can be cast and wrap throws
		final Object o = getEx( paramName );
		if ( !( o instanceof String ) ) {
			RunTime.badCall( "that parameter didn't have a String in it - cast fail" );
		}
		return (String)o;
	}
	
	
	/**
	 * @param paramName
	 * @return false if didn't exist; true if it did exist, but it doesn't now
	 *         after call
	 */
	public boolean remove( final ParamID paramName ) {
		
		// RunTime.assumedTrue( this.isInited() );
		RunTime.assumedNotNull( paramName );
		return null != listOfParamsWithValues.remove( paramName );
		// ChainedReference<T> cref = this.internalGet( paramName );
		// if ( null == cref ) {
		// return false;// not found
		// } else {// already exists
		// boolean ret = listOfParams.removeRef( cref );
		// RunTime.assumedTrue( ret );
		// paramName.remove( cref );
		// redundantListOfNames.removeObject( paramName );
		// // FIXME: transaction needed
		// return ret;
		// }
	}
	
	
	/**
	 * this will merge the two, such as <code>this</code> will have both<br>
	 * 
	 * @param withThisNewOnes
	 *            the contents of this parameter list will be copied to <code>this</code>
	 * @param overwrite
	 *            if true then overwrite the params that already exist in <code>this</code> with those that exist in
	 *            <code>withThisNewOnes</code><br>
	 *            clearly this means that those that don't exist in <code>this</code> will be added from
	 *            withThisNewOnes, but those that do
	 *            exist in <code>this</code> will be lost
	 */
	public void mergeWith( final MethodParams withThisNewOnes, final boolean overwrite ) {
		
		// RunTime.assumedTrue( this.isInited() );
		RunTime.assumedNotNull( withThisNewOnes );
		if ( this == withThisNewOnes ) {
			RunTime.badCall( "attempted to merge with self" );
		}
		
		final Iterator<Entry<ParamID, Reference<Object>>> iter = withThisNewOnes.getIter();
		
		
		while ( iter.hasNext() ) {
			final Entry<ParamID, Reference<Object>> current = iter.next();
			final ParamID paramName = current.getKey();
			final Reference<Object> refToValue = current.getValue();
			final boolean alreadyExists = get( paramName ) != null;
			if ( ( alreadyExists && overwrite ) || ( !alreadyExists ) ) {
				// doesn't reuse refs from the other MethodParams!
				set( paramName, refToValue.getObject() );
				// the values are the same:
				RunTime.assumedTrue( getEx( paramName ) == withThisNewOnes.getEx( paramName ) );
				// the refs are not the same:
				RunTime.assumedTrue( get( paramName ) != withThisNewOnes.get( paramName ) );
			}
		}
		
	}
	
	
	/**
	 * @return
	 * 
	 */
	private Iterator<Entry<ParamID, Reference<Object>>> getIter() {
		
		// RunTime.assumedTrue( this.isInited() );
		return listOfParamsWithValues.entrySet().iterator();
		
	}
	
	
	public void clear() {
		
		// RunTime.assumedTrue( this.isInited() );
		listOfParamsWithValues.clear();
		RunTime.assumedTrue( size() == 0 );
		
	}
	
	
	// private ParamName<T> internalGetParamName( ChainedReference<T> thatPointsToThisRef ) {
	//
	// RunTime.assumedNotNull( thatPointsToThisRef );
	//
	// ParamName<T> ret = null;
	// // we need to find the paramname who's value this is
	// ParamName<T> namesIter = this.getFirstParamName();
	// int count = 0;
	// while ( null != namesIter ) {
	// if ( namesIter.contains( thatPointsToThisRef ) ) {
	// // found it, and since it can be only one...we should break;
	// // break; but we won't break , we try find bugs if any
	// count++;
	// // two params cannot contain same ref, even thos
	// // ref.getObject() can be same; so we detect Bug here
	// RunTime.assumedTrue( count <= 1 );
	// ret = namesIter;
	// }
	//
	// // fetch next ParamName
	// namesIter = this.getNextParamName( namesIter );
	// }
	//
	// return ret;
	// }
	
	/**
	 * after calling this, you will need to use deInit() on the returned,
	 * because the returned did an init()
	 * 
	 * @return clone of this
	 */
	public MethodParams getClone() {
		
		// RunTime.assumedTrue( this.isInited() );
		final MethodParams clone = getNew();// Factory.getNewInstanceAndInitWithoutMethodParams( MethodParams.class );
		// MethodParams clone = new MethodParams();
		// clone.init( null );// must be null or recursion
		RunTime.assumedTrue( clone.size() == 0 );
		clone.mergeWith( this, false// there's nothing to overwrite
			);
		RunTime.assumedTrue( clone.size() == size() );
		RunTime.assumedTrue( clone.listOfParamsWithValues.size() == listOfParamsWithValues.size() );
		RunTime.assumedTrue( clone.listOfParamsWithValues != listOfParamsWithValues );
		RunTime.assumedTrue( clone != this );
		
		return clone;
	}
	
	
	// @Override
	// protected
	// void
	// done(
	// MethodParams params )
	// {
	// RunTime.assumedNull( params );
	// this.clear();
	// RunTime.assumedTrue( this.size() == 0 );
	// RunTime.assumedTrue( listOfParamsWithValues.size() == 0 );
	// }
	
	
	// @Override
	// protected
	// void
	// start(
	// MethodParams params )
	// {
	//
	// // params should be null, passed via init(...)
	// RunTime.assumedTrue( null == params );
	//
	// // assumed it's empty on start, or else bugged
	// RunTime.assumedTrue( this.size() == 0 );
	// }
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		// RunTime.assumedTrue( this.isInited() );
		return super.toString() + listOfParamsWithValues.toString();
	}
	
	
	/**
	 * @param outputs
	 * @param params
	 */
	@Deprecated
	public static void expectedOutputs( final MethodParams outputs, final ParamID... params ) {
		RunTime.assumedNotNull( outputs, params );
		RunTime.assumedTrue( params.length > 0 );
		RunTime.assumedTrue( outputs.size() > 0 );
		for ( final ParamID param : params ) {
			RunTime.assumedNotNull( param );
			final Reference<Object> ref2RVar = outputs.get( param );
			if ( null == ref2RVar ) {
				// RunTime.assumedNotNull( ref2RVar );
				RunTime.badCall( "the expected param(" + param + ") was not in the outputs list" );
			} else {
				// @SuppressWarnings( "unchecked" )
				// Reference<Object> rVar = (Reference<Object>)ref2RVar.getObject();
				final Object rVar = ref2RVar.getObject();
				// if ( rVar.getClass() != Reference.class )
				if ( !( rVar instanceof Reference ) ) {
					RunTime.badCall( "you passed a non Reference variable(rVar) for param " + param );
				}
				RunTime.assumedNotNull( rVar );
				// TODO: expectedInputs additional check for rVar.getObject() != null
			}
		}
	}
	
	
	/**
	 * @param paramID
	 * @param value
	 */
	@Deprecated
	public void setRVarValue( final ParamID paramID, final Object value ) {
		RunTime.assumedNotNull( paramID );
		final Reference<Object> ref2RVar = get( paramID );
		if ( null == ref2RVar ) {
			RunTime
				.badCall( "the param("
					+ paramID
					+ ") must already exist before setting it's value. why? because it's a reference to the value; so that ref must exist" );
		} else {
			// this.set( paramName, value )
			// @SuppressWarnings( "unchecked" )
			final Object rVar = ref2RVar.getObject();
			if ( !( rVar instanceof Reference ) ) {
				RunTime.bug( "should not be that the already existing paramID has a non reference associated with it" );
			} else {
				RunTime.assumedNotNull( rVar );
				@SuppressWarnings( "unchecked" )
				final Reference<Object> rVarCast = (Reference<Object>)rVar;
				rVarCast.setObject( value );// can be null
			}
		}
	}
	
	
	/**
	 * @param paramID
	 * @param rVar
	 */
	@Deprecated
	public void associate( final ParamID paramID, final Reference<?> rVar ) {
		RunTime.assumedNotNull( paramID, rVar );
		if ( null != get( paramID ) ) {
			RunTime.badCall( "the param(" + paramID + ") must not already be set" );
		} else {
			set( paramID, rVar );
		}
	}
	
	
}