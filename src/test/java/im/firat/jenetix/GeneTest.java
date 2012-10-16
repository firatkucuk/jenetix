
package im.firat.jenetix;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;



public class GeneTest {



    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    private static final Logger LOG = LoggerFactory.getLogger(GeneTest.class);



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Test
    public void cloneGene() {

        List<Byte> bits    = Arrays.asList((byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 0);
        Gene       gene    = new Gene(bits);
        Gene       geneRef = gene;

        gene.getBits().set(0, (byte) 0);
        assert geneRef.getBits().get(0) == (byte) 0;

        LOG.info("Changing via reference");

        Gene cloneGene = (Gene) gene.clone();
        cloneGene.getBits().set(0, (byte) 1);

        assertFalse(gene.getBits().get(0) == cloneGene.getBits().get(0));

        LOG.info("Clone is independent from original object");
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    public void initializeGeneWithBitCount() {

        Gene       gene       = new Gene(5);
        String     validation = "";
        List<Byte> bits       = gene.getBits();

        for (Byte geneBit : bits) {
            validation += geneBit;
        }

        String base2Bits  = new BigInteger(validation, 2).toString(2);
        String base2Value = new BigDecimal(gene.getValue()).toBigInteger().toString(2);

        assertEquals(base2Bits, base2Value);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    public void initializeGeneWithNewBits() {

        List<Byte> bits       = Arrays.asList((byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 0);
        Gene       gene       = new Gene(bits);
        String     validation = "";

        for (Byte geneBit : bits) {
            validation += geneBit;
        }

        String base2Bits  = new BigInteger(validation, 2).toString(2);
        String base2Value = new BigDecimal(gene.getValue()).toBigInteger().toString(2);

        assertEquals(base2Bits, base2Value);
    }
}
