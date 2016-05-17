/**
 * This package contains {@link RefCollection} interfaces. A
 * {@link RefCollection} is a {@link Collection} whose element type is possibly
 * {@link Ref}. In this case, collections may be implemented by storing the
 * {@link Ref#getInternalPoolIndex() pool indices} of the elements instead of
 * Object references.
 * 
 * <p>
 * Note that (despite its name) {@link RefCollection} interfaces are
 * <em>not</em> generically typed on {@link Ref}. The actual implementations for
 * {@link Ref} objects are provided in
 * {@link net.trackmate.graph.collection.pool}, while wrappers for standard
 * {@link java.util.Collection}s are provided in
 * {@link net.trackmate.graph.collection.wrap}.
 */
package net.trackmate.graph.collection;

import java.util.Collection;

import net.trackmate.graph.zzrefcollections.Ref;