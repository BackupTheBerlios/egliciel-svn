/**
 * 
 */
package malitech.egliciel.model;

import java.io.Serializable;

/**
 * @author Ech
 *
 * @hibernate.class
 */
public class Country implements Serializable
{
    public static final long serialVersionUID = 1L;

    private Long id;

    /**
     * @return Returns the id.
     * @hibernate.id generator-class="native" column="uid"
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
}
