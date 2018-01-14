package com.girlsgeneration.tiffany270.weather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {


    private ViewPager vp;
    private List<Fragment> mylist;
    private MyFragment fragment1, fragment2, fragment3;
    private String c1 = "重庆";
    private String c2 = "上海";
    private String c3 = "广州";

    private TextView tv;

    private AlertDialog listDialog;
    private int CheckItem = 0;

    private AlertDialog.Builder listbuilder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tv = (TextView) findViewById(R.id.tv);
        tv.setVisibility(View.INVISIBLE);
        c1 = tv.getText().toString();

        init();




    }

    //初始化
    public void init() {

        vp = (ViewPager) findViewById(R.id.vp);
        FragmentManager manager = getSupportFragmentManager();
        myAdapter adapter = new myAdapter(manager);

        data();


        vp.setAdapter(adapter);


    }

    public void data() {
        //数据源
        mylist = new ArrayList<Fragment>();



        fragment1 = MyFragment.newIns(c1,"1");
        mylist.add(fragment1);
        fragment2 = MyFragment.newIns(c2,"2");
        mylist.add(fragment2);
        fragment3 = MyFragment.newIns(c3,"3");
        mylist.add(fragment3);





    }


    public void ListDialog(View view) {


//        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
//        View v = inflater.inflate(R.layout.firstcity_layout,null);
//        TextView dir = (TextView) v.findViewById(R.id.test);
//        Toast.makeText(MainActivity.this,"..."+dir.getText().toString(),Toast.LENGTH_SHORT).show();




        listbuilder = new AlertDialog.Builder(this);
        listbuilder.setTitle("切换城市").
                setIcon(R.drawable.city);

        //设置标题栏的文本
        listDialog = listbuilder.setSingleChoiceItems(R.array.name,
                CheckItem,//item的编号
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CheckItem = which;
                        String[] forname = getResources().getStringArray(R.array.name);
                        setTitle(forname[which]);


                        c1 = forname[which];
                        Log.i("city", forname[which]);


                        fragment1.flash(c1);
                        tv.setText(c1);

                        listDialog.dismiss();
                    }
                }).create();


        listDialog.show();

    }


    class myAdapter extends FragmentPagerAdapter {

        public myAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return mylist.get(position);
        }

        @Override
        public int getCount() {
            return mylist != null ? mylist.size() : 0;
        }
    }


}


