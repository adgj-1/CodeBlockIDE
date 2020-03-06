package main.ide.modules;

public interface Container {
	/**
	 * Insert a Containable into the given slot
	 * @param c    the object to insert
	 * @param slot the slot number to insert (put anything if only one slot)
	 */
	public abstract void input(Containable c, int slot);
	/**
	 * Remove the given Containable from the given slot,
	 * return true if object removed and false if object
	 * not found.
	 * @param c    the object to remove
	 * @param slot the slot number to remove (put anything if only one slot)
	 * @return whether object successfully removed from container
	 */
	public abstract boolean remove(Containable c, int slot);
	
	/**
	 * Find the slot in which the object is stored
	 * @param c the object to search for
	 * @return the slot the object is found, -1 if object not found
	 */
	public abstract int getObjectSlot(Containable c);
	
	public abstract void updateContent();
}
