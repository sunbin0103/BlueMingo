package com.top_adv.myapplication1.DataBaseManagement;

import android.content.Context;
import android.util.Log;

import com.top_adv.myapplication1.DataVO.AdvVO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by sst on 2016-12-20.
 */
public abstract class DataStream<VO> {

    private String TAG = "DataStream";
    //기본 파일 네임
    private String FILE_NAME = "adv_list.txt";



    public void setFILE_NAME(String FILE_NAME){
        this.FILE_NAME = FILE_NAME;
    }

    public void saveAdvList(ArrayList<VO> list, Context context){
        try {
            FileOutputStream fout = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(list);
            //oos.flush();​

            Log.i(TAG,"save list in "+FILE_NAME);
            if(oos != null){
                oos.close();
            }
            if(fout != null){
                fout.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee){
            ee.printStackTrace();
        }
    }

    public void readAdvList(ArrayList<VO> list, Context context){
        try {
            Log.i(TAG,"read list in "+FILE_NAME);

            FileInputStream fin = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fin);
            list = (ArrayList<VO>) ois.readObject();

            if(ois != null){
                ois.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee){
            ee.printStackTrace();
        }
    }
}
