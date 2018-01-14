package com.girlsgeneration.tiffany270.weather;

/**
 * Created by Tiffany270 on 2017/10/13.
 */

public class Weathers {

    private String city;


    public String Weathers(String cityname){

        if(cityname.equals("北京")){
            return "CN101010100";
        }

        if (cityname.equals("上海")){
            return "CN101020100";
        }

        if (cityname.equals("广州")){
            return "CN101280101";
        }


        if (cityname.equals("梧州")){
            return "CN101300601";
        }


        if (cityname.equals("成都")){
            return "CN101270101";
        }

        if (cityname.equals("涪陵")){
            return "CN101041400";
        }

        if (cityname.equals("厦门")){
            return "CN101230201";
        }

        if (cityname.equals("三亚")){
            return "CN101310201";
        }







        return "CN101040100";
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
