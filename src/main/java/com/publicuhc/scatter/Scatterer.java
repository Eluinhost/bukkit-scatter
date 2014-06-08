package com.publicuhc.scatter;

import com.publicuhc.scatter.logic.ScatterLogic;

import java.util.List;

public interface Scatterer {

    /**
     * @param logic the logic to add to the list we can use in scatters
     */
    void addScatterLogic(ScatterLogic logic);

    /**
     * @return all of the scatter logic we know
     */
    List<ScatterLogic> getAllScatterLogic();

    /**
     * @param name the id of the logic to fetch
     * @return the scatterlogic if found, null otherwise
     */
    ScatterLogic getScatterLogic(String name);
}
