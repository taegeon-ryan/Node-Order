package jp.dylee.nodeorder;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Student {
    public int grade;
    public int s_class;
    public String name;
    public int number;
    public String site;
    public String s_return;
    public Map<String, Boolean> stars = new HashMap<>();


    public Student(){

    }

    public Student(String name, String site) {
        this.name = name;
        this.site = site;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("grade", grade);
        result.put("s_class", s_class);
        result.put("name", name);
        result.put("number", number);
        result.put("site", site);
        result.put("s_return", s_return);

        return result;
    }

}
