package unittests;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.util.Utils;
import org.junit.Assert;
import org.junit.Test;


public class UtilsTest {

    @Test
    public void testShorten(){
        Assert.assertEquals("Hallo Welt", Utils.shorten("Hallo Welt Hallo Welt", 10));
    }




}
