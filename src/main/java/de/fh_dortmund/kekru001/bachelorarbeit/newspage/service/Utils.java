package de.fh_dortmund.kekru001.bachelorarbeit.newspage.service;

/**
 * Created by krummenauer on 08.01.2017.
 */
public class Utils {

    public static String getPropertyOrSystemEnv(String key){
        String s = System.getProperty(key);
        if(s != null){
            return s;
        }

        return System.getenv(key);
    }
}
