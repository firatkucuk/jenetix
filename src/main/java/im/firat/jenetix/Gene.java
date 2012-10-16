
package im.firat.jenetix;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Gene implements Cloneable {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private int        bitCount;
    private List<Byte> bits;
    private int        value;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public Gene(int bitCount) {

        this.bitCount = bitCount;

        // 8 bit bir gen isteniyor ise 0 - 255 arası bir rasgele değere sahip olacaktır.
        updateBits(new Random().nextInt((int) (Math.pow(2, bitCount) - 1))); // 0 - ile 2^bitCount - 1 arasında
    }



    /**
     * Eğer genin bit içeriği verilmiş ise sayısal değeri hesaplanır.
     *
     * @param  bits
     */
    public Gene(List<Byte> bits) {

        updateValue(bits);
    }



    private Gene() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public Object clone() {

        Gene gene = new Gene();
        gene.bits     = new ArrayList(bits);
        gene.bitCount = bitCount;
        gene.value    = value;

        return gene;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public List<Byte> getBits() {

        return bits;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public int getValue() {

        return value;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setBits(List<Byte> bits) {

        updateValue(bits);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setValue(int value) {

        updateBits(value);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return "Gene[" + value + ": " + new BigDecimal(value).toBigInteger().toString(2) + ']';
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private void updateBits(int value) {

        this.value = value;
        this.bits  = new ArrayList<Byte>(bitCount);

        // Sayısal değer ikilik gösterime çevriliyor.
        // 10 sayısını mesela ikili sayıya çevirelim.
        // 1010 >> 0 = 1010 ==> 1010 & 0001 = 0
        // 1010 >> 1 = 0101 ==> 0101 & 0001 = 1
        // 1010 >> 2 = 0010 ==> 0010 & 0001 = 0
        // 1010 >> 3 = 0001 ==> 0001 & 0001 = 1
        for (int i = bitCount - 1; i >= 0; i--) {
            bits.add((byte) ((value >> i) & 0x01));
        }
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private void updateValue(List<Byte> bits) {

        this.bits  = bits;
        this.value = 0;

        int lastIndex = bits.size() - 1;

        // 1010 dizesi için heaplayalım
        // 0001 << 3 = 1000 ==> 8, toplam:  8
        // 0000 << 2 = 0000 ==> 0, toplam:  8
        // 0001 << 1 = 0010 ==> 2, toplam: 10
        // 0000 << 1 = 0000 ==> 0, toplam: 10
        for (int i = 0, l = bits.size(); i < l; i++) {
            value += (bits.get(i) << (lastIndex - i));
        }
    }
}
