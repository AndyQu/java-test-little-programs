import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 在程序运行时刻,char/String等字符串在内存中是如何存储的呢?
 * JDK Java Doc讲得很清楚:http://docs.oracle.com/javase/8/docs/api/java/lang/Character.html#unicode
 * The char data type (and therefore the value that a Character object encapsulates) are based on the
 * original Unicode specification, which defined characters as fixed-width 16-bit entities. The Unicode Standard has
 * since been changed to allow for characters whose representation requires more than 16 bits. The range of legal
 * code points is now U+0000 to U+10FFFF, known as Unicode scalar value. (Refer to the definition of the U+n notation
 * in the Unicode Standard.)
 *
 * BMP:
 * The set of characters from U+0000 to U+FFFF is sometimes referred to as the Basic Multilingual Plane (BMP).Characters
 * whose code points are greater than U+FFFF are called supplementary characters. The Java platform uses the UTF-16
 * representation in char arrays and in the String and StringBuffer classes.
 *
 * Supplementary Characters:
 * In this representation, supplementary characters are represented as a pair of char values, the first from the
 * high-surrogates range, (\uD800-\uDBFF), the second from the low-surrogates range (\uDC00-\uDFFF).
 *
 * Char Method:
 *      A char value, therefore, represents Basic Multilingual Plane (BMP) code points, including the surrogate code points,
 * or code units of the UTF-16 encoding.
 *      The methods that only accept a char value cannot support supplementary characters. They treat char values from the
 * surrogate ranges as undefined characters. For example, Character.isLetter('\uD840') returns false, even though this
 * specific value if followed by any low-surrogate value in a string would represent a letter.
 *
 * Int Method:
 *      An int value represents all Unicode code points, including supplementary code points. The lower (least significant)
 * 21 bits of int are used to represent Unicode code points and the upper (most significant) 11 bits must be zero.
 *      The methods that accept an int value support all Unicode characters, including supplementary characters. For example,
 * Character.isLetter(0x2F81A) returns true because the code point value represents a letter (a CJK ideograph).
 *
 * Unicode code point:
 *      used for character values in the range between U+0000 and U+10FFFF
 *
 * Unicode code unit:
 *      used for 16-bit char values that are code units of the UTF-16 encoding
 *
 */
public class Test_CharMemoryValue {
    /*
     * char是primitive类型,应该是直接存储其数值,从0x0000到0xFFFF,这个数值直接对应Unicode Code Point吗?
     * 请看下面的例子(BMP:The set of characters from U+0000 to U+FFFF is sometimes referred to as the Basic Multilingual Plane)
     */
    @Test
    public void unicodeBMP_charArray(){
        char[] charArray=new char[]{
                (char)0x6211,
                (char)0x7684,
                (char)0x540D,
                (char)0x5B57,
                (char)0x662F,
        };
        String textFromCharArray=new String(charArray);
        System.out.println(textFromCharArray);
        String unicodeText="\u6211\u7684\u540D\u5B57\u662F";
        System.out.println(unicodeText);
        Assert.assertEquals(textFromCharArray,unicodeText);
    }

    /**
     * Characters whose code points are greater than U+FFFF are called supplementary characters
     */
    @Test
    public void unicodeSupplementary(){
        String str="\uD800\uDF48";
        System.out.println(str);
        char[] charArray=new char[]{
                (char)0xd800,
                (char)0xdF48
        };
        String textFromCharArray=new String(charArray);
        System.out.println(textFromCharArray);
        Assert.assertEquals(str,textFromCharArray);
    }
    @Test
    public void escapeJava(){
        //下面这一行代码定义了一个字符串,那么Java程序应该是将每一个字符都存储为一个char类型(数值).
        String originalStr="我andy\t\"\\'!";
        System.out.println(String.format("origin text:%s",originalStr));
        String escapedStr = StringEscapeUtils.escapeJava(originalStr);
        System.out.println(String.format("escaped text:%s",escapedStr));
        String deescapedStr=StringEscapeUtils.unescapeJava(escapedStr);
        System.out.println(String.format("deescaped text:%s",deescapedStr));
    }
}
