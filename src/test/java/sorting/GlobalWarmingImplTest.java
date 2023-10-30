package sorting;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*normal import*/
import java.util.Random;



/**
 * Created by johnaoga on 22/10/2018.
 * 
 * updated to junit5: 11/09/2023
 */
@Grade
public class GlobalWarmingImplTest {


    @Test
    @Grade(value=1)
    public void testSimpleAll() {
        int [][] matrix = getSimpleMatrix();
        GlobalWarming warming = new GlobalWarmingImpl(matrix);

        if (warming.nbSafePoints(-1) != 100) {
            assertTrue(false);
        }

        if (warming.nbSafePoints(0) != 24) {
            assertTrue(false);
        }

        if (warming.nbSafePoints(1) != 0) {
            assertTrue(false);
        }
    }

    private int[][] getSimpleMatrix() {
        int [][] tab = new int[][] {{1,3,3,1,3},
                                    {4,2,2,4,5},
                                    {4,4,1,4,2},
                                    {1,4,2,3,6},
                                    {1,1,1,6,3}};
        return tab;
    }


}
