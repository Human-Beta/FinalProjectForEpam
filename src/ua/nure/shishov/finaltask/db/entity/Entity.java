package ua.nure.shishov.finaltask.db.entity;

import java.io.Serializable;

/**
 * Root for all entities which have identifier field.
 * 
 * @author N.Shishov
 *
 */
public class Entity implements Serializable {

	private static final long serialVersionUID = 113910369728523037L;

	protected long id;

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
