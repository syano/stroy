package org.openCage.jmidgard.core;

/**
 * Created by IntelliJ IDEA.
 * User: stephan
 * Date: 6/24/11
 * Time: 7:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateVersion implements Version{
    @Override
    public boolean isSnapshot() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int compareTo(Version version) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
