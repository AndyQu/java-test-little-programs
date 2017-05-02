import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 在程序运行时刻,char/String等字符串在内存中是如何存储的呢?
 *
 */
public class Test_CharMemoryValue {
    /*
     * char是primitive类型,应该是直接存储其数值,从0x0000到0xFFFF,这个数值直接对应Unicode Code Point吗?
     * 请看下面的例子
     */
    @Test
    public void unicodeText_charArray(){
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
