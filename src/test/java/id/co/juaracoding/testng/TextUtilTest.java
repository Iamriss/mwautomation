package id.co.juaracoding.testng;

import id.co.juaracoding.util.TextUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TextUtilTest {

    @BeforeMethod
    public void setUp() {
        // Lifecycle hook dijalankan sebelum setiap method test.
    }

    @Test
    public void is_palindrome_returns_true_for_racecar() {
        Assert.assertTrue(
                TextUtil.isPalindrome("racecar"),
                "kata 'racecar' seharusnya terdeteksi sebagai palindrome"
        );
    }

    @Test
    public void is_palindrome_returns_false_for_non_palindrome_text() {
        Assert.assertFalse(
                TextUtil.isPalindrome("hello"),
                "kata 'hello' seharusnya bukan palindrome"
        );
    }

    @Test
    public void count_vowels_returns_correct_count_for_normal_text() {
        Assert.assertEquals(
                TextUtil.countVowels("Automation"),
                6,
                "kata 'Automation' seharusnya memiliki 6 huruf vokal"
        );
    }

    @Test
    public void count_vowels_returns_zero_for_null_text() {
        Assert.assertEquals(
                TextUtil.countVowels(null),
                0,
                "input null seharusnya menghasilkan jumlah vokal 0"
        );
    }
}
