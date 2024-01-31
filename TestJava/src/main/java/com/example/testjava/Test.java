package com.example.testjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName Test
 * @Author TZY
 * @Date 2023/10/26 17:51
 **/
public class Test {
    public static void main(String[] args) {
        String str = null;
//        System.out.println("123\\");

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
//        String a="dsa"+1;
//        System.out.println(list.remove(0));

        String s="abc";
        String t="ahbgdc";

        ArrayList<String> list3 = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
        for (int i = 0; i < list3.size(); i++) {
            list3.remove(i);
        }
        System.out.println(list3);
        System.out.println(isSubsequence(s,t));
        int sSize = s.length();
        int tSize = t.length();
        int count = 0;
        int index = 0;
        for (int i = 0; i < tSize; i++) {
            for (int j = index+1; j < sSize; j++) {
                if (t.charAt(i) == s.charAt(j)) {
                    index = j;
                    count++;
                    break;
                }
            }
        }
        System.out.println(count==tSize);
//        String s="ahbgdc";
//        System.out.println(s.contains("ahb"));
        String oldStr = s.replaceAll("[^(a-zA-Z)]", "");
        String newStr = "";
        int size = oldStr.length();
        for (int i = size - 1; i >= 0; i--) {
            newStr = newStr + oldStr.charAt(i);
        }
        System.out.print(newStr);
        System.out.print(oldStr);


        String d="hello world";
        System.out.println(s==d);
        for (int i = 0; i < s.length(); i++) {
            System.out.println(s.charAt(i));
        }
        String[] strs=s.split(" ");
        System.out.println(strs.length);
        System.out.println(strs[0]);
        for (String str1 : strs) {
            System.out.println(str1);
        }
//        String[] strs = {"flow", "flower", "flo"};
        String result = Arrays.stream(strs).min(Comparator.comparingInt(String::length)).orElse(null);
//        System.out.println(result);
//        list.forEach(System.out::println);
//        System.out.println(strs[0].indexOf("flower"));

        int[] a = {1, 2, 3, 4, 5, 6};
        System.out.println(a.toString());
        System.out.println(list);
//        System.out.println();
//        if (str != null && !str.trim().isEmpty()) {
//            System.out.println("123\1");
//        }else {
//            System.out.println("456");
//        }
//        HashMap<Student, String> map = new HashMap<>();
//        Student s1 = new Student("西施", 23);
//        Student s5 = new Student("西施", 22);
//        System.out.println("s1==s5: " + (s1==s5));
//        System.out.println("s1.equals(s5): " + (s1.equals(s5)));
//        System.out.println("s1的哈希值:"+s1.hashCode());
//        System.out.println("s5的哈希值:"+s5.hashCode());
//
//        System.out.println();
//        Student s2 = new Student("楊玉環", 23);
//        Student s3 = new Student("貂蟬", 23);
//        Student s4 = new Student("王昭君", 23);
//
//
//
//        HashSet<Student> hst = new HashSet<>();
//        hst.add(s1);
//        hst.add(s5);
//        System.out.println(hst);


//        map.put(s1,"河北");
//        map.put(s5,"河南");
//        map.put(s2,"山西");
//        map.put(s3,"湖南");
//        map.put(s4,"北京");
//        System.out.println(map);
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 8, 9, 10};
        System.out.println(findDuplicate(nums));
        char[] letters = {'c', 'f', 'g', 'k', 'm'};
        char target = 'l';
//        System.out.println(nextGreatestLetter(letters,target));
    }

    public static boolean isSubsequence(String s, String t) {
        if (s.length() == 0) return true;
        for (int i = 0, j = 0; j < t.length(); j++) {
            if (s.charAt(i) == t.charAt(j)) {
                // 若已经遍历完 s ，则提前返回 true
                if (++i == s.length())
                    return true;
            }
        }
        return false;
    }

    public static int findDuplicate(int[] nums) {
        int l = 1, h = nums.length - 1;
        while (l <= h) {
            int mid = l + (h - l) / 2;
            int cnt = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] <= mid) cnt++;
            }
            if (cnt > mid) h = mid - 1;
            else l = mid + 1;
        }
        return l;
    }

    public static char nextGreatestLetter(char[] letters, char target) {
        int n = letters.length;
        int l = 0, h = n - 1;
        while (l <= h) {
            int m = l + (h - l) / 2;
            if (letters[m] <= target) {
                l = m + 1;
            } else {
                h = m - 1;
            }
        }
        return l < n ? letters[l] : letters[0];
    }
}
